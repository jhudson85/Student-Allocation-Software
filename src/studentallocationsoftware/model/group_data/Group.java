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
public class Group implements Comparable<Group>{
    private ArrayList<Student> studentList;
    private int groupNumber;
    private int programSkill, reportSkill, designSkill, testingSkill = 0;
    
    public Group(int groupNumber){
        this.groupNumber = groupNumber;
        studentList = new ArrayList();
    }
    
    public void addStudent(Student student){
        boolean[] preferences = student.getPreferences();
        if(preferences[0] == true){
            designSkill++;
        }
        if(preferences[1] == true){
            reportSkill++;
        }
        if(preferences[2] == true){
            testingSkill++;
        }
        if(preferences[3] == true){
            programSkill++;
        }
        studentList.add(student);
    }
    
    public void removeStudent(Student student){
        studentList.remove(student);
    }

    public int getProgramSkill() {
        return programSkill;
    }

    public void setProgramSkill(int programSkill) {
        this.programSkill = programSkill;
    }

    public int getReportSkill() {
        return reportSkill;
    }

    public void setReportSkill(int reportSkill) {
        this.reportSkill = reportSkill;
    }

    public int getDesignSkill() {
        return designSkill;
    }

    public void setDesignSkill(int designSkill) {
        this.designSkill = designSkill;
    }

    public int getTestingSkill() {
        return testingSkill;
    }

    public void setTestingSkill(int testingSkill) {
        this.testingSkill = testingSkill;
    }
    
    public int totalSkillPoints(){
        return programSkill + reportSkill + designSkill + testingSkill;
    }

    public int compareTo(Group other) {
        int res = other.totalSkillPoints() - this.totalSkillPoints();
        return res;
    }
    
    public int getGroupNumber(){
        return groupNumber;
    }
    
    public ArrayList<Student> getStudentList(){
        return studentList;
    }
}
