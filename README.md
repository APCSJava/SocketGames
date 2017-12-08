# SocketGames
Sometimes one simply wants to play text games across a TCP socket.  For those times, this project.  

Game classes written for this project should:
* provide a default constructor,  
* implement the Servable interface, and  
* carry the @GameInfo type annotation.  

Game class files so outfitted should be placed into the server working directory where they will be detected automatically and made available for play.  

The server must be compiled before it can be run, as -- for example -- <b> javac GameServer.java </b>.

The server may be launched as follows: <b> java GameServer <i>tcp_port</i> </b>  If no port number is provided when launching the server, a default value of 9090 is used.  

Access a running server from a remote shell or terminal by, for example: 
* <b>telnet <i>server_ip_address tcp_port</i></b>, or  
* <b>nc <i>server_ip_address tcp_port</i></b> // macOS 10.13 removed telnet

Two "games" are available as examples -- SecretWord and HighLow.  These games are provided as source code and must be compiled locally in order to be discovered by the server.
* <b> javac SecretWord.java </b>
* <b> javac HighLow.java </b>
