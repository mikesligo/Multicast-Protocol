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
			address = InetAddress.getByName(addr);
			socket = new MulticastSocket(port);
			socket.joinGroup(address);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void init(){
		DatagramPacket packet= null;
		byte[] buffer = null;
		String msg = null;

		try {
			while (true) {
				// receive message from client
				buffer = new byte[MAX_BUFFER];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				msg= new String(buffer, 0, packet.getLength());

				// print this in the server log for debugging
				term.println("Received: " + msg + "from: "+packet.getAddress()+":"+packet.getPort());
				term.println("Sending: " + new String(buffer));
				socket.send(packet);
			}				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		term = new Terminal("Server");

		MulticastServer server = null;
		int port;
		String address;

		term.println("Server start");
		try {
			address= MCAST_ADDR;
			port= MCAST_PORT;
			server= new MulticastServer(address, port);

			server.init();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		term.println("Server close");
	}

}
