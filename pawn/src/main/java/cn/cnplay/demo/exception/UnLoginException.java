package cn.cnplay.demo.exception;

public class UnLoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnLoginException(){
		super();
	}
	
	public UnLoginException(String message){
		super(message);
	}
}
