package xxl.cells;

import java.io.Serializable;
import java.util.ArrayList;


public class Range implements Serializable {

    private Key _firstKey;
    private Key _lastKey;
    private int _currentIndex = 0;
    private ArrayList<Cell> _cells;

    public Range(Key firstKey,Key lastKey, ArrayList<Cell> cells){
        _firstKey = firstKey; _lastKey = lastKey; _cells = cells; 
    }
    
    public int getFirstKeyLine(){
        return _firstKey.getLine();
    }

    public int getFirstKeyColumn(){
        return _firstKey.getColumn();
    }

    public int getLastKeyLine(){
        return _lastKey.getLine();
    }

    public int getLastKeyColumn(){
        return _lastKey.getColumn();
    }
    
    public Cell getFirstCell(){
        return _cells.get(0);
    }

    public int getLength(){
        if(isVertical()){
            return getLastKeyLine() - getFirstKeyLine() +1;
        }
            return getLastKeyColumn() - getFirstKeyColumn() +1;
    }
    
    public Key getCurrentCellKey(){
        
        if(isVertical()){
    
            return new Key((getFirstKeyLine()+_currentIndex)+";"+getFirstKeyColumn());
        } 
            return new Key(getFirstKeyLine()+";"+(getFirstKeyColumn()+_currentIndex));
    }

    public boolean isVertical(){
        return getFirstKeyColumn()==getLastKeyColumn();
    }

    public Key getFirstKey(){return _firstKey;}

    public Cell nextCell(){
    
        if((++_currentIndex) == _cells.size()){
            _currentIndex = 0;
            return null;
        }
        return _cells.get(_currentIndex);
    }
}

