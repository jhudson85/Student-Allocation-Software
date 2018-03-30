/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.view.gui_classes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.model.group_data.Group;
import studentallocationsoftware.model.group_data.Student;
import studentallocationsoftware.model.group_data.UniversityClass;


public class MainPage extends JPanel {
    private JList studentList;
    private JFileChooser fc;
    private JScrollPane scrollPane;
    private DefaultListModel listModel;
    private JButton addStuBtn;
    private JButton removeStuBtn;
    private JButton editStuBtn;
    private JButton addClassBtn;
    private JButton removeClassBtn;
    private JButton exportBtn;
    private JButton sortBtn;
    private JComboBox classDropDown;
    private JPanel studentButtonPanel;
    private JPanel classButtonPanel;
    private JPanel classDropDownPanel;
    private JPanel rightHandPanel;
    private Model model;
    private DefaultComboBoxModel classList;
    ListSelectionModel listSelectionModel;
   
    
    private Dimension defaultDimension;
    private Dimension buttonDimension;
    
    public MainPage(Model model){
        defaultDimension = new Dimension(250, 30);
        buttonDimension = new Dimension(200, 100);
        this.model = model;
    }

    public void init() {
        setLayout(new FlowLayout());
        
        listModel = new DefaultListModel();
        updateList(-1, false);
        studentList = new JList(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(studentList);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        add(scrollPane);
        
        //The code for the right hand side of the display - the button side.
        //All placed into a jpanel - buttonPanel - which has gridbag constraints c
        rightHandPanel = new JPanel();
        rightHandPanel.setLayout(new BoxLayout(rightHandPanel, BoxLayout.Y_AXIS));
        
        classDropDownPanel = new JPanel();
        
        classList = new DefaultComboBoxModel();
        classList.addElement("<Please create a class>");
        classDropDown = new JComboBox(classList);
        classDropDown.setPreferredSize(defaultDimension);
        classDropDownPanel.add(classDropDown);
        classDropDownPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightHandPanel.add(classDropDownPanel);
        
        
        studentButtonPanel = new JPanel();
        BoxLayout bl = new BoxLayout(studentButtonPanel, BoxLayout.Y_AXIS);
        studentButtonPanel.setLayout(bl);
        addStuBtn = new JButton("Add Student");
        addStuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStuBtn.setMaximumSize(buttonDimension);
        removeStuBtn = new JButton("Remove Student");
        removeStuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeStuBtn.setMaximumSize(buttonDimension);
        //removeStuBtn.setPreferredSize(defaultDimension);
        editStuBtn = new JButton("Edit Student");
        editStuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        editStuBtn.setMaximumSize(buttonDimension);
        
        studentButtonPanel.add(addStuBtn);
        studentButtonPanel.add(Box.createVerticalStrut(10));
        studentButtonPanel.add(removeStuBtn);
        studentButtonPanel.add(Box.createVerticalStrut(10));
        studentButtonPanel.add(editStuBtn);
        
        studentButtonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Student Buttons"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        //addStuBtn.setAlignmentX(SwingConstants.CENTER);
        studentButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightHandPanel.add(studentButtonPanel);
        
        
        classButtonPanel = new JPanel();
        classButtonPanel.setLayout(new BoxLayout(classButtonPanel, BoxLayout.PAGE_AXIS));
        addClassBtn = new JButton("Add Class");
        //addClassBtn.setPreferredSize(defaultDimension);
        
        removeClassBtn = new JButton("Remove class");
        removeClassBtn.setPreferredSize(defaultDimension);
        exportBtn = new JButton("Export Class");
        exportBtn.setPreferredSize(defaultDimension);
        sortBtn = new JButton("Sort Class");
        //sortBtn.setPreferredSize(defaultDimension);
        
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
        //addStuBtn.setAlignmentX(SwingConstants.CENTER);
        classButtonPanel.setAlignmentX(Container.LEFT_ALIGNMENT);
        rightHandPanel.add(classButtonPanel);
        
        add(rightHandPanel);
        rightHandPanel.setMaximumSize(defaultDimension);
    }
    
        public void updateList(int classIndex, boolean groupsAdded){
        listModel.removeAllElements();
        UniversityClass uniClass;
        ArrayList<Student> studentList = null;

        if(classIndex != -1){
            uniClass = model.getClassList().get(classIndex);
            studentList = uniClass.getStudentList();
            if(!groupsAdded){
                for(Student s: studentList){
                    listModel.addElement(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber());
                }
            }
            else if(groupsAdded){
            ArrayList<Group> groupList = model.getClassList().get(classIndex).getGroupList();
                for(Group g: groupList){
                    listModel.addElement("Group number: " + g.getGroupNumber());
                    for(Student s: g.getStudentList()){
                        listModel.addElement(s.getFirstName() + " " + s.getLastName() + " - " + s.getStudentNumber());
                    }
                    listModel.addElement("------------");
                }
            }
        }
        if(listModel.isEmpty()){
            listModel.addElement("No Students added");
        }
    }
    
    public String getListElement(int index){
        return listModel.get(index).toString();
    }
    
    public String[] showSaveChooser(){
        fc = new JFileChooser();
        String dir, fileName;
        int returnVal = fc.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            dir = fc.getCurrentDirectory().toString();
            fileName = fc.getSelectedFile().getName();
            System.out.println("Save " + dir + "\\" + fileName);
            return new String[]{fileName, dir};
        }
        return null;
    }
    
    public void addClassListener(ActionListener listener){
        addClassBtn.addActionListener(listener);
    }
    
    public void removeClassListener(ActionListener listener){
        removeClassBtn.addActionListener(listener);
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
        classDropDown.addItemListener(listener);
    }
    
    public void listListener(ListSelectionListener listener){
        listSelectionModel = studentList.getSelectionModel();
        listSelectionModel.addListSelectionListener(listener);
    }
    
    public void updateDropDown(){
        classList.removeAllElements();
        int numOfClasses = model.getClassList().size();
        String[] classNames = new String[numOfClasses];
        for(int i = 1;i < numOfClasses + 1; i++){
            classList.addElement(new String("Class " + i));
        }
        if(numOfClasses == 0){
            classList.addElement("<Please create a class>");
        }
    }
}
