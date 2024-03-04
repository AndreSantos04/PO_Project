package xxl.searches;

import xxl.contents.functions.Function;
import xxl.contents.literals.Literal;
import xxl.contents.Reference;

public class ValueSearch extends CellPredicate{

    public ValueSearch(String value){
        super(value);
    }

    @Override
    public boolean ok(Literal literal){   
        return super.getValue().equals(literal.acceptAsString(literal.getAsStringVisitor()));
    }

    @Override
    public  boolean ok(Reference reference){
        return super.getValue().equals(reference.getLiteral().acceptAsString(reference.getAsStringVisitor()));
    }

    @Override
    public  boolean ok(Function function){
        return super.getValue().equals(function.getLiteral().acceptAsString(function.getAsStringVisitor()));
    }
}
