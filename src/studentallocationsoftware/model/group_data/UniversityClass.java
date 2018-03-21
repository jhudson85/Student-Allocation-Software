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
public class UniversityClass{
    ArrayList<Group> groupList;
    ArrayList<Student> studentList;
    int groupSize;
    int classNumber = 0;
    private int programSkill, reportSkill, designSkill, testingSkill = 0;
    
    public UniversityClass(int classNumber){
        this.classNumber = classNumber;
        this.studentList = new ArrayList();
        this.groupList = new ArrayList();
    }
    
    public void addStudent(Student student){
        studentList.add(student);
    }
    
    public void removeStudent(Student student){
        studentList.remove(student);
    }
    
    public ArrayList<Student> getStudentList(){
        return studentList;
    }
    
    public void addGroup(Group g){
        groupList.add(g);
    }
    
    public void removeAllGroupElements(){
        groupList.clear();
    }
    
    public ArrayList<Group> getGroupList(){
        return groupList;
    }
    
    /*
    * Returns the skill with the lowest to the highest numbers from 0 to 4 respectively.
    */
    public Student.Skills getSkillByLowestQuant(int position){
        if(position == 0){
            //Return the skill with the lowest value
            if(programSkill <= reportSkill && programSkill <= designSkill && programSkill <= testingSkill){
                return Student.Skills.Programming;
            }
            if(reportSkill <= programSkill && reportSkill <= designSkill ){
                
            }
            
        }
        else if(position == 1){
            
        }
        return null;
    }
    
}
