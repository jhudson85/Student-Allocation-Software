/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware;

import java.io.File;
import studentallocationsoftware.controller.Controller;
import studentallocationsoftware.model.Model;
import studentallocationsoftware.view.View;

/**
 *
 * @author Jack
 */
public class StudentAllocationSoftware {
    public static void main(String[] args) {
        Model model = new Model();
        
        View view = new View(model);
        view.init();
        
        Controller cont = new Controller(model, view);
        
        File file = new File(System.getProperty("user.home") + File.separator + "/.sas");
        if(file.exists()){
            File[] fileList = file.listFiles();
            for(File f : fileList){
                model.addUniversityClass(f);
            }
            if(fileList.length > 0){
                cont.selectedClassIndex = 0;
                view.getMainPage().updateDropDown();
                view.getMainPage().updateList(0, false);
            }
        }
        
    }
}
