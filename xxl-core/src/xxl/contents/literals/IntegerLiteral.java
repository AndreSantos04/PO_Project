package xxl.contents.literals;

import xxl.contents.ContentVisitor;
import xxl.contents.Content;

public class IntegerLiteral extends Literal {
    private int _value;

    public IntegerLiteral(String value) {_value = Integer.parseInt(value);}

    public int getValue(){return _value;}
    @Override
    public Content copyContent(){
        return new IntegerLiteral(acceptAsString(getAsStringVisitor()));
    }

    @Override
    public int asInt(){return _value;}
    
    @Override
    public  String acceptAsString(ContentVisitor visitor){
        return visitor.asString(this);
    }
}
