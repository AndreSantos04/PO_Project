package xxl.contents;

import java.io.Serializable;

import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.searches.CellPredicate;
import xxl.contents.literals.Literal;

public abstract class Content implements Serializable{
    
    private boolean _changed = true;
    
    private boolean _inCutBuffer = false;

    private ContentAsStringVisitor _asStringVisitor = new ContentAsStringVisitor();
    
    public boolean isInCutBuffer(){
        return _inCutBuffer;
    }

    public void setInCutBuffer(boolean inCutBuffer){
        _inCutBuffer = inCutBuffer;
    }
    
    public abstract Literal getLiteral();

    public int asInt() throws InvalidFunctionArgumentException{ 
        throw new InvalidFunctionArgumentException();}

    public void removeFromSubjects(){};
    
    public abstract Content copyContent();

    public void setChanged(boolean changed){
        _changed = changed;
    }
    public boolean getChanged(){
        return _changed;
    }

    public ContentAsStringVisitor getAsStringVisitor(){
        return _asStringVisitor;
    }

    public abstract boolean accept(CellPredicate predicate);
    
    public abstract String acceptAsString(ContentVisitor visitor); 
}
