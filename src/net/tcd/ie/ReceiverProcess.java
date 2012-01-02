package net.tcd.ie;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceiverProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatagramPacket packet;
		DatagramSocket socket;
		InetAddress address;
		int port;
		
		ObjectInputStream ostream;
		ByteArrayInputStream bstream;
		byte[] buffer;

		try {
			System.out.println("Program start");

			// extract destination from arguments
			address= InetAddress.getByName(args[0]);
			port= Integer.parseInt(args[1]);
			
			// create buffer for data, packet and socket
			buffer= new byte[1500];
			packet= new DatagramPacket(buffer, buffer.length);
			socket= new DatagramSocket(port, address);
	
			// attempt to receive packet
			System.out.println("Trying to receive");
			socket.receive(packet);

			// extract data from packet
			buffer= packet.getData();
			bstream= new ByteArrayInputStream(buffer);
			ostream= new ObjectInputStream(bstream);
			
			// print data and end of program
			System.out.println("Data: " + ostream.readUTF());
			System.out.println("Program end");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}