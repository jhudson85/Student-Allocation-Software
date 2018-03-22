/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentallocationsoftware;

/**
 *
 * @author Jack
 */
public interface Util {
    
    /*
    * Returns false if the string is compromised of anything other than letters.
    */
        public static boolean isAlpha(String name) {
    char[] chars = name.toCharArray();

    for (char c : chars) {
        if(!Character.isLetter(c)) {
            return false;
        }
    }
    return true;
}
        /*
    * Returns true if the string is a number and false if not
    */
        public static boolean isNumber(String s){
            char[] chars = s.toCharArray();
            if(s.length() == 0){
                return false;
            }
            for(char c: chars){
                if(!Character.isDigit(c)){
                    return false;
                }
            }
            return true;
        }
}
