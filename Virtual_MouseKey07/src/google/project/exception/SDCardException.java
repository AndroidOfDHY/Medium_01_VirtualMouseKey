package google.project.exception;

@SuppressWarnings("serial")
public class SDCardException extends Exception {

	public SDCardException() {
	}

	public SDCardException(String detailMessage) {
		super(detailMessage);
	}

	public SDCardException(Throwable throwable) {
		super(throwable);
	}

	public SDCardException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
