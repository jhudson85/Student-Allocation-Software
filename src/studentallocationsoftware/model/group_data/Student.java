/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.model.group_data;

/**
 *
 * @author Jack
 */
public class Student implements Comparable<Student>{

    //A boolean array with values representing in order: designer, reporter, tester and programmer.
    private boolean[] preferences;
    private String firstName;
    private String lastName;
    private String studentNumber;
    private int classNumber;
    
    public Student(boolean[] preferences, String firstName, String lastName, String studentNumber){
        this.preferences = preferences;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
    }
    
    public int compareTo(Student other) {
        int res = this.getSkillLevel() - other.getSkillLevel();
        return res;
    }
        
    public int getSkillLevel(){
        int count = 0;
        for(int i = 0; i < preferences.length; i++){
            if(preferences[i]){
                count++;
            }
        }
        return count;
    }
    
     public boolean[] getPreferences() {
        return preferences;
    }

    public void setPreferences(boolean[] preferences) {
        this.preferences = preferences;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }
}
