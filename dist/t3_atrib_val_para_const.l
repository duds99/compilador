// Exemplo de Programa na linguagem L

integer n;
string nome;
boolean naoTerminou;
const MAXITER=10;

// Bloco Principal

{
  MAXITER=20;
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