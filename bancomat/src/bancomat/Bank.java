package bancomat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import bancomat.AtmException.ExceptionStatus;

public class Bank implements IBank<Account> {
	
	private ArrayList<Account> data;
	private final String dataPath = Bank.class.getResource("accounts.txt").getPath();
	
	@Override
	public boolean authenticate(Account account) {
		if(data != null) {
			return findAccountByCard(account.getCard(), account.getPin()) != null;
		}
		
		return false;
	}

	@Override
	public int getBalance(Account account) {
		if(data != null) {
			Account acc = findAccountByCard(account.getCard(), account.getPin());
			if(acc != null) {
				return acc.getBalance();
			}
		}

		return 0;
	}

	@Override
	public void withdrawFunds(int amount, Account account) throws AtmException {
		if(data != null) {
			Account acc = findAccountByCard(account.getCard(), account.getPin());
			if(acc != null) {
				if(acc.getBalance() - amount > 0) {
					acc.setBalance(acc.getBalance() - amount);
				} else {
					throw new AtmException(ExceptionStatus.MESSAGE, "Not enough funds!");
				}
			}
		}
	}

	@Override
	public void depositFunds(int amount, Account account) {
		if(data != null) {
			Account acc = findAccountByCard(account.getCard(), account.getPin());
			if(acc != null) {
				acc.setBalance(acc.getBalance() + amount);
			}
		}		
	}

	@Override
	public ArrayList<Account> readDataFomDb() throws AtmException {
		try {
			FileReader fileReader = new FileReader(dataPath);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        
	        if(data == null) {
	        	data = new ArrayList<Account>();
	        }
	        data.clear();
	        
	        String line = null; 
	        while ((line = bufferedReader.readLine()) != null) {
	        	String[] strArray = line.split("\\s");
	    		Account acc = new Account();
	    		acc.setCard(strArray[0]);
	    		acc.setPin(strArray[1]);
	    		acc.setBalance(Integer.parseInt(strArray[2]));
	    		data.add(acc);
	        }
	        
	        bufferedReader.close();
	        
	        return data;
	        
		} 
		catch (final IOException ex) {
			throw new AtmException(ExceptionStatus.ERROR, "ERROR! Please, visit branch!"); 

	    }
	}

	@Override
	public void saveDataToDb() throws AtmException {
		try {
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter(dataPath, false));
            for (Account account : data) {
            	writer.write(account.getCard() + " " + account.getPin() + " "  + Integer.toString(account.getBalance()));
            	writer.newLine();
            }
            writer.flush();
            writer.close();        
		} 
		catch (final IOException ex) {
			throw new AtmException(ExceptionStatus.ERROR, "ERROR! Please, visit branch!"); 
        }
	}

	private Account findAccountByCard(String card, String pin){
	    for (Account account : data) {
	        if (account.getCard().equals(card) && account.getPin().equals(pin)) {
	            return account;
	        }
	    }
	    
	    return null;
	}
	
}
