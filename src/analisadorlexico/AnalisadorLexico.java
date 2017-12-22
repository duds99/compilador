/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico;

import static Main.LC.tabela;
import static Main.LC.reglex;
import static Main.LC.end;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Eduardo de Figueiredo Júnior, Matheus de Souza, Pedro Hardeman
 */
public class AnalisadorLexico {

    char letra[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    char letraM[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    char digito[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    char simbolo[] = {'\n', ' ', '_', '.', ',', ';', ':', '(', ')', '{', '}', '[', ']', '+', '>', '<','*','-'};
    int cont = 0; //conta os erros
    static String arquivo;
    String tipo = "";
    
    public AnalisadorLexico() {
    }

    public boolean lerArquivo() throws IOException{
        boolean resp = false;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String linha = in.readLine();
        int numLinha = 1;
        arquivo = linha;
        
        while (linha != null) {
            analisa(linha, numLinha);
            linha = in.readLine();
            arquivo += " \n" + linha;
            numLinha++;
        }   
        if(cont==0){
            resp = true;
        }
        return resp;
    }
    
    public boolean contem(char vet[], char c, int tamanho) {
        boolean resp = false;
        for (int i = 0; i < tamanho; i++) {
            if (c == vet[i]) {
                resp = true;
            }
        }
        return resp;
    } 
    
    public void analisa(String linha, int numLin) {
        linha += "  ";
        char atual = linha.charAt(0);
        int i = 0;
        String token = "";
        int estado = 0;
        while(i<linha.length()-1){
        switch (estado) {
                case 0:
                    if (atual == '=') {
                        estado = 3;
                        token += "=";
                    }else if (atual == '/') {
                        estado = 1;
                        token += atual;
                    } else if (contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) { //OU 26 se der errado
                        estado = 7;
                        token += atual;
                    } else if (contem(digito, atual, digito.length)) {
                        estado = 4;
                        token += atual;
                    } else if (contem(simbolo, atual, simbolo.length)) {
                        estado = 99;
                        token += atual;
                    } else if (atual == '"'){
                        estado = 13;
                        token += atual;
                    } else {
                        erro(numLin + ": caractere invalido.");
                        cont++;
                        i = linha.length()-2;
                    }
                    break;
                case 1:
                    if (atual == '/') {
                        estado = 2;
                        token = "";
                    } else {
                        token = "/";
                        i--;
                        estado = 99;
                    }
                    break;
                case 2:
                    if (atual != '\n') { // arrumar o \n kkk
                        estado = 2;
                    } else {
                        estado = 0;
                    }
                    break;
                case 3:
                    if (atual == '=') {
                        token += atual;
                        estado = 99;
                    } else {
                        i--;
                        estado = 99;
                    }
                    break;
                case 4:
                    if (contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                        token += atual;
                        estado = 5;
                    } else if (contem(digito, atual, digito.length)) {
                        token += atual;
                        estado = 6;
                    } else {
                        i--;
                        estado = 99;
                        tipo = "constante";
                    }
                    break;
                case 5:
                    if (atual == 'h') {
                        token += atual;
                        estado = 99;
                        tipo = "constante";
                    } else {
                        i = linha.length()-2;
                        erro(numLin + ": fim de arquivo nao esperado.");
                        cont++;
                        token = "";
                    }
                    break;
                case 6:
                    if (contem(digito, atual, digito.length)) {
                        token += atual;
                        estado = 10;
                    } else if (atual == 'h') {
                        token += atual;
                        estado = 99;
                        tipo = "constante";
                    } else if (contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                        erro(numLin + ": fim de arquivo nao esperado.");
                        cont++;
                        token = "";
                        i = linha.length()-2;
                    } else {
                        i--;
                        estado = 99;
                        tipo = "constante";
                    }
                    break;
                case 7:
                    if (contem(digito, atual, digito.length)) {
                        token += atual;
                        estado = 11;
                    } else if (contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                        token += atual;
                        estado = 8;
                    } else {
                        i--;
                        estado = 99;
                        tipo = "identificador";
                    }
                    break;
                case 8:
                    if (atual == 'h') {
                        token += atual;
                        estado = 99;
                        tipo = "constante";
                    } else if (contem(digito, atual, digito.length) || contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                        token += atual;
                        estado = 9;
                    } else { 
                        i--;
                        estado = 99;
                        tipo = "identificador";
                    }
                    break;
                case 9:
                    if (contem(digito, atual, digito.length) || contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                        token += atual;
                        estado = 9;
                    } else {
                        i--;
                        estado = 99;
                        tipo = "identificador";
                    }
                    break;
                case 10:
                    if (contem(digito, atual, digito.length)) {
                        token += atual;
                        estado = 10;
                    } else {
                        if (contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                            token = "";
                            erro(numLin + ": fim de arquivo nao esperado.");
                            i = linha.length()-2;
                            cont++;
                        }
                        i--;
                        estado = 99;
                        tipo = "constante";
                    }
                    break;
                case 11:
                    if (atual == 'h') {
                        token += atual;
                        i--;         
                        estado = 99;
                        tipo = "constante";
                    } else if (contem(digito, atual, digito.length) || contem(letra, atual, letra.length) || contem(letraM, atual,letraM.length)) {
                        token += atual;
                        estado = 9;
                    } else {
                        i--;
                        estado = 99;
                    }
                    break;
                case 12:
                    atual = linha.charAt(i);
                    if(contem(digito,atual,digito.length)){
                        token += atual;
                        estado = 10;
                    }
                    else{
                        i--;
                        estado = 99;
                    }
                    break;
                case 13:
                    if(atual != '"'){
                        token += atual;
                        estado = 13;
                    }
                    else{
                        token += '"';
                        
                        estado = 99;
                        tipo = "constante";
                    }
                    break;
            }  
            if(estado == 99){
                String resp = tabela.getTabela(token);
                if(token.length()>0){
                    if(!(token.length()== 1 && token.charAt(0)== ' ')){
                        if (resp.compareTo("NULL") == 0) {
                            end++;
                            Simbolos a1 = new Simbolos(token, tipo, end,"","","",0);
                            tabela.setTabela(a1);
                            String aux = "" + numLin;
                            a1 = new Simbolos(token, tipo,0,aux,"","",0);
                            reglex.setReg(a1);
                        }
                        else{
                            String aux = "" + numLin;
                            Simbolos a2 = new Simbolos(token,token.toUpperCase(),0,aux,"","",0);
                            reglex.setReg(a2);
                        }
                    }
                }
                token = "";
                estado = 0;
            }
            i++;
            atual = linha.charAt(i);
        }
    }
    
    public void erro(String mostra){
        System.out.println(mostra);
        System.exit(0);
    }
}
