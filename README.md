# Trabalho-CC-23-24

Tarefas Feitas:<br>
✔️ Mensagem de Disconnect <br>
✔️ Servidor ler a lista a partir do socket e guardar essa informação associada ao cliente respetivo <br>
✔️ Identificar cada cliente através do seu IP (de forma a aparecer cliente1, cliente2, ...)<br>
✔️ Cliente ler ficheiros e enviar a lista de ficheiros que possui (ao enviar a mensagem, o primeiro caracter tem de ser !, para identificar que é o inicio de uma mensagem)<br>
✔️Server identificar blocos em vez de ficheiros ao receber <br>
✔️Cliente receber ficheiro e ler em bytes <br>
✔️Dividir ficheiro em blocos de 1007 bytes (bloco é identificado por »« i.e. : file1t.txt»«1 para o bloco 1) e guardar na pasta <br>
✔️Cliente ao dar parse dividir o que é blocos e o que é ficheiros <br>
✔️Verificar tamanho do header para mudar <br>
✔️Implementar mediador e worker <br>
✔️Implementar receber ficheiro no cliente <br>
✔️Definir protocolo UDP <br>
✔️Envio por UDP entre nodos <br>

<br>
To-Do: <br>

-Caso de disconnect de cliente e do server <br>
-Mudar para quando o hash não bater certo voltar a enviar o ficheiro <br>
-Criar e implementar o algoritmo de escolha de blocos <br>
-Em caso de disconnect do cliente limpar ficheiros do server para n tentar aceder depois na procura de bloco <br>
-No final tirar prints de debugs <br>

____________________________________________________________________________________
## TCP
<br>
<br>

### Request Type 1 (Server)
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

### Request Type 2 (Server)
<br>

### Request Type 3 (Server)
<br>

### Request Type 4 (Server)
<br>

### Request Type 1 (Cliente)
<br>

### Request Type 2 (Cliente)
<br>

____________________________________________________________________________________
## UDP 
<br>
<br>

### Request Type 1 (Receive File) 

<br>
RT IP FileName Hash Payload <br> 
1 + 15 + 30 + 16 + 962 <br>

### Request Type 2 (Send File)
<br>
RT IP FileName <br>
1 + 15 + 30
