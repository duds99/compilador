/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class RegistroLexico {
    private List<Simbolos> reglex;

    public RegistroLexico() {
        reglex = new ArrayList<Simbolos>();
    }
   
    public void mostraReg(){
            for(int i = 0;i<reglex.size();i++){
                System.out.println(reglex.get(i).getLexema()+ " " + reglex.get(i).getToken());
            }
   }       
    
    public void setReg(Simbolos a1) {
        reglex.add(a1);
    }
    
    public void update(int i, Simbolos a1){
        reglex.set(i, a1);
    }
    
    public String getLexema(int pos){
        return reglex.get(pos).getLexema();
    }
    
    public String getToken(int pos){
        return reglex.get(pos).getToken();
    }
    
    public String getLinha(int pos){
        return reglex.get(pos).getLinha();
    }
    
    public int getSize(){
        return reglex.size();
    }
}
