package bancomat;

import java.util.ArrayList;

public interface IBank<T> {
	boolean authenticate( T account );
	int getBalance(T account );
	void withdrawFunds(int amount, T account) throws Exception;
	void depositFunds(int amount, T account);
	ArrayList<T> readDataFomDb() throws Exception;
	void saveDataToDb() throws Exception;
}
