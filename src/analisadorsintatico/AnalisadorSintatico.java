/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintatico;

import static Main.LC.reglex;
import static Main.LC.tabela;
import analisadorlexico.Simbolos;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Eduardo
 */
public class AnalisadorSintatico {
    String tipo[] = {"BYTE","INTEGER","BOOLEAN","STRING","CONST"};
    String comandos[] = {"identificador","WHILE","IF","WRITE","READLN","WRITELN","{",";"};
    String leitura[] = {"READLN","WRITE","WRITELN"};
    String tok_esp;
    String linha = " ";
    String salvatipo = "";
    String salvaclasse = "";
    String tiponovo = "";
    String geracao = "";
    int endereco = 10000;
    int pos;
    FileWriter arq;
    PrintWriter gravarArq;
          
    
    public AnalisadorSintatico() throws IOException{
        arq =  new FileWriter("C:\\Users\\Eduardo\\Documents\\NetBeansProjects\\CompiladorLC\\teste.ASM");
        gravarArq = new PrintWriter(arq);
        this.pos = 0;
        this.tok_esp = reglex.getToken(pos);
        gravarArq.printf("sseg SEGMENT STACK\n");
        gravarArq.printf("\tbyte 10000 DUP(?)\n");
        gravarArq.printf("sseg ENDS\n\n");
        gravarArq.printf("dseg SEGMENT PUBLIC\n");
        gravarArq.printf("\tbyte 10000 DUP(?)\n");
        S();
        gravarArq.printf("cseg ENDS\n");
        gravarArq.printf("END strt");
        tabela.mostra();
        arq.close();
    }
    
    public boolean pertencetipo(String tok_esp){
        boolean resp = false;
        
        for(int i = 0;i<tipo.length;i++){
            if(tok_esp.compareTo(tipo[i])==0){
                resp = true;
            }    
        }    
        return resp;
    }
    
    public boolean pertenceComandos(String tok_esp){
        boolean resp = false;
        
        for(int i = 0;i<comandos.length;i++){
            if(tok_esp.compareTo(comandos[i])==0){
                resp = true;
            }    
        }    
        return resp;
    }
    
    public boolean pertenceLeitura(String tok_esp){
        boolean resp = false;
        
        for(int i = 0;i<leitura.length;i++){
            if(tok_esp.compareTo(leitura[i])==0){
                resp = true;
            }    
        }    
        return resp;
    }
    
    public void prox(){
        int tamanho = reglex.getSize();
        pos++;
        if(pos < tamanho){
            tok_esp = reglex.getToken(pos);
        }
    }
    
    //repetidor de D e 
    int flag = 0;
    public void S(){
        while(pos < reglex.getSize()-1){
            if(pertenceComandos(tok_esp)){
                if(flag==0){
                    gravarArq.printf("dseg ENDS\n\n");
                    gravarArq.printf("cseg SEGMENT PUBLIC\n");
                    gravarArq.printf("ASSUME CS:cseg, DS:dseg\n\n");
                    gravarArq.printf("_strt:\n");
                    gravarArq.printf("\tmov ax, dseg\n");
                    gravarArq.printf("\tmov ds, ax\n\n");
                    flag = 1;
                }    
                C();
            }
            else if(pertencetipo(tok_esp)){
                D();
                flag = 0;
            }
            else{
                
            }
        }
    }
    
    //mexemos
    public void C(){
        if(tok_esp.compareTo("{")==0){
	        CasaT("{");
	        while(pertenceComandos(tok_esp)){
	            if(tok_esp.compareTo("identificador")==0){
	                CA();
	            }
	            else if(tok_esp.compareTo("WHILE")==0){
	                CR();
	            }
	            else if(tok_esp.compareTo("IF")==0){
	                CT();
	            }
	            else if(pertenceLeitura(tok_esp)){
	                CL();
	            }
                    else if(tok_esp.compareTo(";")==0){
                        CN();
                    }
                    else{
                        C();
                    }
	        }    
	        CasaT("}");
        }
        else{
        	if(pertenceComandos(tok_esp)){
	        	if(tok_esp.compareTo("identificador")==0){
	                CA();
	            }
	            else if(tok_esp.compareTo("WHILE")==0){
	                CR();
	            }
	            else if(tok_esp.compareTo("IF")==0){
	                CT();
	            }
                    else if(pertenceLeitura(tok_esp)){
	                CL();
	            }
                   else if(tok_esp.compareTo(";")==0){
                        CN();
                    }
                    else{
                        C();
                    } 
	            //CasaT(";");
            }      
        }
    }
    
