/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.model.group_data;

import java.util.ArrayList;

/**
 *
 * @author Jack
 */
public class Group {
    private ArrayList<Student> studentList;
    private int groupNumber;

    
    public Group(int groupNumber){
        this.groupNumber = groupNumber;
        studentList = new ArrayList();
    }
    
    public void addStudent(Student student){
        studentList.add(student);
    }
    
    public void removeStudent(Student student){
        studentList.remove(student);
    }
    
        public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
