package xxl.cells;

import java.util.Map;
import java.util.Iterator;

public class CellIterator implements Iterator<Cell>{
    
    private Iterator<Map.Entry<String,Cell>> _iterator;

    public CellIterator(Map<String,Cell> cells){
        _iterator = cells.entrySet().iterator();
    }

    @Override
    public boolean hasNext(){
        return _iterator.hasNext();
    }
     
    @Override
    public Cell next(){
        Map.Entry<String, Cell> entry = _iterator.next();
        return entry.getValue();
    }
}