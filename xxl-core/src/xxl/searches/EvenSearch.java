package xxl.searches;

import xxl.contents.functions.Function;
import xxl.contents.literals.Literal;
import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.contents.Reference;

public class EvenSearch extends CellPredicate{

    public EvenSearch(String value){
        super(value);
    }

    @Override
    public boolean ok(Literal literal){   
        try{
            return (literal.asInt()%2==0);
        }catch(InvalidFunctionArgumentException e){return false;}
    }

    @Override
    public  boolean ok(Reference reference){
        try{
            return (reference.getLiteral().asInt()%2==0);
        }catch(InvalidFunctionArgumentException e){return false;}
    }

    @Override
    public  boolean ok(Function function){
        try{
            return (function.getLiteral().asInt()%2==0);
        }catch(InvalidFunctionArgumentException e){return false;}
    }
}
