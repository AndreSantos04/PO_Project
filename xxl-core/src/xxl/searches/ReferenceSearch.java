package xxl.searches;

import xxl.contents.functions.Function;
import xxl.contents.literals.Literal;
import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.contents.Reference;

public class ReferenceSearch extends CellPredicate{

    public ReferenceSearch(String value){
        super(value);
    }

    @Override
    public boolean ok(Literal literal){   
        return false;
    }

    @Override
    public  boolean ok(Reference reference){
        return true;
    }

    @Override
    public  boolean ok(Function function){
        return false;
    }
}
