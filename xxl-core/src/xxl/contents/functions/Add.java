package xxl.contents.functions;

import xxl.exceptions.InvalidFunctionArgumentException;

import xxl.contents.Content;
import xxl.contents.literals.*;

public class Add extends BinaryFunction {

    public Add(Content content1, Content content2, String functionName){
        super(content1,content2,functionName);
    }  

    @Override
    public Content copyContent(){
        return new Add(getContent1(), getContent2(), getFunctionName());
    }

    @Override
    public void updateValue(Literal literal1, Literal literal2){
        try{
            int c1 = literal1.asInt();
            int c2 = literal2.asInt();
            int c3 = c1+c2;
            setValue(new IntegerLiteral(Integer.toString(c3)));

        }catch(InvalidFunctionArgumentException e){
            setValue(new StringLiteral("#VALUE"));   
        }  
    }  
}

