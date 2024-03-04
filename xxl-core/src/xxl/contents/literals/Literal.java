package xxl.contents.literals;

import xxl.contents.ContentVisitor;
import xxl.contents.ContentAsStringVisitor;
import xxl.contents.Content;

import xxl.searches.CellPredicate;
public abstract class Literal extends Content {
    
    ContentAsStringVisitor Stringvisitor = getAsStringVisitor();

    public abstract String acceptAsString(ContentVisitor visitor);

    public Literal getLiteral(){return this;}   

    public boolean isString(){
        if(!getLiteral().acceptAsString(Stringvisitor).isBlank()
         && getLiteral().acceptAsString(Stringvisitor).charAt(0) == '\'') 
            return true;
        return false;
    }

    @Override
    public boolean accept(CellPredicate predicate){
        if(predicate != null){
            return predicate.ok(this);
        }
        return false;
    }  
}


