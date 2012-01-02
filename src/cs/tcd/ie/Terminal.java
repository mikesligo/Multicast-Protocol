package cs.tcd.ie;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Terminal {
	private BufferedReader reader;
	private InputStreamReader streamReader;
	
	public Terminal() {
		this.streamReader = new InputStreamReader(System.in);
		this.reader = new BufferedReader(streamReader);
	}
	
	public Terminal(String title) {
		this();
	}
	
	public Terminal(String title, int width, int height) {
		this();
	}
	
	/**
	 * Method to read an integer value from the keyboard. If the user enters
	 * a non-integer value they will be asked to re-enter an integer value.
	 *
	 * @return the integer value read from the keyboard.
	 */
	public int readInt(){
		String line = null;
		int val = 0;
		while(true){
			try {
				line = reader.readLine(); // can throw an IOException.
				val = Integer.parseInt(line); // can throw a NumberFormatException.
				break; // break out of enclosing while 
			}
			catch (IOException error) {
				System.err.println("Unexpected IO ERROR: " + error);
				System.exit(1); // exit program as an uxepected error occurred
			}
			catch (NumberFormatException e) {
				System.err.println("Not a valid integer: " + line);
				System.err.println("Enter an integer: ");
			}
		} // end of while
		return val;
	}
	
	/**
	 * Method to read a positive integer value from the keyboard. If the user 
	 * enters a non-positive integer value they will be asked to re-enter a
	 * positive integer value.
	 *
	 * @return the positive integer value read from the keyboard.
	 */
	public int readPositiveInt(){
		int val = readInt();
		while (val < 1) {
			System.out.println("Not a positive integer: " + val);
			System.out.println("Enter a positive integer:");
			val = readInt();
		} // end of while
		return val;
	}
	
	/**
	 * Method to read a line of text from the keyboard as a String object. 
	 *
	 * @return a reference to a String object read from the keyboard.
	 */
	public String readString() {
		String line = "";
		try {
			line = reader.readLine();
		}
		catch (IOException error) {
			System.err.println("Unexpected IO ERROR: " + error);
			System.exit(1); // exit program as an uxepected error occurred
		}
		return line;
	}
	
	public char readChar() {
		char result = '\n';
		try {
			result = (char)streamReader.read();
			writeChar('\010'); // '\010' == '\n'
		}
		catch(IOException e) {}
		return result;
	}
	
	public String readString(String prompt) {
		print(prompt);
		return readString();
	}
	
	public int readInt(String prompt) {
		print(prompt);
		return readInt();
	}
	
	
	
	public short readShort(String prompt) {
		print(prompt);
		return readShort();
	}
	
	public short readShort() {
		short s = 0;
		while (true) {
			try {
				s = Short.parseShort(readString());	
				break;
			}
			catch (NumberFormatException error) {
				print("Invalid short, retry: ");
			}
		}
		return s;
	}
	
	public byte readByte(String prompt) {
		print(prompt);
		return readByte();
	}
	
	public byte readByte() {
		return (byte)readChar();
	}
	
	public long readLong(String prompt) {
		print(prompt);
		return readLong();
	}
	
	public long readLong() {
		long l = 0;
		while (true) {
			try {
				l = Long.parseLong(readString());
				break;
			}
			catch (NumberFormatException error) {
				print("Invalid long, retry: ");
			}
		}
		return l;
	}
	
	public double readDouble(String prompt) {
		print(prompt);
		return readDouble();
	}
	
	public double readDouble() {
		double d = 0.0d;
		while (true) {
			try {
				d = Double.parseDouble(readString());
				break;
			}
			catch (NumberFormatException error)	{
				print("Invalid double, retry: ");
			}
		}
		return d;
	}
	
	public float readFloat(String prompt) {
		print(prompt);
		return readFloat();
	}
	
	public float readFloat() {
		float f = 0.0f;
		while (true) {
			try {
				f = Float.parseFloat(readString());
				break;
			}
			catch (NumberFormatException error)	{
				print("Invalid float, retry: ");
			}
		}
		return f;
	}
	
	public boolean readBoolean(String prompt) {
		print(prompt);
		return readBoolean();
	}
	
	public boolean readBoolean() {
		return new Boolean(readString()).booleanValue();
	}
	
	public void println(String message) {
		System.out.println(message);
	}
	
	public void println() {
		System.out.print("\n");
	}
	
	public void print(String message) {
		System.out.print(message);
	}
	
	public void writeInt(int i) {
		print("" + i);
	}
	
	public void writeShort(short s) {
		print("" + s);
	}
	
	public void writeByte(byte b) {
		print("" + b);
	}
	
	public void writeLong(long l) {
		print("" + l);
	}
	
	public void writeDouble(double d) {
		print("" + d);
	}
	
	public void	writeBoolean(boolean b){
		print("" + b);
	}
	
	public void writeFloat(float f) {
		print("" + f);
	}
	
	public void writeChar(char c) {
		print("" + c);
	}
}