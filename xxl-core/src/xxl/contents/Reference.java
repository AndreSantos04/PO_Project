package xxl.contents;

import xxl.Observer;
import xxl.cells.Cell;
import xxl.cells.Key;
import xxl.contents.literals.Literal;
import xxl.searches.CellPredicate;

public class Reference extends Content implements Observer{

    private Key _referenceKey;
    private Cell _cellSubject; 
    private Literal _value;
    
    public Reference(Key referencedKey, Cell cell){
        _referenceKey = referencedKey;
        _cellSubject = cell;
        _cellSubject.addObserver(this);
        updateValue();
    }
       
    public Literal getLiteral(){
        if (!isInCutBuffer() && _cellSubject.isChanged())
            updateValue();
        return _value;
    }

    public Key getReferencedKey(){
        return _referenceKey;
    }

    @Override
    public Content copyContent(){
        return new Reference(_referenceKey, _cellSubject);
    }

    @Override
    public void removeFromSubjects(){
        _cellSubject.removeObserver(this);
    }

    public void updateValue(){
        _value = _cellSubject.getValue();
        setChanged(true);
    }

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

