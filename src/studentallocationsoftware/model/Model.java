/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.model;

import java.util.ArrayList;
import studentallocationsoftware.model.group_data.UniversityClass;

/**
 *
 * @author Jack
 */
public class Model {
    ArrayList<UniversityClass> classList;
    
    public Model(){
        classList = new ArrayList();
    }
    
    public void addNewClass(){
        classList.add(new UniversityClass(classList.size() + 1));
    }
    
    public ArrayList<UniversityClass> getClassList(){
        return classList;
    }
}
