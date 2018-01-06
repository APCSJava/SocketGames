# SocketGames
Sometimes one simply wants to play text games across a TCP socket.  For those times, this project. (requires Java8 or higher) 

Game classes written for this project should:
* provide a default constructor,  
* extend AbstractGame, to facilitate setting/retrieving best score data,
* implement the Servable interface, and  
* carry the @GameInfo class type annotation, to aid in menu display  

Compiled class files meeting these criteria will be detected automatically and made available for play. 

Access a running server from a remote shell or terminal by, for example: 
* <b>telnet <i>server_ip_address tcp_port</i></b>, or  
* <b>nc <i>server_ip_address tcp_port</i></b> // macOS 10.13 removed the 'telnet' command, so use 'netcat' or 'nc', instead

# Notes for compiling and running inside an IDE (Eclipse, say)
The main application class is GameServer.  It requires two command line arguments -- the port number and the maximum number of connections to accept.  In Eclipse, go to "Run Configurations ..." and under the Arguments tab, enter "9090 5", for example, to run the server on port 9090 and accept a maximum of 5 simultaneous connections.

# Notes for compiling and running outside of an IDE
The server must be compiled before it can be run.  If you are compiling from the root folder of the distribution, this will take a form such as: <b> javac org/asl/socketserver/GameServer.java</b>.  This will compile not only the server but all related and dependent classes in the org.asl.socketserver package. 

To compile all classes in the games folder, <b> javac org/asl/socketserver/games/*.java</b>

The server may then be launched as follows:
* <b>java org/asl/socketserver/GameServer 9090 5</b> // launches the server on port 9090 and accepts up to 5 simultaneous connections
