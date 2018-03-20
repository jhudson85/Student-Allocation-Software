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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import studentallocationsoftware.Util;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.model.group_data.Student;
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
                String firstName = studentDetails[4];
                String lastName = studentDetails[5];
                String studentNumber = studentDetails[6];
                boolean[] preferences = {Boolean.valueOf(studentDetails[0]), Boolean.valueOf(studentDetails[1]), Boolean.valueOf(studentDetails[2]), Boolean.valueOf(studentDetails[3])};
                
                Student student = new Student(preferences, firstName, lastName, studentNumber);
                
                model.getClassList().get(selectedClass - 1).addStudent(student);
                System.out.println("Added student name " + firstName + " to class " + selectedClass);
                view.changeDisplay();
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
                }
                else{
                    selectedClass = -1;
                }
            }
        }
    }
}