    public void CN(){
        CasaT(";");
    }
    //mexemos
    public void CA(){
        String resp = tabela.getTabela(reglex.getLexema(pos));
        int val = Integer.parseInt(resp);
        Simbolos a1 = tabela.tabela.get(val-1);
        salvatipo = a1.getTipo();
        salvaclasse = a1.getClasse();
        if(salvatipo.compareTo("")==0){
            errotipo(2);//NAO DECLARADO
        }
        if(!(salvaclasse.compareTo("const")==0)){
            CasaT("identificador");
            CasaT("=");
            EXP();
            CasaT(";");
        }
        else{
            errotipo(4);
        }
    }
    
    //mexemos
    public void CR(){
        salvatipo = "";
        salvaclasse = "";
        CasaT("WHILE");
        String resp = tabela.getTabela(reglex.getLexema(pos));
        int val = Integer.parseInt(resp);
        Simbolos a1 = tabela.tabela.get(val-1);
        salvatipo = a1.getTipo();
        salvaclasse = a1.getClasse();
        EXP();
        C(); 
    }    
    
    //mexemos
    public void CT(){
        salvatipo = "";
        salvaclasse = "";
        CasaT("IF");
        String resp = tabela.getTabela(reglex.getLexema(pos));
        int val = Integer.parseInt(resp);
        Simbolos a1 = tabela.tabela.get(val-1);
        salvatipo = a1.getTipo();
        salvaclasse = a1.getClasse();
        EXP();
        CasaT("THEN");
        C();
        if(tok_esp.compareTo("ELSE")==0){
            CasaT("ELSE");
            C();
        }
    }
    
    //mexemos
    public void CL(){
        salvatipo = "";
        salvaclasse = "";
        if(pertenceLeitura(tok_esp)){
            if(tok_esp.compareTo("WRITE")==0){
                endereco += 1;
                gravarArq.printf("dseg SEGMENT PUBLIC\n");
                CasaT("WRITE");
                CasaT(",");

                gravarArq.printf("\tbyte " +reglex.getLexema(pos) +"\ndseg ENDS\n\n");
                EXP();
                while(tok_esp.compareTo(",")==0){
                	CasaT(",");
                	EXP();
                }
                CasaT(";");
                
            }
            else if(tok_esp.compareTo("READLN")==0){
                CasaT("READLN");
                CasaT(",");
                CasaT("identificador");
                CasaT(";");
            }
            else{
                CasaT("WRITELN");
                CasaT(",");
                EXP();
                while(tok_esp.compareTo(",")==0){
                	CasaT(",");
                	EXP();
                }
                CasaT(";");
            }
        }
    }
    
