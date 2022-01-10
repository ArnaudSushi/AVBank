package component;

import java.time.LocalDate;

//1.3.3 Creation of the Transfert, Credit, Debit classes
public class Debit extends Flow {

	public Debit(String comment, int id, double amount, int targetAccountNumber, boolean effect, LocalDate date) {
		super(comment, id, amount, targetAccountNumber, effect, date);
	}

}
