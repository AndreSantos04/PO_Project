package xxl.contents.functions;

import java.util.ArrayList;
import java.util.List;

import xxl.cells.Cell;
import xxl.Observer;
import xxl.cells.Range;
import xxl.exceptions.InvalidFunctionArgumentException;



public abstract class IntervalFunction extends Function implements Observer{
    
    private List<Cell> _subjects = new ArrayList<Cell>();
    private Range _range;


    public IntervalFunction(Range range, String functionName){
        super(functionName);
        _range = range;
        addAllSubjects();
        updateValue(); 
    }
    
    @Override
    public void evaluate(){
        if (subjectsChanged()){
            setChanged(true);     
            updateValue();
        }
    }

    public Range getRange(){
        return _range;
    }

    public void addAllSubjects(){
        Cell cell = _range.getFirstCell();
        
        while (cell!=null){
            _subjects.add(cell);
            cell.addObserver(this);
            cell = _range.nextCell();
        }
    }

    @Override
    public void removeFromSubjects(){
        for (Cell c : _subjects)
            c.removeObserver(this);
    }

    public List<Cell> getSubjects(){
        return _subjects;
    }

    public boolean subjectsChanged(){
        for(Cell c: _subjects){
            if(c.isChanged())
                return true;
        }
        return false;
    }

}
