package multicast;

/**
 * Michael Gallagher
 * 1025071
 * gallam10@tcd.ie
 */

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import tcdIO.*;

/**
 * Server 
 * Skeleton code for Multicast server
 */
public class MulticastServer {

	public static final String MCAST_ADDR = "230.0.0.1";	// Hardcoded address for the multicast group
	public static final int MCAST_PORT = 9013; 				// Hardcoded port number for the multicast group

	public static final int MAX_BUFFER = 1024; 				// Maximum size for data in a packet
	
	static Terminal term;

	MulticastSocket socket;
	InetAddress address;
	int port;

	/**
	 * Default Constructor
	 * 
	 * Fills an instance with the hardcoded values
	 */
	public MulticastServer() {
		this(MCAST_ADDR, MCAST_PORT);
	}

	/**
	 * Constructor
	 * 
	 * Creates an instance with specific values for the 
	 * address and port of the multicast group 
	 * 
	 * @param addr Address of the multicast group as string
	 * @param port Port number of the server 
	 */
	public MulticastServer(String addr, int port) {
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
	 * This method is continuously looking to receive messages from clients.
	 * The method will reply with a message containing the current date information
	 * if a client sends a message that contains the string "Date?". 
	 */
	public void run() {
		DatagramPacket packet= null;
		byte[] buffer= null;
		String msg= null;

		try {
			while (true) {
				// receive message from client
				buffer = new byte[MAX_BUFFER];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				msg= new String(buffer, 0, packet.getLength());
				term.println("Received: " + msg);
				term.println("From: "+packet.getAddress()+":"+packet.getPort());

				if (msg.equalsIgnoreCase("Date?")) {
					// send reply to everyone
					msg = new Date().toString();
					buffer = msg.getBytes();
					packet = new DatagramPacket(buffer, buffer.length, address, port);
					term.println("Sending: " + new String(buffer));
					socket.send(packet);
				}
				else {
					msg = new Date().toString();
					buffer = msg.getBytes();
					packet = new DatagramPacket(buffer, buffer.length, address, port);
					term.println("Sending: " + new String(buffer));
					socket.send(packet);
				}
			}				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method
	 * Starts a server application by creating an instance of 
	 * the class MulticastServer.
	 * 
	 * @param args  [0] IP address the server should bind to 
	 * 				[1] Port number the server should bind to
	 */
	public static void main(String[] args) {
		term = new Terminal ("MulticastSocket");
		int port= 0;
		String address=null;
		MulticastServer server=null;

		term.println("Program start");
		try {
			if (args.length==2) {
				address= MCAST_ADDR;
				port= MCAST_PORT;

				server= new MulticastServer(address, port);
			}
			else
				server= new MulticastServer();

			server.run();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		term.println("Server close");
	}

}