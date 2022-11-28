package bancomat;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bancomat.AtmException.ExceptionStatus;


public class Atm {

	private boolean cardAuthenticated = false;
	private AtmDisplay atmDisplay;
	private Bank bank;
	private int cashLimit = 1000000;
	
	private Account currentAccount;
	
	public static void main(String[] args) {
		Atm atm = new Atm();
		atm.processing();
	}
	
	public Atm()   {
		cardAuthenticated = false; 
		atmDisplay = new AtmDisplay();
		bank = new Bank(); 
    } 
	
	void processing() {
		try {
			bank.readDataFomDb();
			
			while(true){
				atmDisplay.displayMessage( "\nWelcome!" );
				
		        while ( !cardAuthenticated )  {
		        	  authenticateCard(); 
		        } 
		        
		        startSession(); 
		     }
		}  
		catch (final AtmException e) {  
			atmDisplay.displayMessage(e.getMessage());
		}
		catch (final Exception e) {  
			atmDisplay.displayMessage(e.getMessage());
		}
		finally {
			atmDisplay.displayMessage("\nThank U for banking!");
	        atmDisplay.close();
		}
	}
	
	private void authenticateCard() {
		atmDisplay.displayMessage( "\nPlease, enter your card number:" );
		String card = atmDisplay.getInput(); 
		
		Pattern pattern = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
		Matcher matcher = pattern.matcher(card);
		if (!matcher.find()) {
			atmDisplay.displayMessage( "\nPlease, enter valid card number: XXXX-XXXX-XXXX-XXXX" );
			return;
		}
		
		atmDisplay.displayMessage( "\nPlease, enter your PIN: " ); 
		String pin = atmDisplay.getInput(); 
		
		pattern = Pattern.compile("\\d{4}");
		matcher = pattern.matcher(pin);
		if (!matcher.find()) {
			atmDisplay.displayMessage( "\nPlease, enter valid pin number: XXXX" );
			return;
		}
		
		if(currentAccount == null) {
			currentAccount = new Account(card, pin);
		} else {
			currentAccount.setCard(card);
			currentAccount.setPin(pin);
		}
		
		if(bank.authenticate( currentAccount ) ){	
			cardAuthenticated  = true;
		}
		else {
			atmDisplay.displayMessage( "card not in bank" );
		}
	} 

	private void startSession() throws Exception {
		boolean endSession = false;
		while (!endSession) {
	
			atmDisplay.displayMessage( "\nPlease, type number:" );
			atmDisplay.displayMessage("\n1 - View Balance");
			atmDisplay.displayMessage("\n2 - Withdraw Funds");
			atmDisplay.displayMessage("\n3 - Deposit Funds");
			atmDisplay.displayMessage("\n4 - exit");
			
			int choice = atmDisplay.getInputInt(); 
			atmDisplay.clearConsole();
			switch (choice) {
			case 1:
				atmDisplay.displayMessage("\nYour Balance: " + bank.getBalance(currentAccount));
				
				break;
			case 2:
				atmDisplay.clearConsole();
				atmDisplay.displayMessage("\nType withdraw sum: ");
				int withdrawAmount = atmDisplay.getInputInt(); 
				if( withdrawAmount > cashLimit ) {
					atmDisplay.displayMessage("\nToo much, no cash! ");
					continue;
				}
				cashLimit -= withdrawAmount;
				bank.withdrawFunds(withdrawAmount, currentAccount);
				atmDisplay.displayMessage("\nGrab cash fast! ");
				
				break;
			case 3:
				atmDisplay.clearConsole();
				atmDisplay.displayMessage("\nType deposit sum: ");
				int depositAmount = atmDisplay.getInputInt(); 
				
				if(depositAmount > 1000000 ) {
					atmDisplay.displayMessage("\nIt is illegal to have so much cash! ");
					continue;
				}

				bank.depositFunds(depositAmount, currentAccount);
				atmDisplay.displayMessage("\nThankU for saving money in our bank! ");
				
				break;
			case 4:
				atmDisplay.displayMessage("\nBye! See ya!" );
				bank.saveDataToDb();
				cardAuthenticated  = false;
				endSession = true;
				break;
			default:
				System.out.println("\nInvalid Choice.");
				//throw new AtmException(ExceptionStatus.ERROR , "Session closed");				
			} 
		}
	}
}

