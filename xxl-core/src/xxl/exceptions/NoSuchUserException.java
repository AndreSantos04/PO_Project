package xxl.exceptions;

import java.io.Serial;

public class NoSuchUserException extends Exception{
    
    @Serial
	private static final long serialVersionUID = 202308312359L;
    
    private static final String ERROR_MESSAGE = "No user named: ";

    public NoSuchUserException(String name){
        super(ERROR_MESSAGE + name);
    }
}