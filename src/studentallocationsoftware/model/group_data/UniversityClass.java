/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.model.group_data;

import java.util.ArrayList;

public class UniversityClass extends Group{
    ArrayList<StudentGroup> groupList;
    int classNumber = 0;
    
    public UniversityClass(int classNumber){
        this.classNumber = classNumber;
        studentList = new ArrayList();
        groupList = new ArrayList();
    }
    
    public void addStudent(Student student){
        studentList.add(student);
    }
    
    public ArrayList<Student> getStudentList(){
        return studentList;
    }
    
    public void addGroup(StudentGroup g){
        groupList.add(g);
    }
    
    public void removeAllGroupElements(){
        groupList.clear();
    }
    
    public ArrayList<StudentGroup> getGroupList(){
        return groupList;
    }
    
    public Student getStudent(String studentNumber){
        for(Student s: studentList){
            if(s.getStudentNumber().equals(studentNumber)){
                return s;
            }
        }
        return null;
    }
    
    public int getClassNumber(){
        return classNumber;
    }
}
