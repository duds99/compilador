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
public class TabelaSimbolos {
    public List<Simbolos> tabela;

    public TabelaSimbolos() {
        tabela = new ArrayList<Simbolos>();
    }
    
    public String getTabela(String lexema){
        String resp = "NULL";
        
        for(int i=0;i<tabela.size();i++){
            if(lexema.compareTo(tabela.get(i).getLexema())==0){
                resp = "" + tabela.get(i).getPosicao();
            }
        }
        return resp;
    }
    
    public String getToken(String lexema){
        String resp = "";
        
        for(int i=0;i<tabela.size();i++){
            if(lexema.compareTo(tabela.get(i).getLexema())==0){
                resp = "" + tabela.get(i).getToken();
            }
        }
        return resp;
    }
    
    public void setSimbolo(String lexema,String classe, String tipo, int endereco){
        Simbolos a1 = new Simbolos();
        
        for(int i=0;i<tabela.size();i++){
            if(lexema.compareTo(tabela.get(i).getLexema())==0){
                tabela.get(i).setClasse(classe);
                tabela.get(i).setTipo(tipo);
                tabela.get(i).setEndereco(endereco);
            }
        }     
    }
    
   
    public void mostra(){
            for(int i = 0;i<tabela.size();i++){
                System.out.println(tabela.get(i).getLexema()+ " " + tabela.get(i).getToken() + " " + tabela.get(i).getPosicao() + " "  + tabela.get(i).getClasse() + " " + tabela.get(i).getTipo() + " " + tabela.get(i).getEndereco());
            }
    }       
    
    public void setTabela(Simbolos a1) {
        tabela.add(a1);
    }
    
    public int getSize(){
        return tabela.size();
    }
}
