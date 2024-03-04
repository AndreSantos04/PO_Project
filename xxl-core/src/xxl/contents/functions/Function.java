package xxl.contents.functions;

import xxl.contents.ContentVisitor;

import xxl.contents.Content;
import xxl.contents.literals.Literal;
import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.searches.CellPredicate;

public abstract class Function extends Content {
    
    private String _functionName;
    private Literal _value;

    public Function(String functionName){_functionName = functionName;}

    @Override
    public Literal getLiteral(){
        if(!isInCutBuffer())
            evaluate();
        return _value;    
    }

    public String getFunctionName(){
        return _functionName;
    }

    public void setValue(Literal value){_value = value;}

    public abstract void evaluate();
    
    @Override
    public boolean accept(CellPredicate predicate){
        if(predicate != null){
            return predicate.ok(this);
        }
        return false;
    }

    @Override
    public  String acceptAsString(ContentVisitor visitor){
        return visitor.asString(this);
    }   
}
