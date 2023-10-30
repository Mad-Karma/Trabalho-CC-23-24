# Trabalho-CC-23-24

Tarefas:<br>
✔️ Mensagem de Disconnect <br>
✔️ Servidor ler a lista a partir do socket e guardar essa informação associada ao cliente respetivo <br>
- Identificar cada cliente através do seu IP (de forma a aparecer cliente1, cliente2, ...)
- Cliente ler ficheiros e enviar a lista de ficheiros que possui (ao enviar a mensagem, o primeiro caracter tem de ser !, para identificar que é o inicio de uma mensagem)

<br>
# Request Type 1

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
Tamanhos (com delimitadores) : 1 + 1 + 15 + 1 + 1007 + 1
