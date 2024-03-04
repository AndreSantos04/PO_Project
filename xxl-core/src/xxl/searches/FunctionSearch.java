package xxl.searches;

import xxl.contents.functions.Function;
import xxl.contents.literals.Literal;
import xxl.contents.Reference;

public class FunctionSearch extends CellPredicate{
    
    public FunctionSearch(String value){
        super(value);
    }
    @Override
    public  boolean ok(Literal literal){
        return false;
    }
    @Override
    public  boolean ok(Reference reference){
        return false;
    }

    @Override
    public boolean ok(Function function){ 
            return function.getFunctionName().contains(super.getValue());
    }
}
