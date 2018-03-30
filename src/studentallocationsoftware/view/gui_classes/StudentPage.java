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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import studentallocationsoftware.Util;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.model.group_data.Student;

/**
 *
 * @author Jack
 */
public class StudentPage extends JPanel {

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
    private Model model;
    private GridBagConstraints c;
    
    {
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        studentNumberField = new JTextField();
        
        designBox = new JCheckBox();
        reportBox = new JCheckBox();
        testingBox = new JCheckBox();
        implementationBox = new JCheckBox();
    }

    public StudentPage(Model model) {
        this.model = model;
    }
    
    public StudentPage(Model model, Student student){
        this.model = model;
        
        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        studentNumberField.setText(student.getStudentNumber());
        
        boolean[] preferences = student.getPreferences();
        if(preferences[0]) designBox.doClick();
        if(preferences[1]) reportBox.doClick();
        if(preferences[2]) testingBox.doClick();
        if(preferences[3]) implementationBox.doClick();

    }

    public void init() {
        //Set the layout of the student page as a new box layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        c = new GridBagConstraints();
        studentDetails = new JPanel(new GridBagLayout());
        notificationLbl = new JLabel("<HTML>Please complete electronic form for each student in your class and <br> complete all sections for allocation, once completed, click submit.</HTML>", SwingConstants.CENTER);
        c.gridwidth = 2;
        addComponent(0, 0, notificationLbl, studentDetails);
        
        c.gridwidth = 1;
        JLabel firstNameLbl = new JLabel("First name *");
        addComponent(0,1,firstNameLbl, studentDetails);
        
        firstNameField.setColumns(20);
        addComponent(1,1,firstNameField,studentDetails);
        
        JLabel lastNameLbl = new JLabel("Last name *");
        addComponent(0,2,lastNameLbl, studentDetails);
        
        lastNameField.setColumns(20);
        addComponent(1,2,lastNameField,studentDetails);
        
        JLabel studentNumberLbl = new JLabel("Student number *");
        addComponent(0,3,studentNumberLbl,studentDetails);
        
        studentNumberField.setColumns(20);
        addComponent(1, 3, studentNumberField, studentDetails);

        studentDetails.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Student Details"),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)));

        add(studentDetails);

        studentPreferences = new JPanel(new GridBagLayout());
        JLabel designLbl = new JLabel("Design");
        addComponent(0,0,designLbl, studentPreferences);
        

        addComponent(1,0,designBox,studentPreferences);
        
        JLabel testingLbl = new JLabel("Testing");
        addComponent(2,0,testingLbl,studentPreferences);
        
        addComponent(3,0,testingBox,studentPreferences);
        
        JLabel reportLbl = new JLabel("Report");
        addComponent(0,1,reportLbl,studentPreferences);
        
        addComponent(1,1,reportBox,studentPreferences);
        
        JLabel implementationLbl = new JLabel("Implementation");
        addComponent(2,1,implementationLbl,studentPreferences);
        
        addComponent(3,1,implementationBox, studentPreferences);

        studentPreferences.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Student Preferences"),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)));

        add(studentPreferences);

        submitPanel = new JPanel();
        
        submitBtn = new JButton("Submit");
        submitPanel.add(submitBtn);
        
        cancelBtn = new JButton("Cancel");
        submitPanel.add(cancelBtn);

        add(submitPanel);
    }

    public void cancelButtonListener(ActionListener listener) {
        cancelBtn.addActionListener(listener);
    }

    public void submitButtonListener(ActionListener listener) {
        submitBtn.addActionListener(listener);
    }
    
    private void addComponent(int x, int y, JComponent component, JPanel panel){
        c.gridx = x;
        c.gridy = y;
        panel.add(component, c);
    }

    public String[] submitStudent() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String studentNumber = studentNumberField.getText();
        
        //Make checks to see if valid student details have been entered. If they have, return 
        //details of the student. If not, display error in message box and return null.
        if (Util.isNumber(studentNumber) && studentNumber.length() == 8) {
            if (Util.isAlpha(lastName) && lastName.length() > 0) {
                if (Util.isAlpha(firstName) && firstName.length() > 0) {
                    if (designBox.isSelected() || reportBox.isSelected() || testingBox.isSelected() || implementationBox.isSelected()) {
                        return new String[]{Boolean.toString(designBox.isSelected()), Boolean.toString(reportBox.isSelected()), Boolean.toString(testingBox.isSelected()), Boolean.toString(implementationBox.isSelected()), firstName, lastName, studentNumber};
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Please select at least one preference!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Please check the first name - it must only contain letters");
                }
            } 
            else{
                JOptionPane.showMessageDialog(this, "Please check the last name - it must only contain letters");
            }
        } 
        else {
            JOptionPane.showMessageDialog(this, "Please check the student number - it must contain 8 numbers");
        }
        return null;
    }
}
