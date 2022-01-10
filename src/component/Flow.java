package component;

import java.time.LocalDate;

//1.3.2 Creation of the Flow class
public abstract class Flow {
	
	private String comment;
	private int id;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private LocalDate date;
	
	public Flow(String comment, int id, double amount, int targetAccountNumber, boolean effect, LocalDate date) {
		super();
		this.comment = comment;
		this.id = id;
		this.amount = amount;
		this.targetAccountNumber = targetAccountNumber;
		this.effect = effect;
		this.date = date;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getTargetAccountNumber() {
		return targetAccountNumber;
	}
	public void setTargetAccountNumber(int targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}
	public boolean isEffect() {
		return effect;
	}
	public void setEffect(boolean effect) {
		this.effect = effect;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

}
