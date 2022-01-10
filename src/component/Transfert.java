package component;

import java.time.LocalDate;

//1.3.3 Creation of the Transfert, Credit, Debit classes
public class Transfert extends Flow {
	
	private int issuingAccountNumber;

	public Transfert(String comment, int id, double amount, int targetAccountNumber, boolean effect, LocalDate date,
			int issuingAccountNumber) {
		super(comment, id, amount, targetAccountNumber, effect, date);
		this.issuingAccountNumber = issuingAccountNumber;
	}

	public int getIssuingAccountNumber() {
		
		return issuingAccountNumber;
	}

	public void setIssuingAccountNumber(int issuingAccountNumber) {
		this.issuingAccountNumber = issuingAccountNumber;
	}
}
