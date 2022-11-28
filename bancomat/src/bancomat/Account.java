package bancomat;

public class Account {
	private String card;
	private String pin;
	private int balance;
	private AccountStatus status;
	
	public Account() {};
	public Account(String card, String pin) {
		this.card = card;
		this.pin = pin;
	};
	
	public String getCard() {
		return card;
	}
	
	public void setCard(String card) {
		this.card = card;
	}
	
	public String getPin() {
		return pin;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public enum AccountStatus {
	    ACTIVE,
	    NEW,
	    FROZEN
	  }
}
