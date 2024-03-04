package xxl.contents.functions;

import xxl.exceptions.InvalidFunctionArgumentException;

import xxl.cells.Cell;
import xxl.cells.Range;
import xxl.contents.Content;
import xxl.contents.literals.IntegerLiteral;
import xxl.contents.literals.StringLiteral;

public class Average extends IntervalFunction {

    public Average(Range range, String functionName){
        super(range, functionName);
    }

    @Override
    public Content copyContent(){
        return new Average(getRange(), getFunctionName());
    }

    @Override
    public void updateValue(){

        int res = 0;
        int length = 0;
        try{
            for(Cell s: getSubjects()){
                res += s.getValue().asInt();
                length++;
            }
            setValue(new IntegerLiteral(Integer.toString(res/length)));     

        }catch(InvalidFunctionArgumentException e){
            setValue(new StringLiteral("#VALUE"));
        }
    }
}