    //mexemos
    public void D(){
    	if(!(tok_esp.compareTo("identificador")==0)){
            if(pertencetipo(tok_esp)){
                if(tok_esp.compareTo("BYTE")==0){
                    CasaT("BYTE");
                    salvatipo = "BYTE";
                    salvaclasse = "var";
                    endereco += 1;
                    geracao = "\tbyte ?\n";
                }
                else if(tok_esp.compareTo("INTEGER")==0){
                    CasaT("INTEGER");
                    salvatipo = "INTEGER";
                    salvaclasse = "var";
                    endereco += 2;
                    geracao = "\tsword ?\n";
                }
                else if(tok_esp.compareTo("BOOLEAN")==0){
                    CasaT("BOOLEAN"); 
                    salvatipo = "BOOLEAN";
                    salvaclasse = "var";
                    endereco += 1;
                    geracao = "\tbyte ?\n";
                }
                else if(tok_esp.compareTo("STRING")==0){
                   CasaT("STRING");
                   salvatipo = "STRING";
                   salvaclasse = "var";
                   endereco += 256;
                   geracao = "\tbyte 256 DUP(?)\n";
                }
                else if(tok_esp.compareTo("CONST")==0){
                    CasaT("CONST");
                    salvatipo = "const";
                    salvaclasse = "const";
                }
                if(!(salvatipo.compareTo("const")==0)){
                    String resp = tabela.getTabela(reglex.getLexema(pos));
                    int val = Integer.parseInt(resp);
                    Simbolos a1 = tabela.tabela.get(val-1);
                    String tipo = a1.getTipo();
                    if(!(tipo.compareTo("")==0)){errotipo(3);}
                    tabela.setSimbolo(reglex.getLexema(pos),salvaclasse,salvatipo,endereco);
                }
                
                CasaT("identificador");
                if(tok_esp.compareTo("=")==0){
                    CasaT("=");
                    if(tok_esp.compareTo("-")==0){
                        CasaT("-");
                    }
                    else if(tok_esp.compareTo("FALSE")==0){
                        if(salvatipo.compareTo("const")==0){
                            String resp = tabela.getTabela(reglex.getLexema(pos-2));
                            int val = Integer.parseInt(resp);
                            Simbolos a1 = tabela.tabela.get(val-1);
                            String tipo = a1.getTipo();
                            if(!(tipo.compareTo("")==0)){errotipo(3);}
                            endereco += 1;
                            geracao = "\tbyte 0h\n";
                            tabela.setSimbolo(reglex.getLexema(pos-2),salvaclasse,"BOOLEAN",endereco); 
                        }
                         else{
                           if(!(salvatipo.compareTo("BOOLEAN")==0)){
                               errotipo(1);//incompativeis
                           } 
                        }
                        CasaT("FALSE"); 
                    }
                    else if(tok_esp.compareTo("TRUE")==0){
                        if(salvatipo.compareTo("const")==0){
                            String resp = tabela.getTabela(reglex.getLexema(pos-2));
                            int val = Integer.parseInt(resp);
                            Simbolos a1 = tabela.tabela.get(val-1);
                            String tipo = a1.getTipo();
                            if(!(tipo.compareTo("")==0)){errotipo(3);}
                            endereco += 1;
                            geracao = "\tbyte FFh\n";
                            tabela.setSimbolo(reglex.getLexema(pos-2),salvaclasse,"BOOLEAN",endereco); 
                        }
                        else{
                           if(!(salvatipo.compareTo("BOOLEAN")==0)){
                               errotipo(1);//incompativeis
                           } 
                        }
                        CasaT("TRUE");
                    }
                    if(tok_esp.compareTo("constante")==0){
                        if(salvatipo.compareTo("const")==0){
                           String resp = tabela.getTabela(reglex.getLexema(pos));
                           int val = Integer.parseInt(resp);
                           Simbolos a1 = tabela.tabela.get(val-1);
                           String lex = a1.getLexema();
                           
                            resp = tabela.getTabela(reglex.getLexema(pos-2));
                            val = Integer.parseInt(resp);
                            a1 = tabela.tabela.get(val-1);
                            String tipo = a1.getTipo();
                            if(!(tipo.compareTo("")==0)){errotipo(3);}
                           
                           if(lex.charAt(0)=='"'){
                               salvatipo = "STRING";
                               endereco += 256;
                               geracao = "\tbyte 256 DUP("+ reglex.getLexema(pos) + ")\n";
                           }
                           else if(lex.charAt(lex.length()-1)=='h'){
                               salvatipo = "BYTE";
                               endereco += 1;
                               geracao = "\tbyte " + reglex.getLexema(pos) + "\n";
                           }
                           else{
                               salvatipo = "INTEGER";
                               endereco += 2;
                               geracao = "\tbyte " + reglex.getLexema(pos) + "\n";
                           }
                           
                           tabela.setSimbolo(reglex.getLexema(pos-2),salvaclasse,salvatipo,endereco);

                        }
                        else{
                           String resp = tabela.getTabela(reglex.getLexema(pos));
                           int val = Integer.parseInt(resp);
                           Simbolos a1 = tabela.tabela.get(val-1);
                           String lex = a1.getLexema();
                           
                           if(lex.charAt(0)=='"'){
                               tiponovo = "STRING";
                               //endereco += 256;
                               geracao = "\tbyte 256 DUP(" + reglex.getLexema(pos) + ")\n";
                           }
                           else if(lex.charAt(lex.length()-1)=='h'){
                               tiponovo = "BYTE";
                               //endereco += 1;
                               geracao = "\tbyte " + reglex.getLexema(pos) + "\n";
                           }
                           else{
                               tiponovo = "INTEGER";
                              // endereco += 2;
                              geracao = "\tsword " + reglex.getLexema(pos) + "\n";
                           }
                           if(!(salvatipo.compareTo(tiponovo)==0)){
                               errotipo(1);//incompativeis
                           }
                            
                        }
                        CasaT("constante");
                    }
                }
            }
            while(tok_esp.compareTo(",")==0){
                CasaT(",");
                if(!(salvatipo.compareTo("const")==0)){
 
                    tabela.setSimbolo(reglex.getLexema(pos),salvaclasse,salvatipo,endereco);
                }
                
                String resp = tabela.getTabela(reglex.getLexema(pos));
                int val = Integer.parseInt(resp);
                Simbolos a1 = tabela.tabela.get(val-1);
                String tipo = a1.getTipo();
                if(!(tipo.compareTo("")==0)){errotipo(3);}
                
                
                CasaT("identificador");
                if(tok_esp.compareTo("=")==0){
                    CasaT("=");
                    if(tok_esp.compareTo("-")==0){
                        CasaT("-");
                    }
                    else if(tok_esp.compareTo("FALSE")==0){
                        if(salvatipo.compareTo("const")==0){
                            endereco += 1;
                            geracao = "\tbyte 0h\n";
                           tabela.setSimbolo(reglex.getLexema(pos-2),salvaclasse,"BOOLEAN",endereco); 
                        }
                        CasaT("FALSE");
                    }
                    else if(tok_esp.compareTo("TRUE")==0){
                        if(salvatipo.compareTo("const")==0){
                            endereco += 1;
                            geracao = "\tbyte FFh\n";
                           tabela.setSimbolo(reglex.getLexema(pos-2),salvaclasse,"BOOLEAN",endereco); 
                        }
                        CasaT("TRUE");
                    }
                    if(tok_esp.compareTo("constante")==0){
                         if(salvatipo.compareTo("const")==0){
                           resp = tabela.getTabela(reglex.getLexema(pos));
                           val = Integer.parseInt(resp);
                           a1 = tabela.tabela.get(val-1);
                           String lex = a1.getLexema();
                           
                           if(lex.charAt(0)=='"'){
                               salvatipo = "STRING";
                               endereco += 256;
                               geracao = "\tbyte 256 DUP("+ reglex.getLexema(pos) + ")\n";
                           }
                           else if(lex.charAt(lex.length()-1)=='h'){
                               salvatipo = "BYTE";
                               endereco += 1;
                               geracao = "\tbyte " + reglex.getLexema(pos) + "\n";
                           }
                           else{
                               salvatipo = "INTEGER";
                               endereco += 2;
                               geracao = "\tbyte " + reglex.getLexema(pos) + "\n";
                           }
                           tabela.setSimbolo(reglex.getLexema(pos-2),salvaclasse,salvatipo,endereco); 
                        }
                        CasaT("constante");
                    }
                }
            }
            
            CasaT(";");
        }else{
            CA();
        }
        gravarArq.printf(geracao);
    }
    
