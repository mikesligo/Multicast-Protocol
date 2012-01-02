package stream.sockets;

import java.net.InetAddress;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Client {

	public Client() {
	}
	
	public void run() {
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		String message = null;

		try {
			socket= new Socket (InetAddress.getLocalHost(), 50000);
			out= new ObjectOutputStream (socket.getOutputStream());
			in= new ObjectInputStream (socket.getInputStream());
			System.out.println("Client: Connection established - sending message");
			
			out.writeUTF ("Hello Server");
			out.flush();
			message= in.readUTF();
			System.out.println("Client - Message from Server: " + message);

			in.close(); 
			out.close(); 
			socket.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Client client;
		
		System.out.println("Client: Program start");
		client= new Client();
		client.run();
		System.out.println("Client: Program end");
	}
}