// Exemplo de Programa na linguagem L

integer n;
string nome;
string sobrenome;
string nomecompleto;
boolean naoTerminou;
const MAXITER=10;

// Bloco Principal

{
  nome = "disciplina";
  sobrenome = " compiladores";
  nomecompleto = nome * sobrenome;
  writeln,"Nome completo ",nomecompleto;
  
  write,"Digite seu nome: ";
  readln,nome;
  naoTerminou=TRUE;
  n=0;
  while naoTerminou {
    writeln,"Ola' ",nome;
    n=n+1;
    naoTerminou=n<MAXITER;
  }
}