    public void F(){
    	if(tok_esp.compareTo("constante")==0){
    		String resp = tabela.getTabela(reglex.getLexema(pos));
                int val = Integer.parseInt(resp);
                Simbolos a1 = tabela.tabela.get(val-1);
                String lex = a1.getLexema();
                
                if(lex.charAt(0)=='"'){
                    tiponovo = "STRING";
                }
                else if(lex.charAt(lex.length()-1)=='h'){
                    tiponovo = "BYTE";
                }
                else{
                    tiponovo = "INTEGER";
                }
                if(!(salvatipo.compareTo("")==0)){
                    if(!(salvatipo.compareTo(tiponovo)==0)){
                        errotipo(1);
                    }
                }
                CasaT("constante");
    	}
    	else if(tok_esp.compareTo("identificador")==0){
                String resp = tabela.getTabela(reglex.getLexema(pos));
                int val = Integer.parseInt(resp);
                Simbolos a1 = tabela.tabela.get(val-1);
                tiponovo = a1.getTipo();
                if(!(salvatipo.compareTo("")==0)){
                    if(!(salvatipo.compareTo(tiponovo)==0)){
                        errotipo(1);
                    }
                }
    		CasaT("identificador");
    	}
    	else if(tok_esp.compareTo("NOT")==0){
    		CasaT("NOT");
    		F();
    	}
        else if(tok_esp.compareTo("FALSE")==0){
            if(!(salvatipo.compareTo("")==0)){
                if(!(salvatipo.compareTo("BOOLEAN")==0)){
                        errotipo(1);
                }
            }
            CasaT("FALSE");
        }
        else if(tok_esp.compareTo("TRUE")==0){
            if(!(salvatipo.compareTo("")==0)){
                if(!(salvatipo.compareTo("BOOLEAN")==0)){
                        errotipo(1);
                }
            }
            CasaT("TRUE");
        }
    	else if(tok_esp.compareTo("(")==0){
    		CasaT("(");
    		EXP();
    		CasaT(")");
    	}
        else{
            linha = "" + reglex.getLinha(pos);
            pos = reglex.getSize();
        }
    }

