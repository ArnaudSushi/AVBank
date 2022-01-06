package component;
// 1.1.1 Creation of the Client class
public class Client {
	private String name;
	private String firstName;
	private static int clientNumber = 0;
	
	Client(String name, String firstName) {
		this.name = name;
		this.firstName = firstName;
		this.clientNumber++;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public static int getClientNumber() {
		return clientNumber;
	}
}