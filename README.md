# Trabalho-CC-23-24

Tarefas Feitas:<br>
✔️ Mensagem de Disconnect <br>
✔️ Servidor ler a lista a partir do socket e guardar essa informação associada ao cliente respetivo <br>
✔️ Identificar cada cliente através do seu IP (de forma a aparecer cliente1, cliente2, ...)
✔️ Cliente ler ficheiros e enviar a lista de ficheiros que possui (ao enviar a mensagem, o primeiro caracter tem de ser !, para identificar que é o inicio de uma mensagem)
✔️Server identificar blocos em vez de ficheiros ao receber <br>
✔️Cliente receber ficheiro e ler em bytes <br>
✔️Dividir ficheiro em blocos de 1007 bytes (bloco é identificado por »« i.e. : file1t.txt»«1 para o bloco 1) e guardar na pasta <br>
✔️Cliente ao dar parse dividir o que é blocos e o que é ficheiros <br>

<br>
To-Do: <br>

-Caso de disconnect de cliente no server <br>
-Verificar tamanho do header para mudar <br>
-Implementar mediador e worker <br>
-Implementar receber ficheiro no cliente <br>
-Definir protocolo UDP <br>
-Envio por UDP entre nodos <br>


## Request Type 1
<br>
<br>
RT ; IP ; Payload ?
<br>
RT -> Request Type = 1 byte
<br>
IP = 15 bytes
<br>
Payload -> File_name ! nº_blocks : File_name ! nº_blocks 
<br>
? -> Delimitador Final
<br>
<br>
Tamanhos (com delimitadores) : 1 + 1 + 15 + 1 + 1007 + 1 = 1024 bytes
