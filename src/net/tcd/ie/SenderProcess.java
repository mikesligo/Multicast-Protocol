package net.tcd.ie;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SenderProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatagramPacket packet;
		DatagramSocket socket;
		InetAddress address;
		int port;
		
		ObjectOutputStream ostream;
		ByteArrayOutputStream bstream;
		byte[] buffer;

		try {
			System.out.println("Program start");
	
			// extract destination from arguments
			address= InetAddress.getByName(args[0]);
			port= Integer.parseInt(args[1]);
			
			// convert string "Hello World" to byte array
			bstream= new ByteArrayOutputStream();
			ostream= new ObjectOutputStream(bstream);
			ostream.writeUTF("Hello World");
			ostream.flush();
			buffer= bstream.toByteArray();
			
			// create packet addressed to destination
			packet= new DatagramPacket(buffer, buffer.length, 
					address, port);
		
			// create socket and send packet
			socket= new DatagramSocket();
			socket.send(packet);

			System.out.println("Program end");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}