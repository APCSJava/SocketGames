# SocketGames
Sometimes one simply wants to play text games across a TCP socket.  For those times, this project (requires Java8 or higher). 

The project consists of two packages:
org.asl.socketserver which contains the server and necessary supporting classes
org.asl.socketserver.games which contains game source code or class files

Game classes written for this project should abide by the following four constraints:
* provide a default constructor (to permit no-arg instantiation inside a thread)
* implement the Servable interface (thread life is bounded by Servable.serve())
* extend AbstractGame (to enable setting/retrieving best score data)
* annotate the class with a @GameInfo annotation (provides content for menus)  

Compiled class files meeting these criteria and stored in the org/asl/socketserver/games folder will be identified automatically by the server and made available for play. 

A running server may be accessed from a remote shell or terminal by: 
* <b>netcat <i>server_ip_address tcp_port</i></b>, or  
* <b>nc <i>server_ip_address tcp_port</i></b> 
* // other tools, such as 'telnet' may be used, if available on the client platform

# Notes for compiling and running inside an IDE (Eclipse, say)
The main application class is GameServer.  It requires two command line arguments -- the port number and the maximum number of connections to accept.  In Eclipse, go to "Run Configurations ..." and under the Arguments tab, enter "9090 5", for example, to run the server on port 9090 and accept a maximum of 5 simultaneous connections.

# Notes for compiling and running outside of an IDE (command line)
The server must be compiled before it can be run.  If you are compiling from the root folder of the distribution, this will take a form such as: 
* <b> javac org/asl/socketserver/GameServer.java</b>.  
This will compile not only the server but all related and dependent classes in the org.asl.socketserver package. 

To compile all classes in the games folder:
* <b> javac org/asl/socketserver/games/*.java</b>

Launch the compiled server as follows:
* <b>java org/asl/socketserver/GameServer 9090 5</b> 
// launches the server on port 9090 and accepts up to 5 simultaneous connections
