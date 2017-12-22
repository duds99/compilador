// Exemplo de Programa na linguagem L

integer n = 55;
string nome;
boolean naoTerminou;
const MAXITER=50;

// Bloco Principal

{
  write,"Digite seu nome: ";
  write,nome;
  readln,nome;
  naoTerminou=TRUE;
  n=0;
  while MAXITER {
    writeln,"Ola' ",nome;
    n=n+1;
    n=n<MAXITER;
  }
}