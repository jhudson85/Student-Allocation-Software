/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.view;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.view.gui_classes.MainPage;
import studentallocationsoftware.view.gui_classes.StudentPage;

/**
 *
 * @author Jack
 */
public class View {
    Model model;
    JFrame frame;
    MainPage main;
    StudentPage studentPanel;
    
    public View(Model model){
        this.model = model;
        
    }
    
    public void init(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500, 500);
        
        frame.setTitle("Student Allocation Software");
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
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
    
    public void addStudent(){
        main.setVisible(false);
        studentPanel = new StudentPage(model);
        studentPanel.setVisible(true);
        studentPanel.init();
        frame.setContentPane(studentPanel);
    }
}
