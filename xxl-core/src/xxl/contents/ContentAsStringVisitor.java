package xxl.contents;

import java.io.Serializable;
import xxl.contents.functions.Function;
import xxl.contents.literals.IntegerLiteral;
import xxl.contents.literals.StringLiteral;

public class ContentAsStringVisitor implements ContentVisitor,Serializable{
    
    @Override
    public String asString(Function function){
        return function.getLiteral().acceptAsString(this)+function.getFunctionName();
    }
    
    @Override
    public String asString(Reference reference){

            String referencedKeyValue = reference.getReferencedKey().getValue();
            String temp = reference.getLiteral().acceptAsString(this);

            if(temp.equals("\'") || temp.isBlank())
               return "#VALUE"+"="+referencedKeyValue;
            return temp+"="+referencedKeyValue;
    }

    @Override
    public String asString(StringLiteral stringLiteral){
        return stringLiteral.getValue();
    }

    @Override
    public String asString(IntegerLiteral integerLiteral){
        return Integer.toString(integerLiteral.getValue());
    }

    

    
    

    
}
