package xxl.cellregistries;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import xxl.cells.CellIterator;
import xxl.cells.Cell;
import xxl.cells.Key;

import xxl.contents.Content;
import xxl.contents.literals.Literal;
import xxl.contents.literals.StringLiteral;

public class DynamicCellRegistry extends CellRegistry{
    
    private Map<String,Cell> _cells;

    public DynamicCellRegistry(){_cells = new HashMap<String,Cell>();}

    @Override
    public Cell getCell(Key key){
        if (_cells.get(key.getValue())==null)
            insertCell(key, new Cell(new StringLiteral(""),key.getValue()));
        return _cells.get(key.getValue());
    }

    @Override
    public void insertCell(Key key, Cell cell){
         _cells.put(key.getValue(),cell);
    }

    @Override
    public void deleteCell(Cell cell){
        cell.setContent(new StringLiteral(""));
    }

    @Override
    public Literal getCellValue(Key key){
        return getCell(key).getValue();
    }

    @Override
    public void deleteCellRegistry(){
        _cells.clear();
    }

    @Override
    public Content getCellContent(Key key){
        return getCell(key).getContent();
    }

    @Override
    public Content copyCellContent(Key key){
        return getCell(key).copyContent();
    }

    @Override
    public Iterator<Cell> iterator(){
        return new CellIterator(_cells);
    }
}