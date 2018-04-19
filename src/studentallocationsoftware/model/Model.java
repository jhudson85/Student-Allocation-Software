/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    
    //Returns an ArrayList of the numbers assigned to the classes in the class list
    private ArrayList<Integer> getClassNumbers(){
        ArrayList<Integer> classNumbers = new ArrayList();
        for(int i = 0; i < classList.size(); i++){
            classNumbers.add(classList.get(i).getClassNumber());
        }
        return classNumbers;
    }
    
    //Creates a university class with the lowest non-taken class number
    public void addUniversityClass(){
        //Attempt to create class with class number of the lowest possible unoccupied class number
        ArrayList<Integer> classNumbers = getClassNumbers();
        for(int i = 1; i <= classList.size() + 1; i++){
            if((!(classNumbers).contains(i))){
                classList.add(new UniversityClass(i));
                break;
            }
        }
    }
    
    public void addUniversityClass(File file){
        UniversityClass uniClass = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            uniClass = (UniversityClass) in.readObject();
            in.close();
            fileIn.close();
      } catch (IOException ex) {
            ex.printStackTrace();
            return;
      } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
      }
        classList.add(uniClass);
    }
    
    public ArrayList<UniversityClass> getClassList(){
        return classList;
    }
}
