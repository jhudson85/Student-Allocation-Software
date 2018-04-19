/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.model.group_data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author JackDesktop
 */
public abstract class Group implements Serializable{
    protected ArrayList<Student> studentList;
    
    public abstract void addStudent(Student s);
    
    public void removeStudent(Student student){
        studentList.remove(student);
    }
    
    public ArrayList<Student> getStudentList(){
        return studentList;
    }
}
