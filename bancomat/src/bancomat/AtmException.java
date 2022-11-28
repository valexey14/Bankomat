package bancomat;

public class AtmException extends Exception {
	public ExceptionStatus status;
	
	public AtmException(ExceptionStatus status, String message) { 
		super(message); 
		this.status = status;
	}
	
	public enum ExceptionStatus {
	    ERROR,
	    MESSAGE,
	    SUCCESS
	  }
}
