/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import studentallocationsoftware.model.Model;
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
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        this.frame = view.getFrame();
        this.main = view.getMainPage();
        main.addClassListener(new AddClassListener());
        main.addStudentListener(new AddStudentListener());
        
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
            view.addStudent();
            studentPanel = view.getStudentPage();
            studentPanel.submitButtonListener(new SubmitStudentListener());
        }
    }
    
    class SubmitStudentListener implements ActionListener{
        public void actionPerformed(ActionEvent e) { 
            studentPanel.submitStudent();
        }
        
    }
}
