package bancomat;

import java.util.Scanner;

public class AtmDisplay {
	Scanner scaner = new Scanner(System.in);
	
	public void displayMessage(String msgLine) {
		System.out.println(msgLine);
	}

	public String getInput() {
		return scaner.next();
	}
	
	public int getInputInt() {
		return scaner.nextInt();
	}
	
	public void close() {
		scaner.close();
	}
	
	public void clearConsole() {
		//do later
	/*	try  {  
			final String os = System.getProperty("os.name");  
			if (os.contains("Windows"))  {  
				Runtime.getRuntime().exec("cls");  
			}  
			else{  
				Runtime.getRuntime().exec("clear");  
			}  
		}  
		catch (final Exception e) {  
			e.printStackTrace();  
		}  
	*/}  

}
