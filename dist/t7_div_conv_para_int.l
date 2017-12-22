// Exemplo de Programa na linguagem L

integer n;
byte n2;
integer n3;
string nome;
boolean naoTerminou;
const MAXITER=10;

// Bloco Principal

{
  n = 10;
  n2 = 25;
  n3 = n2 / n;
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