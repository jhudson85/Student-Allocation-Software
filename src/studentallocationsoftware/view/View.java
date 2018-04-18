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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    private JMenu dropDownMenu;
    private JMenuBar menu;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    
    public View(Model model){
        this.model = model;
    }
    
    public void init(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Student Allocation Software");
        menu = new JMenuBar();
        dropDownMenu = new JMenu("File");
        menuItem1 = new JMenuItem("Help");
        menuItem2 = new JMenuItem("Exit");
        dropDownMenu.add(menuItem1);
        dropDownMenu.add(menuItem2);
        menu.add(dropDownMenu);
        frame.setJMenuBar(menu);
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
    
    public void addHelpListener(ActionListener listener){
        menuItem1.addActionListener(listener);
    }
    
    public void addExitListener(ActionListener listener){
        menuItem2.addActionListener(listener);
    }
}
