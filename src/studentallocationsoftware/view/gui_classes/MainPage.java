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
   
    
    private final Dimension dropDownDimension = new Dimension(250, 30);
    private final Dimension buttonDimension = new Dimension(180, 100);
    
    public MainPage(Model model){
        this.model = model;
    }

    public void init() {
        setLayout(new FlowLayout());
        
        //Creates the student list for the left hand side of the screen
        listModel = new DefaultListModel();
        updateList(-1, false);
        studentList = new JList(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(studentList);
        scrollPane.setPreferredSize(new Dimension(250, 400));
        add(scrollPane);
        
        //Creates the drop down list for the right hand side of the display
        rightHandPanel = new JPanel();
        rightHandPanel.setLayout(new BoxLayout(rightHandPanel, BoxLayout.Y_AXIS));
        
        classDropDownPanel = new JPanel();
        classList = new DefaultComboBoxModel();
        classList.addElement("<Please create a class>");
        classDropDown = new JComboBox(classList);
        classDropDown.setPreferredSize(dropDownDimension);
        classDropDownPanel.add(classDropDown);
        classDropDownPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightHandPanel.add(classDropDownPanel);
        rightHandPanel.add(Box.createVerticalStrut(10));
        
        //Creates the button list for the right hand side of the display
        studentButtonPanel = new JPanel();
        studentButtonPanel.setLayout(new BoxLayout(studentButtonPanel, BoxLayout.Y_AXIS));
        addStuBtn = new JButton("Add Student");
        removeStuBtn = new JButton("Remove Student");
        editStuBtn = new JButton("Edit Student");
        
        addButton(addStuBtn, studentButtonPanel);
        studentButtonPanel.add(Box.createVerticalStrut(10));
        addButton(removeStuBtn, studentButtonPanel);
        studentButtonPanel.add(Box.createVerticalStrut(10));
        addButton(editStuBtn, studentButtonPanel);
        
        studentButtonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Student Buttons"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        
        studentButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightHandPanel.add(studentButtonPanel);
        rightHandPanel.add(Box.createVerticalStrut(10));
        
        
        classButtonPanel = new JPanel();
        classButtonPanel.setLayout(new BoxLayout(classButtonPanel, BoxLayout.Y_AXIS));
        classButtonPanel.setAlignmentX(Container.LEFT_ALIGNMENT);
        classButtonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Class Buttons"),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        
        addClassBtn = new JButton("Add Class");     
        removeClassBtn = new JButton("Remove class");
        exportBtn = new JButton("Export Class");
        sortBtn = new JButton("Sort Class");
        
        addButton(addClassBtn, classButtonPanel);
        classButtonPanel.add(Box.createVerticalStrut(10));
        addButton(removeClassBtn, classButtonPanel);
        classButtonPanel.add(Box.createVerticalStrut(10));
        addButton(exportBtn, classButtonPanel);        
        classButtonPanel.add(Box.createVerticalStrut(10));
        addButton(sortBtn, classButtonPanel);
        
        rightHandPanel.add(classButtonPanel);
        
        add(rightHandPanel);
    }
    
    private void addButton(JButton button, JPanel panel){
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(buttonDimension);
        panel.add(button);
    }
    
    //Updates the student list into a one or several groups of students
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
}
