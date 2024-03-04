package xxl.contents.functions;

import xxl.exceptions.InvalidFunctionArgumentException;

import xxl.cells.Cell;
import xxl.cells.Range;
import xxl.contents.Content;
import xxl.contents.literals.IntegerLiteral;
import xxl.contents.literals.StringLiteral;

public class Product extends IntervalFunction{
    
    public Product(Range range, String functionName){
        super(range,functionName);
    }

    @Override
    public Content copyContent(){
        return new Product(getRange(), getFunctionName());
    }

    @Override
    public void updateValue(){

        int res = 1;
        try{
            for(Cell s: getSubjects()){
                res *= s.getValue().asInt();
            }
            setValue(new IntegerLiteral(Integer.toString(res)));  
              

        }catch(InvalidFunctionArgumentException e){
            setValue(new StringLiteral("#VALUE"));
        }
    }
}
