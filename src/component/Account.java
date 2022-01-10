package component;

//1.2.1 Creation of the account class
public abstract class Account {
	
	protected String label;
	protected int balance;
	protected int accountId;
	protected static int accountNumber;
	protected Client client;
	
	public Account(String label, Client client) {
		accountNumber++;
		this.label = label;
		this.client = client;
		this.accountId = accountNumber;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int amount) {
		this.balance = amount;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String toString() {
		return ("Account n°" + String.valueOf(this.accountId)
				+ ", "+ this.label
				+ ", balance : " + String.valueOf(this.balance)
				+ ", client : " + this.client);
	}
}
