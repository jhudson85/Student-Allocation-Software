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
import studentallocationsoftware.model.group_data.Group;
import studentallocationsoftware.model.group_data.Student;
import studentallocationsoftware.model.group_data.UniversityClass;
import studentallocationsoftware.view.View;
import studentallocationsoftware.view.gui_classes.MainPage;
import studentallocationsoftware.view.gui_classes.StudentPage;

/**
 *
 * @author Jack
 */
public class Controller {
    Model model;
    View view;
    JFrame frame;
    MainPage main;
    StudentPage studentPanel;
    int selectedClass = -1;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        this.frame = view.getFrame();
        this.main = view.getMainPage();
        main.addClassListener(new AddClassListener());
        main.addStudentListener(new AddStudentListener());
        main.dropDownListener(new ClassSelectedListener());
        main.sortClassListener(new SortGroupListener());
        main.exportClassListener(new SaveFileListener());
        main.listListener(new ListListener());
    }
    
    class AddClassListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            int opt = JOptionPane.showConfirmDialog(frame, "Are you sure you want to create a new class?", "Create new Class?", JOptionPane.YES_NO_OPTION);
            if(opt == 0){
                model.addNewClass();
                main.updateDropDown();
            }
        }
    }
    
    class AddStudentListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(selectedClass != -1){
                view.changeDisplay();
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
        public void actionPerformed(ActionEvent e) {
            String[] studentDetails;
            studentDetails = studentPanel.submitStudent();
            
            if(studentDetails != null){
                boolean designer = Boolean.valueOf(studentDetails[0]);
                boolean reporter = Boolean.valueOf(studentDetails[1]);
                boolean tester = Boolean.valueOf(studentDetails[2]);
                boolean programmer = Boolean.valueOf(studentDetails[3]);
                String firstName = studentDetails[4];
                String lastName = studentDetails[5];
                String studentNumber = studentDetails[6];
                
                boolean[] preferences = {designer, reporter, tester, programmer};
                
                Student student = new Student(preferences, firstName, lastName, studentNumber);
                
                //REMOVE this
                //for(int i = 0; i < 30; i++){
                //designer, reporter, tester and programmer
                model.getClassList().get(selectedClass - 1).addStudent(student);
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, false, true, false}, "test", "number 1", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, false, true, false}, "test", "number 2", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, false, false, true}, "test", "number 3", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, true, true, false}, "test", "number 4", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, false, false, true}, "test", "number 5", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, true, false, false}, "test", "number 6", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, true, false, false}, "test", "number 7", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, false, false, true}, "test", "number 8", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, false, true, false}, "test", "number 9", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, true, true, false}, "test", "number 10", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, false, false, true}, "test", "number 11", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, true, false, false}, "test", "number 12", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, false, true, false}, "test", "number 13", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, false, true, true}, "test", "number 14", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, true, true, true}, "test", "number 15", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{true, true, true, true}, "test", "number 16", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, true, true, false}, "test", "number 17", "00000001"));
                model.getClassList().get(selectedClass - 1).addStudent(new Student(new boolean[]{false, true, true, true}, "test", "number 18", "00000001"));

                

