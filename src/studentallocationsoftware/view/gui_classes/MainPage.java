/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.view.gui_classes;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.model.group_data.Student;


public class MainPage extends JPanel {
    private JList stuList;
    private DefaultListModel listModel;
    private JButton addStuBtn;
    private JButton removeStuBtn;
    private JButton editStuBtn;
    private JButton addClassBtn;
    private JButton removeClassBtn;
    private JButton exportBtn;
    private JButton sortBtn;
    private JComboBox dropDown;
    private JPanel studentButtonPanel;
    private JPanel classButtonPanel;
    private JPanel classDropDownPanel;
    private JPanel buttonPanel;
    private Model model;
    private DefaultComboBoxModel boxModel;
   
    
    private Dimension defaultDimension;
    
    public MainPage(Model model){
        setLayout(new FlowLayout());
        defaultDimension = new Dimension(250, 30);
        this.model = model;
    }
    
    public void updateList(int classIndex){
        listModel.removeAllElements();
        for(Student s: model.getClassList().get(classIndex).getStudentList()){
            listModel.addElement(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber());
            
        }
    }
    

    public void init() {
        listModel = new DefaultListModel();
        listModel.addElement("asdf");
        
        stuList = new JList(listModel);
        stuList.setPreferredSize(new Dimension(250, 500));
        stuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(stuList);
        
        
        //The code for the left hand side of the display - the button side.
        //All placed into a jpanel - buttonPanel - which has gridbag constraints c2
        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        
        classDropDownPanel = new JPanel();
        
        boxModel = new DefaultComboBoxModel();
        dropDown = new JComboBox(boxModel);
        boxModel.addElement("<Please create a class>");
        dropDown.setPreferredSize(defaultDimension);
        classDropDownPanel.add(dropDown);
        buttonPanel.add(classDropDownPanel, c);
        
        c.gridx = 0; c.gridy = 1;
        
        studentButtonPanel = new JPanel();
        studentButtonPanel.setLayout(new BoxLayout(studentButtonPanel, BoxLayout.PAGE_AXIS));
        addStuBtn = new JButton("Add Student");
        addStuBtn.setPreferredSize(defaultDimension);
        removeStuBtn = new JButton("Remove Student");
        removeStuBtn.setPreferredSize(defaultDimension);
        editStuBtn = new JButton("Edit Student");
        
        studentButtonPanel.add(addStuBtn);
        studentButtonPanel.add(Box.createVerticalStrut(10));
        studentButtonPanel.add(removeStuBtn);
        studentButtonPanel.add(Box.createVerticalStrut(10));
        studentButtonPanel.add(editStuBtn);
        
        studentButtonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Student Buttons"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        addStuBtn.setAlignmentX(SwingConstants.CENTER);
        buttonPanel.add(studentButtonPanel, c);
        
        c.gridx = 0; c.gridy = 2;
        
        classButtonPanel = new JPanel();
        classButtonPanel.setLayout(new BoxLayout(classButtonPanel, BoxLayout.PAGE_AXIS));
        addClassBtn = new JButton("Add Class");
        addClassBtn.setPreferredSize(defaultDimension);
        
        removeClassBtn = new JButton("Remove class");
        removeClassBtn.setPreferredSize(defaultDimension);
        exportBtn = new JButton("Export Class");
        exportBtn.setPreferredSize(defaultDimension);
        sortBtn = new JButton("Sort Class");
        sortBtn.setPreferredSize(defaultDimension);
        
        classButtonPanel.add(addClassBtn);
        classButtonPanel.add(Box.createVerticalStrut(10));
        
        classButtonPanel.add(removeClassBtn);
        classButtonPanel.add(Box.createVerticalStrut(10));
        classButtonPanel.add(exportBtn);
        classButtonPanel.add(Box.createVerticalStrut(10));
        classButtonPanel.add(sortBtn);
        
        classButtonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Class Buttons"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        addStuBtn.setAlignmentX(SwingConstants.CENTER);
        buttonPanel.add(classButtonPanel, c);
        
        
        
        add(buttonPanel);
    }
    
    public void addClassListener(ActionListener listener){
        addClassBtn.addActionListener(listener);
    }
    
    
    public void removeClassListener(ActionListener listener){
        removeStuBtn.addActionListener(listener);
    }
    
    public void exportClassListener(ActionListener listener){
        exportBtn.addActionListener(listener);
    }
    
    public void sortClassListener(ActionListener listener){
        sortBtn.addActionListener(listener);
    }
    
    public void addStudentListener(ActionListener listener){
        addStuBtn.addActionListener(listener);
    }
    
    public void removeStudentListener(ActionListener listener){
        removeStuBtn.addActionListener(listener);
    }
    
    public void editStudentListener(ActionListener listener){
        editStuBtn.addActionListener(listener);
    }
    
    public void dropDownListener(ItemListener listener){
        dropDown.addItemListener(listener);
    }
    
    
    public void updateDropDown(){
        boxModel.removeAllElements();
        int numOfClasses = model.getClassList().size();
        String[] classNames = new String[numOfClasses];
        for(int i = 1;i < numOfClasses + 1; i++){
            boxModel.addElement(new String("Class " + i));
        }
        if(numOfClasses == 0){
            boxModel.addElement("<Please create a class>");
        }
    }
    
}
