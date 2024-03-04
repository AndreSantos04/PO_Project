package xxl.exceptions;

import java.io.Serial;

public class DuplicateUserException extends Exception{
    
    @Serial
	private static final long serialVersionUID = 202308312359L;

    private static final String ERROR_MESSAGE = "Name already exists: ";

    public DuplicateUserException(String name){
        super(ERROR_MESSAGE + name);
    }
    
}
