/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.view.gui_classes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import studentallocationsoftware.Util;
import studentallocationsoftware.model.Model;

/**
 *
 * @author Jack
 */
public class StudentPage extends JPanel{
    
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentNumberField;
    private JLabel notificationLbl;
    private JPanel studentDetails;
    private JPanel studentPreferences;
    private JPanel submitPanel;
    private JButton submitBtn;
    private JButton cancelBtn;
    private JCheckBox designBox;
    private JCheckBox reportBox;
    private JCheckBox testingBox;
    private JCheckBox implementationBox;
    
    public StudentPage(Model model){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
    
    public void init(){
        GridBagConstraints c = new GridBagConstraints();
        studentDetails = new JPanel(new GridBagLayout());
        notificationLbl = new JLabel("<HTML>Please complete electronic form for each student in your class and <br> complete all sections for allocation, once completed, click submit.</HTML>", SwingConstants.CENTER);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        studentDetails.add(notificationLbl, c);
        c.gridx = 0; c.gridy = 1; c.gridwidth = 1;
        JLabel firstNameLbl = new JLabel("First name *");
        firstNameField = new JTextField();
        firstNameField.setColumns(20);
        studentDetails.add(firstNameLbl, c);
        c.gridx = 1; c.gridy = 1; c.gridwidth = 1;
        studentDetails.add(firstNameField, c);
        c.gridx = 0; c.gridy = 2; c.gridwidth = 1;
        JLabel lastNameLbl = new JLabel("Last name *");
        lastNameField = new JTextField();
        lastNameField.setColumns(20);
        studentDetails.add(lastNameLbl, c);
        c.gridx = 1; c.gridy = 2; c.gridwidth = 1;
        studentDetails.add(lastNameField, c);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 1;
        JLabel studentNumberLbl = new JLabel("Student number *");
        studentNumberField = new JTextField();
        studentNumberField.setColumns(20);
        studentDetails.add(studentNumberLbl, c);
        c.gridx = 1; c.gridy = 3; c.gridwidth = 1;
        studentDetails.add(studentNumberField, c);
        
        
        studentDetails.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Student Details"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        
        add(studentDetails);
        
        
        studentPreferences = new JPanel(new GridBagLayout());
        c.gridx = 0; c.gridy = 0;
        JLabel designLbl = new JLabel("Design");
        studentPreferences.add(designLbl, c);
        c.gridx = 1; c.gridy = 0;
        designBox = new JCheckBox();
        studentPreferences.add(designBox, c);
        c.gridx = 2; c.gridy = 0;
        JLabel testingLbl = new JLabel("Testing");
        studentPreferences.add(testingLbl, c);
        c.gridx = 3; c.gridy = 0;
        testingBox = new JCheckBox();
        studentPreferences.add(testingBox, c);
        c.gridx = 0; c.gridy = 1;
        JLabel reportLbl = new JLabel("Report");
        studentPreferences.add(reportLbl, c);
        c.gridx = 1; c.gridy = 1;
        reportBox = new JCheckBox();
        studentPreferences.add(reportBox, c);
        c.gridx = 2; c.gridy = 1;
        JLabel implementationLbl = new JLabel("Implementation");
        studentPreferences.add(implementationLbl, c);
        c.gridx = 3; c.gridy = 1;
        implementationBox = new JCheckBox();
        studentPreferences.add(implementationBox, c);
        
        studentPreferences.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Student Preferences"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        
       
        add(studentPreferences);
        
        submitPanel = new JPanel();
        submitBtn = new JButton("Submit");
        cancelBtn = new JButton("Cancel");
        
        submitPanel.add(cancelBtn);
        submitPanel.add(submitBtn);
        
        
        
        add(submitPanel);
    }
    
    public void cancelButtonListener(ActionListener listener){
        cancelBtn.addActionListener(listener);
    }
    
    public void submitButtonListener(ActionListener listener){
        submitBtn.addActionListener(listener);
    }
    
    public void submitStudent(){
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String studentNumber = studentNumberField.getText();
        if(firstName.length() > 0 && lastName.length() > 0 && studentNumber.length() == 8){
            if(Util.isAlpha(firstName) && Util.isAlpha(lastName) && Util.isNumber(studentNumber)){
                if(designBox.isSelected() || reportBox.isSelected() || testingBox.isSelected() || implementationBox.isSelected()){
                    //Create a new Student
                    System.out.println("You have added a new student!");
                    
                }
                else{
                    System.out.println("Please select at least one preference!");
                }
                
            }
            else{
                System.out.println("Please only use letters for names and numbers for student numbers");
            }
        }
        else{
            System.out.println("Please enter first name and last name with at least 1 letter and student number with 8 numbers.");
        }
    }
}