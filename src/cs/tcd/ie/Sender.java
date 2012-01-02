/**
 * 
 */
package cs.tcd.ie;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

/**
 * @author sweber
 *
 * Sending side of a communication link
 *
 * The class provides the basic functionality to transmit a datagram to a receiver.
 * The main method acts as test for the class by filling the destination host and port number and the source port number.
 *
 */
/**
 * @author sweber
 *
 */
public class Sender {
	static final int DEFAULT_PORT = 50000;
	static final int DEFAULT_DST_PORT = 50001;
	static final String DEFAULT_HOST = "localhost";
	
	static Terminal terminal;
	
	DatagramSocket socket;
	InetSocketAddress dstAddress;
	
	/**
	 * Constructor
	 * 
	 */
	Sender() {
		this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DST_PORT);
	}
	

	/**
	 * Constructor
	 * 	 
	 * Attempts to create socket at given port and create an InetSocketAddress for the destinations
	 */
	Sender(String dstHost, int dstPort, int srcPort) {
		try {
			dstAddress= new InetSocketAddress(dstHost, dstPort);
			socket= new DatagramSocket(srcPort);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Accepts input from the user in the form of individual elements of a array of bytes
	 * 
	 * @return byte array with input from the user
	 */
	byte[] getData() {
		byte length;
		byte[] data;

		length= terminal.readByte("No. of data items: ");
		data= new byte[length+1];
		data[0]= length;
		for(int i= 1; i<=data.length; i++) {
			terminal.println("Item "+i+" : ");
			data[i] = terminal.readByte();
		}
		
		return data;
	}

	
	/**
	 * Sender Method
	 * 
	 * Transmits single datagram packet to a receiver.
	 */
	void start() {
		byte[] data= null; // data[0] is the length, data[1..n] are data items
		DatagramPacket packet= null;
		
		try {
			data= getData(); // fill the data field with something to send
			
			terminal.println("Sending packet...");
			packet= new DatagramPacket(data, data.length, dstAddress);
			socket.send(packet);
			terminal.println("Packet sent");
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}		
	}


	/**
	 * Test method
	 * 
	 * Sends a packet to a given address
	 * 
	 * @param args 0, hostname to send to; 1, port number to send to; 2, port number to bind to
	 *             
	 */
	public static void main(String[] args) {
		Sender s;
		try {			
			String dstHost= args[0];
			int dstPort= Integer.parseInt(args[1]);
			int srcPort= Integer.parseInt(args[2]);
		
			terminal= new Terminal("Sender");
		
			s= new Sender(dstHost, dstPort, srcPort);
			s.start();

			terminal.println("Program completed");
		} catch(java.lang.Exception e) {
			e.printStackTrace();
		}
	}
}