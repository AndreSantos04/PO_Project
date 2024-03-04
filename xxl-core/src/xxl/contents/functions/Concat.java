package xxl.contents.functions;

import xxl.cells.Cell;
import xxl.cells.Range;
import xxl.contents.Content;
import xxl.contents.literals.Literal;
import xxl.contents.literals.StringLiteral;

public class Concat extends IntervalFunction{
    
    

    public Concat(Range range, String functionName){
        super(range, functionName);
    }

    @Override
    public Content copyContent(){
        return new Concat(getRange(), getFunctionName());
    }

    @Override
    public void updateValue(){

        String res = "\'";

        for(Cell s: getSubjects()){
            Literal value = s.getValue();
            if(value.isString()){
                res += value.acceptAsString(value.getAsStringVisitor()).split("\'")[1];
            }
        }
        setValue(new StringLiteral(res));
    }
}

