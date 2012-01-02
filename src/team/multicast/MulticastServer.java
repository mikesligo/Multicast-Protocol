package team.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Date;

import tcdIO.*;

public class MulticastServer {

	public static final String MCAST_ADDR = "230.0.0.1";	// Hardcoded address for the multicast group
	public static final int MCAST_PORT = 9013; 				// Hardcoded port number for the multicast group

	public static final int MAX_BUFFER = 1024; 				// Maximum size for data in a packet

	static Terminal term;

	MulticastSocket socket;
	InetAddress address;
	int port;

	/**
	 * Default Constructor - Fills an instance with the hardcoded values
	 */
	public MulticastServer() {
		this(MCAST_ADDR, MCAST_PORT);
	}

	/**
	 * Constructor
	 * Creates an instance with specific values for the 
	 * address and port of the multicast group 
	 */
	public MulticastServer(String addr, int port) {
		try {
			this.port= port;
			this.address = InetAddress.getByName(addr);
			
			// creates the socket
			this.socket = new MulticastSocket(port);
			
			// joins the multicast socket (or something)
			this.socket.joinGroup(address);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	// Method called by Main
	public void init(){
		// The sample code initialised them as null
		DatagramPacket packet= null;
		byte[] buffer = null;
		String msg = null;
		String prevmsg = null;

		try {
			while (true) {
				// receive message from client
				buffer = new byte[MAX_BUFFER];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				msg= new String(buffer, 0, packet.getLength());
				prevmsg = msg; // explained in a bit

				// print this in the server log for debugging
				term.println("Received: " + msg + "from: "+packet.getAddress()+":"+packet.getPort());
				
				// This 'if' statement is here, because without it the server goes in a perpetual loop of sending and receiving the same message
				// I'm sure there's a fix to this, but I haven't been able to find it..
				if (msg != prevmsg){
					term.println("Sending: " + new String(buffer));
					socket.send(packet);
				}
			}				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		term = new Terminal("Server");
		
		// Initialise it's own class
		MulticastServer server;

		term.println("Server start");
		try {
			// MCAST_ADDR and MCAST_PORT are hardcoded as static final variables
			server= new MulticastServer(MCAST_ADDR, MCAST_PORT);
			server.init();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		term.println("Server close");
	}

}
