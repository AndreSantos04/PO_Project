package xxl.exceptions;

import java.io.Serial;

public class InvalidSpreadSheetSizeException extends Exception{
    
    //When one of the dimensions of the spreadsheet is negative or zero
    @Serial
	private static final long serialVersionUID = 202308312359L;
}
