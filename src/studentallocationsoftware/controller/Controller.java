/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import studentallocationsoftware.Util;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.model.group_data.StudentGroup;
import studentallocationsoftware.model.group_data.Student;
import studentallocationsoftware.model.group_data.UniversityClass;
import studentallocationsoftware.view.View;
import studentallocationsoftware.view.gui_classes.MainPage;
import studentallocationsoftware.view.gui_classes.StudentPage;

/**
 * Class which contains the listener classes to allow the view
 * and model to interact with each other.
 * 
 */
public class Controller {
    private Model model;
    private View view;
    private JFrame frame;
    private MainPage main;
    private StudentPage studentPanel;
    private int selectedClassIndex = -1;
    private UniversityClass selectedClass;
    private Student selectedStudent;
    private boolean editMode;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        this.frame = view.getFrame();
        this.main = view.getMainPage();
        
        //Add the listener instances to the main page to give buttons and lists functionality
        main.addClassListener(new AddClassListener());
        main.removeClassListener(new RemoveClassListener());
        main.addStudentListener(new AddStudentListener());
        main.dropDownListener(new ClassSelectedListener());
        main.sortClassListener(new SortGroupListener());
        main.exportClassListener(new SaveFileListener());
        main.listListener(new ListListener());
        main.removeStudentListener(new RemoveStudentListener());
        main.editStudentListener(new EditStudentListener());
    }
    
    class AddClassListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int opt = JOptionPane.showConfirmDialog(frame, "Are you sure you want to create a new class?", "Create new Class?", JOptionPane.YES_NO_OPTION);
            // If user chooses yes, add a new class to the class list and update the display.
            if(opt == 0){
                model.addNewClass();
                main.updateDropDown();
            }
        }
    }
    
    class AddStudentListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(selectedClassIndex != -1){
                //Change from the main to student display and set edit mode 
                //to false because we're adding a new student not editing one
                view.changeDisplay();
                editMode = false;
                //Get the instance of the student page and add the listeners to that page
                studentPanel = view.getStudentPage();
                studentPanel.submitButtonListener(new SubmitStudentListener());
                studentPanel.cancelButtonListener(new CancelButtonListener());
            }
            else{
                JOptionPane.showMessageDialog(main, "Please select a class before adding students.");
            }
        }
    }
    
    class SubmitStudentListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //Retrive student details from the student page and store them in variables
            String[] studentDetails = studentPanel.submitStudent();
            if(studentDetails != null){
                boolean designer = Boolean.valueOf(studentDetails[0]);
                boolean reporter = Boolean.valueOf(studentDetails[1]);
                boolean tester = Boolean.valueOf(studentDetails[2]);
                boolean programmer = Boolean.valueOf(studentDetails[3]);
                String firstName = studentDetails[4];
                String lastName = studentDetails[5];
                String studentNumber = studentDetails[6];
                boolean[] preferences = {designer, reporter, tester, programmer};

                //Check that the student number isn't already taken
                if (model.getClassList().get(selectedClassIndex).getStudent(studentNumber) == null || (editMode && selectedStudent.getStudentNumber().equals(studentNumber))){
                    //If adding a new student else if editing a current student
                    if (!editMode){
                        Student student = new Student(preferences, firstName, lastName, studentNumber);
                        selectedClass.addStudent(student);
                        JOptionPane.showMessageDialog(studentPanel, "You have added a new student");
                    } else{
                        selectedStudent.setPreferences(preferences);
                        selectedStudent.setFirstName(firstName);
                        selectedStudent.setLastName(lastName);
                        selectedStudent.setStudentNumber(studentNumber);
                        JOptionPane.showMessageDialog(studentPanel, "You succesfully edited the student");
                    }
                    //Change display back to main screen and update student list
                    view.changeDisplay();
                    main.updateList(selectedClassIndex, false);
                } else {
                    JOptionPane.showMessageDialog(studentPanel, "The student number has already been used");
                }
            }
        }
    }
    
    class ClassSelectedListener implements ItemListener{
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED){
                String className = e.getItem().toString();
                String[] splitName = className.split(" ");
                if(Util.isNumber(splitName[1])){
                    selectedClassIndex = Integer.valueOf(splitName[1]) - 1;
                    selectedClass = model.getClassList().get(selectedClassIndex);
                    main.updateList(selectedClassIndex, false);
                }
                else{
                    selectedClassIndex = -1;
                }
            }
        }
    }
    
    class SortGroupListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(main, "Please enter prefered group size", "");
            if(Util.isNumber(input)){
                int groupSize = Integer.valueOf(input);
                //Gets the students in the class
                ArrayList<Student> studentList = selectedClass.getStudentList();
                //Sort the students by the total number of prefered skills that they have. (Ascending order)
                Collections.sort(studentList);
                //Group sizes must be greater than one and less than or equal to half of the class size
                if(groupSize > 1 && groupSize <= studentList.size() / 2){
                    //Calculate the number of groups that needs to be created
                    int numOfGroups = studentList.size() / groupSize;
                    //Clear any existing groups and then create new groups
                    selectedClass.removeAllGroupElements();
                    for(int i = 0; i < numOfGroups; i++){
                        selectedClass.addGroup(new StudentGroup(i));
                    }
                    for(Student student:studentList){
                        //Attempt to put a student into a group that requires a skill they have.
                        boolean placed = groupByRequiredSkill(student, selectedClass);
                        //If they are not put into a group put them into a group which has the least number of skilled members overall.
                        if(!placed){
                            Collections.sort(selectedClass.getGroupList());
                            selectedClass.getGroupList().get(0).addStudent(student);
                        }
                    }
                    //Sort the groups by group number and
                    //Update the class list specifying that the students are in groups
                    selectedClass.getGroupList().sort((group1, group2) -> group1.getGroupNumber() - group2.getGroupNumber());
                    main.updateList(selectedClassIndex, true);
                }
            }
        }
        
        /*
        *Attempt to put a student into a group if that group does not have a student with that preference and
        *that student has the required preference.
        *@return Whether the student has been placed into a group.
        */
        private boolean groupByRequiredSkill(Student student, UniversityClass uniClass){
            boolean[] prefs = student.getPreferences();
            for(StudentGroup g: uniClass.getGroupList()){
                            if(g.getProgramSkill() == 0 && prefs[3]){
                                g.addStudent(student);
                                return true;
                            }
                            else if(g.getDesignSkill() == 0 && prefs[0]){
                                g.addStudent(student);
                                return true;
                            }
                            else if(g.getReportSkill() == 0 && prefs[1]){
                                g.addStudent(student);
                                return true;
                            }
                            else if(g.getTestingSkill() == 0 && prefs[2]){
                                g.addStudent(student);
                                return true;
                            }
                        }
            return false;
        }
    }
    
    class CancelButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            view.changeDisplay();
        }
    }
    
    class SaveFileListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(selectedClassIndex == -1){
                JOptionPane.showMessageDialog(main, "Please select a class");
            }
            else{
                //Show the user a save file location chooser and save the file passing through
                //file name and directory
                String[] saveLocation = main.showSaveChooser();
                if(saveLocation != null){
                    saveFile(saveLocation[0], saveLocation[1]);
                }
            }
        }
    }
    private void saveFile(String fileName, String dir){
        File file = new File(dir + "\\" + fileName + ".txt");
        ArrayList<StudentGroup> groupList = selectedClass.getGroupList();
        //Get new line separator for adding new lines to a text file
        String nl = System.getProperty("line.separator");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Writer w = new BufferedWriter(osw);
            //Possible change if I implement abstract class for groups and uni class
            if (groupList.isEmpty()) {
                for (Student s : selectedClass.getStudentList()) {
                    boolean[] prefs = s.getPreferences();
                    w.write(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber() + " [PROGRAMMING: " + prefs[3] + " :: DESIGN: " + prefs[0] + " :: REPORT: " + prefs[1] + " TESTING: " + prefs[2] + "]" + nl);
                }
            } else {
                for (StudentGroup g : groupList) {
                    w.write(nl + "group number: " + g.getGroupNumber() + nl + "group stats: PROGRAMMERS: " + g.getProgramSkill() + " DESIGNERS: " + g.getDesignSkill() + " REPORTERS: " + g.getReportSkill() + " TESTERS: " + g.getTestingSkill() + nl + "total skill: " + g.totalSkillPoints() + nl);
                    for (Student s : g.getStudentList()) {
                        boolean[] prefs = s.getPreferences();
                        w.write(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber() + " [PROGRAMMING: " + prefs[3] + " :: DESIGN: " + prefs[0] + " :: REPORT: " + prefs[1] + " TESTING: " + prefs[2] + "]" + nl);
                    }
                }
            }
            w.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    class ListListener implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            boolean isAdjusting = e.getValueIsAdjusting();
            int selectedIndex = -1;
            String listData = "";
            //Ensures selected item has already changed
            if(!isAdjusting){
                //Find the student that the user clicked on
                selectedIndex = lsm.getMaxSelectionIndex();
                if(selectedIndex != -1){
                    listData = main.getListElement(selectedIndex);
                    findStudent(listData);
                }
            }
            
        }
    }
    
    private void findStudent(String text){
        String[] splitText = text.split(" ");
        if(splitText.length == 4){
            String studentNumber = splitText[3];
            if(Util.isNumber(studentNumber)){
                Student s = selectedClass.getStudent(studentNumber);
                if(s != null){
                    selectedStudent = s;
                }
            }
        }
    }
    
    class RemoveStudentListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(selectedStudent != null){
                selectedClass.removeStudent(selectedStudent);
                main.updateList(selectedClassIndex, false);
            }
        }
    }
    
    class EditStudentListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(selectedStudent != null){
                view.changeDisplay(selectedStudent);
                editMode = true;
                studentPanel = view.getStudentPage();
                studentPanel.submitButtonListener(new SubmitStudentListener());
                studentPanel.cancelButtonListener(new CancelButtonListener());
            }
        }
    }
    
    class RemoveClassListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(selectedClassIndex != -1){
                int opt = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this class?", "Delete class", JOptionPane.YES_NO_OPTION);
                if(opt == 0){
                    model.getClassList().remove(selectedClassIndex);
                    main.updateDropDown();
                }
            }
        }
    }
}