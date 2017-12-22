/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico;

/**
 *
 * @author Eduardo
 */
public class Simbolos {
   private String token;
   private String lexema;
   private int posicao;
   private String linha;
   private String tipo;
   private String classe;
   private int endereco;

    public Simbolos() {
        this.token = null;
        this.lexema = null;
        this.posicao = 0;
        this.linha = null;
        this.tipo = null;
        this.classe = null;
        this.endereco = 0;
    }
   
    public Simbolos(String lexema, String token, int posicao, String linha, String classe, String tipo, int endereco) {
        this.token = token;
        this.lexema = lexema;
        this.posicao = posicao;
        this.linha = linha;        
        this.classe = classe;
        this.tipo = tipo;
        this.endereco = endereco;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the lexema
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * @param lexema the lexema to set
     */
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    /**
     * @return the posicao
     */
    public int getPosicao() {
        return posicao;
    }

    /**
     * @param posicao the posicao to set
     */
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
    
    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }
    
    public void setClasse(String classe){
        this.classe = classe;
    }
    
    public String getClasse(){
        return classe;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setEndereco(int endereco){
        this.endereco = endereco;
    }
    
    public int getEndereco(){
        return endereco;
    }
}
