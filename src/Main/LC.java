package Main;
import analisadorlexico.*;
import analisadorsintatico.*;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo de Figueiredo Júnior, Matheus de Souza, Pedro Hardeman
 */
public class LC {
    
    public static TabelaSimbolos tabela = new TabelaSimbolos();
    public static RegistroLexico reglex = new RegistroLexico();
    public static int end = 34;
    
    public LC(){
        Simbolos a1 = new Simbolos("const","CONST",1,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("integer","INTEGER",2,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("byte","BYTE",3,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("string","STRING",4,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("while","WHILE",5,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("if","IF",6,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("else","ELSE",7,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("and","AND",8,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("or","OR",9,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("not","NOT",10,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("==","==",11,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("=","=",12,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("(","(",13,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos(")",")",14,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("<","<",15,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos(">",">",16,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("<>","<>",17,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos(">=",">=",18,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("<=","<=",19,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos(",",",",20,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("+","+",21,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("-","-",22,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("*","*",23,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("/","/",24,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos(";",";",25,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("{","{",26,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("}","}",27,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("readln","READLN",28,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("write","WRITE",29,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("writeln","WRITELN",30,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("TRUE","TRUE",31,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("FALSE","FALSE",32,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("boolean","BOOLEAN",33,"","","",0);
        tabela.setTabela(a1);
        a1 = new Simbolos("then","THEN",34,"","","",0);
        tabela.setTabela(a1);
    }
   
    public void arrumareg(){
       int tamanho = reglex.getSize();
       
       for(int i=0;i<tamanho;i++){
           String tipo = tabela.getToken(reglex.getLexema(i));
           Simbolos a1 = new Simbolos(reglex.getLexema(i), tipo,-1,reglex.getLinha(i),"","",0);
           reglex.update(i, a1);
       }
       //reglex.mostraReg();
    }
    
    public static void main(String [] args) throws IOException{
        LC a1 = new LC();
        AnalisadorLexico a2 = new AnalisadorLexico();
        boolean resp = a2.lerArquivo();
        a1.arrumareg();
        
        if(resp == true){
            System.out.println("Analise lexica feita com sucesso!");
            AnalisadorSintatico a3 = new AnalisadorSintatico();//Sintatito e semantico
            System.out.print("Analise sintatica e semantica feita com sucesso!");
        }
    }    
}
