package db;

public class DBIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
 // CONSTRUTOR
	public DBIntegrityException (String msg) {
		super(msg);
	}
}