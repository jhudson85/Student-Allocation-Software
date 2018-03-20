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
public class UniversityClass {
    ArrayList<Group> groupList;
    ArrayList<Student> studentList;
    int groupSize;
    int classNumber = 0;
    
    public UniversityClass(int classNumber){
        this.classNumber = classNumber;
    }
}
