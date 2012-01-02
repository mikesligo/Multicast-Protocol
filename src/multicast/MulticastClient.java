package multicast;

/**
 * Michael Gallagher
 * 1025071
 * gallam10@tcd.ie
 */

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import tcdIO.*;

/**
 * Client 
 * Skeleton code for Multicast client
 */
public class MulticastClient {
	
	public static final String MCAST_ADDR = "230.0.0.1"; // hardcoded address for the multicast group
	public static final int MCAST_PORT = 9013; // hardcoded port number for the multicast group
	
	public static final int MAX_BUFFER = 1024; // maximum size for data in a packet   
	
	static Terminal term;
	
	MulticastSocket socket;
	InetAddress address;
	int port;
	
	/**
	 * Default Constructor
	 * 
	 * Fills an instance with the hardcoded values
	 */
	public MulticastClient() {
		this(MCAST_ADDR, MCAST_PORT);
	}
	
	/**
	 * Constructor
	 * 
	 * Creates an instance with specific values for the 
	 * address and port of the multicast group 
	 */
	public MulticastClient(String addr, int port) {
		try {
			this.port= port;
			address = InetAddress.getByName(addr);
			socket = new MulticastSocket(port);
			socket.joinGroup(address);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	/**
	 * Run method
	 *
	 * This method sends a datagram with the string "Data?" to a server and
	 * then enters an endless loop in which it attempts to receive datagrams
	 * and prints the content of received datagrams.
	 */
	public void run(){
		String msg = "Date?";
		byte[] buffer;
		DatagramPacket packet = null;
		
		try {
			
			// send datagram to server - asking for date
			packet = new DatagramPacket(msg.getBytes(),	msg.length(), address, port);
			socket.send(packet);
			//term.println("Send Msg");
			
			// wait for incoming datagrams and print their content
			while (true) {
				//term.println("Waiting");
				buffer = new byte[MAX_BUFFER];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				buffer= packet.getData();
				term.println(packet.getAddress()+":"+packet.getPort()+ " | " + new String(buffer, 0, packet.getLength()));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	/**
	 * Main method
	 * Start a client by creating an instance of the class MulticastClient.
	 * 
	 * @param args 	[0] IP address the client should send to 
	 * 				[1] Port number the client should send to
	 */
	public static void main(String[] args) {
		term = new Terminal ("Client");
		int port= 0;
		String address=null;
		MulticastClient client=null;
		
		term.println("Program start");
		try {
			if (args.length==2) {
				address= MCAST_ADDR;
				port= 9013;
				
				client= new MulticastClient(address, port);
			}
		else
			client= new MulticastClient();
		
		client.run();
		}	
		catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		term.println("Program end");
	}
}