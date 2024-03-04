package xxl.contents.literals;

import xxl.contents.ContentVisitor;

import xxl.contents.Content;

public class StringLiteral extends Literal {
    private String _value;

    public StringLiteral(String value) {
        _value = value;
    }
    public String getValue(){
        return _value;
    }
    @Override
    public Content copyContent(){
        return new StringLiteral(_value);
    }
    

    @Override
    public  String acceptAsString(ContentVisitor visitor){
        return visitor.asString(this);
    }
}
