# SocketGames
Sometimes one simply wants to play text games across a TCP socket.  For those times, this project.  

Game classes written for this project should:
* provide a default constructor,  
* extend AbstractGame, to facilitate setting/retrieving best score data,
* implement the Servable interface, and  
* carry the @GameInfo class type annotation, to aid in menu display  

Compiled class files meeting these criteria will be detected automatically and made available for play. 

Access a running server from a remote shell or terminal by, for example: 
* <b>telnet <i>server_ip_address tcp_port</i></b>, or  
* <b>nc <i>server_ip_address tcp_port</i></b> // macOS 10.13 removed the 'telnet' command, so use 'netcat' or 'nc', instead

# Notes for compiling and running outside of an IDE
The threaded server must be compiled before it can be run, as -- for example -- <b> javac GameServer.java </b>.  However, because the source code exists in a package, the classpath must be specified.  If you are compiling from the root of the distribution, this should amount to the following: <b> javac -cp blah.GameServer.java</b>.  This should compile not only the server but all necessary classes in the org.asl.gameserver package. 

The server may be launched as follows: <b> java GameServer <i>tcp_port</i> <i>num_threaded_connections</i> </b>
* java GameServer 9090 5 // launches the service on port 9090 and accepts up to 5 simultaneous connections

Several games are available as examples.  These games are provided as source code and must be compiled locally in order to be discovered by the server.
