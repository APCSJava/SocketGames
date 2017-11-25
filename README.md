# SocketGames
Sometimes one simply wants to play a text game across a TCP socket.  For those times, this repository.  

Game classes written for this project should:
* provide a default constructor,  
* implement the Servable interface, and  
* carry the @GameInfo type annotation.  

Game class files so outfitted should be placed into the server working directory where they will be detected automatically and made available for play.  

The server may be launched as follows: <b> java SocketServer <i>tcp_port</i> </b>  If no port number is provided when launching the server, a default value of 9090 is used.  

Access a running server from a remote shell or terminal by, for example, <b>telnet <i>server_ip_address tcp_port</i></b>. 

Two "games" are available as examples -- Echo and HiLo.
