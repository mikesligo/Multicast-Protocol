package team.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import tcdIO.*;



public class MulticastClient {

	public static final String MCAST_ADDR = "230.0.0.1";	// Hardcoded address for the multicast group
	public static final int MCAST_PORT = 9013; 				// Hardcoded port number for the multicast group

	public static final int MAX_BUFFER = 1024; 				// Maximum size for data in a packet

	static Terminal term;

	MulticastSocket socket;
	InetAddress address;
	int port;

	/**
	 * Default Constructor
	 */
	public MulticastClient() {
		this(MCAST_ADDR, MCAST_PORT);
	}

	/**
	 * Constructor
	 * Creates an instance with specific values for the 
	 * address and port of the multicast group 
	 */
	public MulticastClient(String addr, int port) {
		try {
			this.port= port;
			this.address = InetAddress.getByName(addr);

			// creates the socket
			this.socket = new MulticastSocket(port);

			// joins the multicast socket (or something)
			this.socket.joinGroup(address);
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	// Method called my main
	public void run(){
		String msg = "Date?"; // temporary, will have a do-while loop to take input msgs, and that's basically part 1
		byte[] buffer;
		DatagramPacket packet = null;

		try {
			// send datagram to server
			packet = new DatagramPacket(msg.getBytes(),	msg.length(), address, port);
			socket.send(packet);

			// wait for incoming datagrams and print their content
			while (true) {
				buffer = new byte[MAX_BUFFER];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				buffer= packet.getData();
				term.println(" | " + new String(buffer, 0, packet.getLength()));
			}

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		term = new Terminal ("Client");
		MulticastClient client=null;

		try{
			term.println("Program start");
			// define new multicast client object
			client= new MulticastClient(MCAST_ADDR, MCAST_PORT);
			client.run();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		term.println("Program end");
	}

}
