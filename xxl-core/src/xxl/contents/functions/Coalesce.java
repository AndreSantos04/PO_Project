package xxl.contents.functions;

import xxl.cells.Cell;
import xxl.cells.Range;
import xxl.contents.Content;
import xxl.contents.literals.Literal;
import xxl.contents.literals.StringLiteral;

public class Coalesce extends IntervalFunction{

    public Coalesce(Range range, String functionName){
        super(range, functionName);
    }

    @Override
    public Content copyContent(){
        return new Coalesce(getRange(), getFunctionName());
    }

    @Override
    public void updateValue(){
       
        Literal firstString = new StringLiteral("\'");

        for(Cell s: getSubjects()){
            Literal value = s.getValue();
            if(value.isString() && !value.acceptAsString(value.getAsStringVisitor()).isBlank()){
                firstString = value;
                break;
            }
        }
        setValue(firstString);     
    }
}
