package yellow.penguin.demo.exception;

public class MissingUserArgumentsForCreationRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 2342343243241L;
	
	public MissingUserArgumentsForCreationRuntimeException(String message) {
		super(message);
	}

}
