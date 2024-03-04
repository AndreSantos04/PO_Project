package xxl.cellregistries;

import java.io.Serializable;

import java.util.Iterator;

import xxl.cells.Cell;
import xxl.cells.Key;
import xxl.contents.Content;
import xxl.contents.literals.Literal;


public abstract class CellRegistry implements Serializable, Iterable<Cell> {
    
    public abstract Cell getCell(Key key);
    public abstract void insertCell(Key key, Cell cell);
    public abstract void deleteCell(Cell cell);
    public abstract Literal getCellValue(Key key);
    public abstract void deleteCellRegistry();
    public abstract Content getCellContent(Key key);
    public abstract Content copyCellContent(Key key);
    public abstract Iterator<Cell> iterator();

}
