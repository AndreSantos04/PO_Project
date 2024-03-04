package xxl.cells;

import java.io.Serializable;

import xxl.exceptions.InvalidCellRangeCoreException;

public class Key implements Serializable{
    
    private String _value;
    private String[] _position; 
    
    public Key(String value){
        _value = value;
        _position = _value.split(";");
    }
    
    public int getLine(){
        return Integer.parseInt(_position[0]);
    }

    public int getColumn(){
        return Integer.parseInt(_position[1]);
    }

    public String getValue(){return _value;}

    public void setValue(String value){
        _value = value;
        _position = _value.split(";");
        
    }

    public void nextHorizontal(){
        int nextColumn = this.getColumn()+1;
        setValue(getLine() + ";" + nextColumn);
    }

    public void nextVertical(){
        int nextLine= this.getLine()+1;
        setValue(nextLine + ";" + getColumn());
    }

    public int compare(Key key1,Key key2) throws InvalidCellRangeCoreException{
        int key1Line = key1.getLine();
        int key2Line = key2.getLine();
        int key1Column = key1.getColumn();
        int key2Column = key2.getColumn();
        
        if(key1Line == key2Line)
            return key1Column-key2Column;
        if(key1Column == key2Column)
            return key1Line-key2Line;
        throw new InvalidCellRangeCoreException();
   
    }
    public boolean areVertical(Key key1,Key key2){
        if(key1.getColumn() == key2.getColumn())
            return true;
        return false;
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof Key key){
            return this._value.equals(key.getValue());
        }
        return false;
    }
}
