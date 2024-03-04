package xxl.searches;

import xxl.contents.functions.Function;
import xxl.contents.literals.Literal;
import xxl.contents.Reference;

public abstract class CellPredicate{

    private String _value;

    public CellPredicate(String value){
        _value = value;
    }

    public String getValue(){ return _value;}
    
    public abstract boolean ok(Literal literal);

    public abstract boolean ok(Reference reference);

    public abstract boolean ok(Function function);
}