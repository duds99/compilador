// Exemplo de Programa na linguagem L

integer n;
string nome;
string nome1;
string nome2;
boolean naoTerminou;
const MAXITER=10;

// Bloco Principal

{
  nome1 = "compiladores";
  nome2 = "compiladores";
  if nome1 == nome2 then
    writeln,"nome1 e nome2 sao iguais";
  else
    writeln,"nome1 e nome2 sao diferentes";
  
  write,"Digite seu nome: ";
  readln,nome;
  naoTerminou=TRUE;
  n=0;
  while naoTerminou {
    writeln,"Ola' ",nome;
    n=n+1;
    }
}