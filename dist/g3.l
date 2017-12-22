// Exemplo de Programa na linguagem L

integer n;
byte n2;
integer n3;
string nome1;
string nome2;
string nome3;
boolean b1;
boolean b2;
boolean b3;

// Bloco Principal

{
  nome1 = "disciplina de ";
  nome2 = "compiladores";
  nome3 = nome1 + nome;
  writeln,"Fim da ",nome3;
  
  write,"Mais alguns testes ";
  writeln,"...";
  
  n=-10;
  n2=10;
  
  writeln,"valor de n = ", n;
  writeln,"valor de n2 = ", n2;
  
  if n < n2 then
    writeln,"n < n2";
	
  if n <= n2 then
    writeln,"n <= n2";
  
  if n > n2 then
    writeln,"n > n2";
	
  if n >= n2 then
    writeln,"n >= n2";
	
  if n <> n2 then
    writeln,"n <> n2";
	
  if n == n2 then
    writeln,"n == n2";

  n3 = n + n2;
  writeln,"n + n2", n3;
  
  n3 = n - n2;
  writeln,"n - n2", n3;
  
  n3 = n * n2;
  writeln,"n * n2", n3;
  
  n3 = n / n2;
  writeln,"n / n2", n3;
  
  n3 = (((n + n) - n2) / (n * n2));
  writeln,"n / n2", n3;
  
  b1 = n < n2;
  b2 = n > n2;
  
  b3 = b1 or b2;
  writeln,"b1 or b2", b3;
  
  b3 = b1 and b2;
  writeln,"b1 and b2", b3;
  
  b3 = not b1;
  writeln,"not b1", b3;
 
  write,"Fim da disciplina de compiladores... Boas ferias";
}