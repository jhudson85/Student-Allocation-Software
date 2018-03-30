/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.view;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javafx.stage.FileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.model.group_data.Student;
import studentallocationsoftware.view.gui_classes.MainPage;
import studentallocationsoftware.view.gui_classes.StudentPage;

/**
 *
 * @author Jack
 */
public class View {
    private Model model;
    private JFrame frame;
    private MainPage main;
    private StudentPage studentPanel;
    
    public View(Model model){
        this.model = model;
    }
    
    public void init(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Student Allocation Software");
        main = new MainPage(model);
        main.init();
        frame.setContentPane(main);
        frame.pack();
    }
    
    public JFrame getFrame(){
        return frame;
    }
    
    public MainPage getMainPage(){
        return main;
    }
    
    public StudentPage getStudentPage(){
        return studentPanel;
    }
    
    //Changes the display from main page to student page and vice-versa when called
    public void changeDisplay(){
        if(main.isVisible()){
            main.setVisible(false);
            studentPanel = new StudentPage(model);
            studentPanel.setVisible(true);
            studentPanel.init();
            frame.setContentPane(studentPanel);
        }
        else{
            main.setVisible(true);
            studentPanel.setVisible(false);
            frame.setContentPane(main);
        }
    }
    
    //Load the student page with student details inserted
    public void changeDisplay(Student s){
        main.setVisible(false);
        studentPanel = new StudentPage(model, s);
        studentPanel.setVisible(true);
        studentPanel.init();
        frame.setContentPane(studentPanel);
    }
}
