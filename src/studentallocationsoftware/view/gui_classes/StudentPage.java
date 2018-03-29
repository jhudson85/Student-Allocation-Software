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
        //Set the layout of the page as a new box layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        GridBagConstraints c = new GridBagConstraints();
        studentDetails = new JPanel(new GridBagLayout());
        notificationLbl = new JLabel("<HTML>Please complete electronic form for each student in your class and <br> complete all sections for allocation, once completed, click submit.</HTML>", SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        studentDetails.add(notificationLbl, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        JLabel firstNameLbl = new JLabel("First name *");
        firstNameField.setColumns(20);
        studentDetails.add(firstNameLbl, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        studentDetails.add(firstNameField, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        JLabel lastNameLbl = new JLabel("Last name *");
        lastNameField.setColumns(20);
        studentDetails.add(lastNameLbl, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        studentDetails.add(lastNameField, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        JLabel studentNumberLbl = new JLabel("Student number *");
        studentNumberField.setColumns(20);
        studentDetails.add(studentNumberLbl, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        studentDetails.add(studentNumberField, c);

        studentDetails.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Student Details"),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)));

        add(studentDetails);

        studentPreferences = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        JLabel designLbl = new JLabel("Design");
        studentPreferences.add(designLbl, c);
        c.gridx = 1;
        c.gridy = 0;
        studentPreferences.add(designBox, c);
        c.gridx = 2;
        c.gridy = 0;
        JLabel testingLbl = new JLabel("Testing");
        studentPreferences.add(testingLbl, c);
        c.gridx = 3;
        c.gridy = 0;
        studentPreferences.add(testingBox, c);
        c.gridx = 0;
        c.gridy = 1;
        JLabel reportLbl = new JLabel("Report");
        studentPreferences.add(reportLbl, c);
        c.gridx = 1;
        c.gridy = 1;
        studentPreferences.add(reportBox, c);
        c.gridx = 2;
        c.gridy = 1;
        JLabel implementationLbl = new JLabel("Implementation");
        studentPreferences.add(implementationLbl, c);
        c.gridx = 3;
        c.gridy = 1;
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

    public void cancelButtonListener(ActionListener listener) {
        cancelBtn.addActionListener(listener);
    }

    public void submitButtonListener(ActionListener listener) {
        submitBtn.addActionListener(listener);
    }

    public String[] submitStudent() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String studentNumber = studentNumberField.getText();
        if (Util.isNumber(studentNumber) && studentNumber.length() == 8) {
            if (Util.isAlpha(lastName) && lastName.length() > 0) {
                if (Util.isAlpha(firstName) && firstName.length() > 0) {
                    if (designBox.isSelected() || reportBox.isSelected() || testingBox.isSelected() || implementationBox.isSelected()) {
                        //Create a new Student
                        return new String[]{Boolean.toString(designBox.isSelected()), Boolean.toString(reportBox.isSelected()), Boolean.toString(testingBox.isSelected()), Boolean.toString(implementationBox.isSelected()), firstName, lastName, studentNumber};
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select at least one preference!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please check the first name - it must only contain letters");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please check the last name - it must only contain letters");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please check the student number - it must contain 8 numbers");
        }

        return null;
    }
}
