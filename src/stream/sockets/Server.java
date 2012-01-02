package stream.sockets;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Server {

	static final int serverPort=50000;
	
	public Server() {	
	}
	
	public void run() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverPort));

			while (true) { 
				System.out.println("Server: Expecting a connection");
				socket = serverSocket.accept();
				handleConnection(socket);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handleConnection(Socket socket) {
		String message = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		try {
			out= new ObjectOutputStream (socket.getOutputStream());
			in= new ObjectInputStream (socket.getInputStream());

			message= in.readUTF();
			System.out.println("Server - Message received:" + message);
			out.writeUTF("Hello World");
			out.flush();

			in.close(); 
			out.close(); 
			socket.close();
			System.out.println("Server: Sent reply - closing connection");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = null;
		
		System.out.println("Server: Program start");
		server= new Server();
		server.run();
		System.out.println("Server: Program end");
	}
}