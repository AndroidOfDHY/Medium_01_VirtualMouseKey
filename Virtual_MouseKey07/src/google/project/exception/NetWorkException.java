package google.project.exception;

public class NetWorkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7376074434975493398L;
	
	public NetWorkException() {
	}

	public NetWorkException(String detailMessage) {
		super(detailMessage);
	}

	public NetWorkException(Throwable throwable) {
		super(throwable);
	}

	public NetWorkException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
