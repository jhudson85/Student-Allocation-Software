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
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
                for(int i = 0; i < 30; i++){
                model.getClassList().get(selectedClass - 1).addStudent(student);
                }
                view.changeDisplay();
                main.updateList(selectedClass - 1);
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
                    main.updateList(selectedClass - 1);
                }
                else{
                    selectedClass = -1;
                }
            }
        }
    }
    
    class SortGroupListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(main, "Please enter an appropriate group size (2-30)", "");
            if(Util.isNumber(input)){
                int groupSize = Integer.valueOf(input);
                UniversityClass uniClass = model.getClassList().get(selectedClass - 1);
                ArrayList<Student> studentList = uniClass.getStudentList();
                if(groupSize > 1 && groupSize <= studentList.size()){
                    int numOfGroups = studentList.size() / groupSize;
                    for(int i = 0; i < numOfGroups; i++){
                        uniClass.addGroup(new Group(i));
                    }
                    for(Student student:studentList){
                        for(Group g: uniClass.getGroupList()){
                            if(g.getProgramSkill() == 0){
                                g.addStudent(student);
                            }
                            else if(g.getDesignSkill() == 0){
                                g.addStudent(student);
                            }
                            else if(g.getReportSkill() == 0){
                                g.addStudent(student);
                            }
                            else if(g.getReportSkill() == 0){
                                g.addStudent(student);
                            }
                        }
                    }
                }
                
            }
        }
    }
}