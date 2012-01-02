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
 */
public class Receiver {

	static final int DEFAULT_PORT = 50001;
	static Terminal terminal;
	
	DatagramSocket socket;
	InetSocketAddress dstAddress;
	
	/**
	 * Constructor
	 * 
	 */
	Receiver() {
		this(DEFAULT_PORT);
	}
	

	/**
	 * Constructor
	 * 	 
	 * Attempts to create socket at given port
	 */
	Receiver(int port) {
		try {
			socket= new DatagramSocket(port);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Attempts to print the given array of bytes, expecting the number of 
	 * data items in the first byte.
	 * 
	 * @param data byte array to print
	 */
	void printContent(byte[] data) {
		terminal.println("Packet length:" + data.length);
		terminal.println("No. of data items: "+ data[0]);
		terminal.println("Data items: ");
		for(int i= 1; i<=data[0]; i++) {
			terminal.print(""+data[i]+" ");
		}
		terminal.println();
	}
	
	
	/**
	 * Receiver Method
	 * 
	 * Attempts to receive a single datagram.
	 */
	void start() {
		byte[] data;
		DatagramPacket packet;
		
		try {
			data= new byte[1024];
			packet= new DatagramPacket(data, data.length);
			terminal.println("Waiting for packet...");
			socket.receive(packet);			
			terminal.println("Received packet");			
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}		
	}


	/**
	 * Test method
	 * 
	 * Creates a socket and attempts to receive a packet at a given port number
	 * 
	 * @param args arg[0] port number to receive information on
	 */
	public static void main(String[] args) {
		Receiver r;
		
		try {
			terminal= new Terminal("Receiver");
			int port= Integer.parseInt(args[0]);
			r= new Receiver(port);	
			r.start();

			terminal.println("Program completed");
		} catch(java.lang.Exception e) {
			e.printStackTrace();
		}
	}

}