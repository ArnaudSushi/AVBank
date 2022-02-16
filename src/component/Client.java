package component;
// 1.1.1 Creation of the Client class
public class Client {
	
	private int clientId;
	private String name;
	private String firstName;
	private static int clientNumber = 0;
	
	public Client(String name, String firstName) {
		upClientNumber();
		this.clientId = Client.clientNumber;
		this.name = name;
		this.firstName = firstName;
	}
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	private static void upClientNumber() {
		clientNumber++;
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
	
	public String toString() {
		return ("Client N°" + this.clientId + " : " + this.name + " " + this.firstName + ".");
	}
}