    public void T(){
    	F();
    	while(tok_esp.compareTo("*")==0 || tok_esp.compareTo("/")==0 || tok_esp.compareTo("AND")==0){
    		if(tok_esp.compareTo("*")==0){
    			CasaT("*");
    		}
    		else if(tok_esp.compareTo("/")==0){
    			CasaT("/");
    		}
    		else if(tok_esp.compareTo("AND")==0){
    			CasaT("AND");
    		}
    		F();
    	}
    }

    public void EXP(){
    	EXPS();
    	while(tok_esp.compareTo("<")==0 || tok_esp.compareTo(">")==0  || tok_esp.compareTo("==")==0){
    		if(tok_esp.compareTo("<")==0){
    			CasaT("<");
    			if(tok_esp.compareTo("=")==0){
    				CasaT("=");
    			}
    			else if(tok_esp.compareTo(">")==0){
    				CasaT(">");
    			}
    		}
    		else if(tok_esp.compareTo(">")==0){
    			CasaT(">");
    			if(tok_esp.compareTo("=")==0){
    				CasaT("=");
    			}
    		}
    		else if(tok_esp.compareTo("==")==0){
    			CasaT("==");
    		}
    		EXPS();
    	}
    }

    public void EXPS(){
    	if(tok_esp.compareTo("+")==0 || tok_esp.compareTo("-")==0){
    		if(tok_esp.compareTo("+")==0){
    			CasaT("+");
    		}
    		else{
    			CasaT("-");
    		}
    	}
    	T();
    	while(tok_esp.compareTo("+")==0 || tok_esp.compareTo("-")==0 || tok_esp.compareTo("OR")==0){
    		if(tok_esp.compareTo("+")==0){
    			CasaT("+");
    		}
    		else if(tok_esp.compareTo("-")==0){
    			CasaT("-");
    		}
    		else if(tok_esp.compareTo("OR")==0){
    			CasaT("OR");
    		}
    		T();
    	}
    }
    
    public void errotipo(int tipo){
        if(tipo == 1){
            System.out.println(reglex.getLinha(pos) + " :tipos incompatíveis [" + reglex.getLexema(pos) + "]");
        }
        else if(tipo == 2){
            System.out.println(reglex.getLinha(pos) + " :identificador nao declarado [" + reglex.getLexema(pos) + "]");
        }
        else if(tipo == 3){
            System.out.println(reglex.getLinha(pos) + " :identificador já declarado [" + reglex.getLexema(pos) + "]");
        }
        else{
            System.out.println(reglex.getLinha(pos) + " :classe de identificador incompatível [" + reglex.getLexema(pos) + "]");
        }
        System.exit(0);
    }
    
    //mudar
    public void CasaT(String tok_esp){
        if(pos<reglex.getSize()){
            if(tok_esp.compareTo(reglex.getToken(pos))==0){
                prox();
            }
            else{
                if(tok_esp.compareTo("}")==0){
                    int lin = Integer.parseInt(reglex.getLinha(pos)) - 1;
                    System.out.println(lin + ":fim de arquivo nao esperado.");
                }
                else{
                    linha = null;
                    int lin = Integer.parseInt(reglex.getLinha(pos));
                    linha = "" + lin;
                    System.out.println(linha + ":erro sintatico no casamento de token: " + tok_esp);
                    pos = reglex.getSize();  
                }
                System.exit(0);
            }
        }
        else{
            if(tok_esp.compareTo("}")==0){
                System.out.println(tok_esp + ":fim de arquivo nao esperado.");
            }
            else{
                System.out.println(linha + ":erro sintatico no casamento de token: " + tok_esp);
            }    
            System.exit(0);
        }
    }
}