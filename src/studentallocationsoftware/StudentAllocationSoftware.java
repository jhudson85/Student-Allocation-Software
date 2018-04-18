/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware;

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
    }
}