//}
                view.changeDisplay();
                main.updateList(selectedClass - 1, false);
            }
        }
    }
    
    class ClassSelectedListener implements ItemListener{
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED){
                String className = e.getItem().toString();
                String[] splitName = className.split(" ");
                if(Util.isNumber(splitName[1])){
                    selectedClass = Integer.valueOf(splitName[1]);
                    main.updateList(selectedClass - 1, false);
                }
                else{
                    selectedClass = -1;
                }
            }
        }
    }
    
    class SortGroupListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(main, "Please enter prefered group size", "");
            if(Util.isNumber(input)){
                int groupSize = Integer.valueOf(input);
                //Gets the currently selected class to be sorted
                UniversityClass uniClass = model.getClassList().get(selectedClass - 1);
                //Gets the students in that class
                ArrayList<Student> studentList = uniClass.getStudentList();
                //Sort the students by the number total number of prefered skills that they have. (Ascending order)
                Collections.sort(studentList);
                
                //Group sizes must be between one and less than or equal to half of the class size
                if(groupSize > 1 && groupSize <= studentList.size() / 2){
                    //Calculate the number of groups that needs to be created
                    int numOfGroups = studentList.size() / groupSize;
                    
                    //Clear any existing groups and then create new groups
                    uniClass.removeAllGroupElements();
                    for(int i = 0; i < numOfGroups; i++){
                        uniClass.addGroup(new Group(i));
                    }
                    for(Student student:studentList){
                        //Attempt to put a student into a group that requires a skill they have.
                        boolean placed = groupByRequiredSkill(student, uniClass);
                        //System.out.println(student.getLastName());
                        //If they are not put into a group put them into a group which has the least number of skilled members overall.
                        if(!placed){
                            Collections.sort(uniClass.getGroupList());
                            uniClass.getGroupList().get(0).addStudent(student);
                        }
                    }
                    
                    main.updateList(selectedClass - 1, true);
                    
                    //Test group to console output - possible use in export text file
                    for(Group g: uniClass.getGroupList()){
                        System.out.println("\ngroup number: " + g.getGroupNumber() + "\n group stats: PROGRAMMERS: " + g.getProgramSkill() + " DESIGNERS: " + g.getDesignSkill() + " REPORTERS: " + g.getReportSkill() + " TESTERS: " + g.getTestingSkill() + "\ntotal skill: " + g.totalSkillPoints() + "\n");
                        for(Student s: g.getStudentList()){
                            boolean[] prefs = s.getPreferences();
                            System.out.println(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber() + " [PROGRAMMING: " + prefs[3] + " :: DESIGN: " + prefs[0] + " :: REPORT: " + prefs[1] + " TESTING: " + prefs[2] + "]");
                        }
                    }
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
            for(Group g: uniClass.getGroupList()){
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
                            else if(g.getReportSkill() == 0 && prefs[2]){
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
            if(selectedClass == -1){
                JOptionPane.showMessageDialog(main, "Please select a class");
            }
            else{
                String[] saveLocation = main.showSaveChooser();
                if(saveLocation != null){
                    saveFile(saveLocation[0], saveLocation[1]);
                }
            }
        }
}
    private void saveFile(String fileName, String dir){
            File file = new File(dir + "\\" + fileName + ".txt");
            UniversityClass uniClass = model.getClassList().get(selectedClass - 1);
            ArrayList<Group> groupList = uniClass.getGroupList();
            String nl = System.getProperty("line.separator");
            try{
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                Writer w = new BufferedWriter(osw);
                if(groupList.size() == 0){
                    for(Student s: uniClass.getStudentList()){
                                boolean[] prefs = s.getPreferences();
                                w.write(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber() + " [PROGRAMMING: " + prefs[3] + " :: DESIGN: " + prefs[0] + " :: REPORT: " + prefs[1] + " TESTING: " + prefs[2] + "]" + nl);
                        }
                }
                else{
                    for(Group g: groupList){
                                w.write(nl + "group number: " + g.getGroupNumber() + nl+ "group stats: PROGRAMMERS: " + g.getProgramSkill() + " DESIGNERS: " + g.getDesignSkill() + " REPORTERS: " + g.getReportSkill() + " TESTERS: " + g.getTestingSkill() + nl + "total skill: " + g.totalSkillPoints() + nl);
                                for(Student s: g.getStudentList()){
                                    boolean[] prefs = s.getPreferences();
                                    w.write(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber() + " [PROGRAMMING: " + prefs[3] + " :: DESIGN: " + prefs[0] + " :: REPORT: " + prefs[1] + " TESTING: " + prefs[2] + "]" + nl);
                                }
                            }
                }
                w.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
    }
    
    class ListListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        }
        
    }
}