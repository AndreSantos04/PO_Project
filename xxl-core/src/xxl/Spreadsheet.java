package xxl;

import java.io.Serial;
import java.io.Serializable;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import xxl.exceptions.UnrecognizedEntryException;
import xxl.exceptions.InvalidCellRangeCoreException;
import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.exceptions.UnknownCoreFunctionException;

import xxl.cellregistries.*;

import xxl.cells.*;

import xxl.contents.*;
import xxl.contents.literals.*;
import xxl.contents.functions.*;

import xxl.searches.*;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {

    @Serial
    private static final long serialVersionUID = 202308312359L;

    private int _maxLine;
    private int _maxCol;
    private CellRegistry _cells;
    private ArrayList<User> _users;
    private CellRegistry _cutBuffer;
    private boolean _isChanged = false;
    private boolean _isCutBufferVertical;
    private int _cutBufferLength;

    public Spreadsheet(int lines, int columns){
        
        _maxLine = lines;
        _maxCol = columns;
        _cells = new DynamicCellRegistry();
        _users = new ArrayList<User>();
        _cutBuffer = new DynamicCellRegistry();
        _isChanged = true;
    }

    public ArrayList<User> getUsers(){
        return _users;
    }

    public boolean isChanged(){
        return _isChanged;
    }

    public void setIsChanged(boolean change){
        _isChanged = change;
    }

    public List<String> searchValue(String value){
        List<String> show;
        CellPredicate predicate = new ValueSearch(value);
        show = search(value,predicate);
        return Collections.unmodifiableList(show);
    }

    public List<String> searchFunction(String value){
        List<String> show;
        CellPredicate predicate = new FunctionSearch(value);
        show = search(value,predicate);
        Collections.sort(show, new FunctionComparator());
        return Collections.unmodifiableList(show);
    }

    public Collection<String> searchEven(String value){
        List<String> show;
        CellPredicate predicate = new EvenSearch(value);
        show = search(value,predicate);
        return Collections.unmodifiableList(show);
    }
    
    public Collection<String> searchReference(String value){
        List<String> show;
        CellPredicate predicate = new ReferenceSearch(value);
        show = search(value,predicate);
        return Collections.unmodifiableList(show);
    }

    private List<String> search(String value, CellPredicate predicate){
        
        List <String> show = new ArrayList<String>();
        
        for(Cell cell: _cells){
            if(cell.getContent().accept(predicate)){
                Content content = cell.getContent();
                show.add(cell.getKey()+"|"+content.acceptAsString(content.getAsStringVisitor()));
            }
        }
        return show;
    }


    public void copy(String rangeSpecification) 
    throws InvalidCellRangeCoreException {
        Range range = handleRange(rangeSpecification);
        int rangeLength = range.getLength();
        Cell cell = range.getFirstCell();
        _cutBuffer.deleteCellRegistry();

        Key cutBufferKey = new Key("1;1");

        if (range.isVertical())
            _isCutBufferVertical = true;
        else
            _isCutBufferVertical = false;

        for(int i = 0; i < rangeLength; i++){
            
            _cutBuffer.insertCell(cutBufferKey, new Cell(cell.copyContent(),cutBufferKey.getValue()));
            _cutBuffer.getCell(cutBufferKey).setInCutBuffer(true);

            if(_isCutBufferVertical)
                cutBufferKey.nextVertical();
            else
                cutBufferKey.nextHorizontal();
            
            cell = range.nextCell();
        }

        _cutBufferLength = rangeLength;
    }

    public void delete(String rangeSpecification)
    throws InvalidCellRangeCoreException {
        Range range = handleRange(rangeSpecification);
        Cell cell = range.getFirstCell();

        while(cell != null){
            _cells.deleteCell(cell);
            cell.getContent().removeFromSubjects();
            cell = range.nextCell();
        }
    }

    public void cut(String rangeSpecification) 
    throws InvalidCellRangeCoreException{
        copy(rangeSpecification);
        delete(rangeSpecification);
    }


    public void paste(String rangeSpecification)
    throws InvalidCellRangeCoreException{
        Range range = handleRange(rangeSpecification);
        Cell cell = range.getFirstCell();
        Key cutBufferKey = new Key("1;1");
        
        if(_cutBufferLength == 0){return;}
        else if(range.getLength() == _cutBufferLength && _isCutBufferVertical==range.isVertical()){
            for(int i = 0; i < _cutBufferLength; i++){

                cell.setContent(_cutBuffer.copyCellContent(cutBufferKey));
                cell.setInCutBuffer(false);
                cell = range.nextCell();

                if(_isCutBufferVertical)
                    cutBufferKey.nextVertical();
                else
                    cutBufferKey.nextHorizontal();
            }
        }
        else if(range.getLength() == 1){
            Key firstKey = range.getFirstKey();
            Key cellKey = firstKey;

            int length = _cutBufferLength;
            int firstI, secondI;
            int max1, max2;

            if(_isCutBufferVertical){
                firstI = firstKey.getColumn();
                secondI = firstKey.getLine();
                max1 = _maxCol;
                max2 = _maxLine;
            }
            else{
                firstI = firstKey.getLine();
                secondI = firstKey.getLine();
                max1 = _maxLine;
                max2 = _maxCol;      
            }

            for(int i = firstI ; i <= max1; i++){
                for(int j = secondI; j <= max2 && length>0; j++, length--){
                    cell = _cells.getCell(cellKey);
                    cell.setContent(_cutBuffer.copyCellContent(cutBufferKey));
                    cell.setInCutBuffer(false);
                    if(_isCutBufferVertical){
                        cellKey.nextVertical();
                        cutBufferKey.nextVertical();
                    }
                    else{
                        cellKey.nextHorizontal();
                        cutBufferKey.nextHorizontal();
                    }
                }
                if(length == 0) break;
                if(_isCutBufferVertical)
                    cellKey.setValue("1;"+cellKey.getColumn()+1);
                else 
                    cellKey.setValue(cellKey.getLine()+1+";1");
            }  
        }
    }
 
    /**
     * Insert specified content in specified range.
     *
     * @param rangeSpecification
     * @param contentSpecification
     */
    public void insertContents(String rangeSpecification, String contentSpecification) 
    throws UnrecognizedEntryException, InvalidCellRangeCoreException, 
    UnknownCoreFunctionException,InvalidFunctionArgumentException {
        
        Content _content = null;
        Range _range = handleRange(rangeSpecification);
        
        _isChanged = true;
        _content = switch(checkType(contentSpecification)){
                    case "Integer" -> new IntegerLiteral(contentSpecification);
                    case "String"  -> new StringLiteral(contentSpecification);
                    case "Reference" -> {Key referencedKey = new Key(contentSpecification.split("=")[1]);
                                        yield new Reference(referencedKey,_cells.getCell(referencedKey));}
                    case "Function" -> registerFunction(contentSpecification);
                    default -> {
                        throw new UnrecognizedEntryException(contentSpecification);
                    }
                };

        Cell cell = _range.getFirstCell();
        while (cell != null){
            cell.setContent(_content);
            cell = _range.nextCell();   
        }
    }   

    private Content parseToContent(String input) 
        throws InvalidFunctionArgumentException{
        
        Content content;
        content = switch(checkArgumentsType(input)){
                    case "Integer" -> new IntegerLiteral(input);
                    case "String" -> new StringLiteral(input);
                    case "Reference" ->{Key key = new Key(input);
                                        yield new Reference(key,_cells.getCell(key));}
                    default -> {
                        throw new InvalidFunctionArgumentException();
                    }
                };
                return content;
        }
    

    public List<String> showCutBuffer(){
        List <String> show = new ArrayList<String>();
        int max1;
        int max2;
        int length = _cutBufferLength;

            if(_isCutBufferVertical){
                max1 = _maxCol;
                max2 = _maxLine;
            }
            else{
                max1 = _maxLine;
                max2 = _maxCol;      
            }
            
            Key cutBufferKey = new Key("1;1");
            for(int j = 1; j <= max1; j++){
                for(int k = 1; k <= max2 && length>0; k++, length--){

                    show.add(_cutBuffer.getCell(cutBufferKey).toString());
                    
                    if(_isCutBufferVertical)
                        cutBufferKey.nextVertical();
                    else
                        cutBufferKey.nextHorizontal();
                }
                if(length == 0) break;
                if(_isCutBufferVertical)
                    cutBufferKey.setValue("1;"+cutBufferKey.getColumn()+1);
                else 
                    cutBufferKey.setValue(cutBufferKey.getLine()+1+";1");
            }
        
        return Collections.unmodifiableList(show);
    } 


    public List<String> show(String gama) throws InvalidCellRangeCoreException{

        Range range = handleRange(gama);
        Cell cell = range.getFirstCell();
        List <String> show = new ArrayList<String>();
   
        while(cell != null){
            show.add(cell.toString());
            cell = range.nextCell();
        }
        show.add("Numero de Celulas:" + range.getLength());

        return Collections.unmodifiableList(show);
    }

    private boolean isValidKey(String key){
        String[] split = key.split(";");
        try {
            int line = Integer.parseInt(split[0]);
            int col = Integer.parseInt(split[1]); 
            if(line<=_maxLine && line>0 && col<=_maxCol && col>0)
                return true;
            return false;
        } catch (NumberFormatException e){
            return false;
        }
        
    }

    private Range handleRange(String range) throws InvalidCellRangeCoreException{
        Key firstKey = null;
        Key temp = null;
        Key lastKey = null;
        ArrayList<Cell> rangeList = new ArrayList<>();

        if(range.contains(":")){
            String[] keys = range.split(":");
            if(isValidKey(keys[0]) && isValidKey(keys[1])){
                
                firstKey = new Key(keys[0]);
                lastKey = new Key(keys[1]);
                temp = new Key(keys[0]);
                if(firstKey.compare(firstKey,lastKey) > 0){
                    
                    temp = firstKey;
                    firstKey = lastKey;
                    lastKey = temp;
                    
                    temp = new Key(keys[1]);              
                }   
                
                while(temp.compare(temp,lastKey) <= 0){
                     
                    rangeList.add(_cells.getCell(temp));
                    if(temp.areVertical(temp,lastKey))
                        temp.nextVertical();
                    else
                        temp.nextHorizontal();
                }
            }
            else 
                throw new InvalidCellRangeCoreException();
        }
        else if (range.contains(";") && isValidKey(range)){
            
                firstKey = new Key(range);
                lastKey = firstKey;
                rangeList.add(_cells.getCell(firstKey));
        }   
        else 
            throw new InvalidCellRangeCoreException();
        
        return new Range(firstKey,lastKey,rangeList);
    }

    private Content registerFunction(String input) 
    throws InvalidFunctionArgumentException,UnknownCoreFunctionException,
    InvalidCellRangeCoreException,UnrecognizedEntryException{
        
        String[] funcAndArgs = input.split("\\(");
        String functionName = funcAndArgs[0];
        String arguments = funcAndArgs[1].split("\\)")[0];

        Content content = null;
        Content argument1 = null;
        Content argument2 = null;

        Range range = null;
        
        if(arguments.contains(",")){
            String[] args = arguments.split(",");
            argument1  = parseToContent(args[0]);
            argument2  = parseToContent(args[1]);
        }
        else if(arguments.contains(":")){
            range = handleRange(arguments);
             
            
        }
        else{ throw new InvalidFunctionArgumentException();}
                
        content = switch(functionName){
                    case "=ADD" ->      new Add(argument1,argument2,input);
                    case "=SUB" ->      new Sub(argument1,argument2,input);
                    case "=MUL" ->      new Mul(argument1,argument2,input);
                    case "=DIV" ->      new Div(argument1,argument2,input);
                    case "=AVERAGE" ->  new Average(range,input);
                    case "=PRODUCT" ->  new Product(range,input);
                    case "=CONCAT" ->   new Concat(range,input);
                    case "=COALESCE" -> new Coalesce(range,input);
                    default-> {
                        throw new UnknownCoreFunctionException();
                    }
        };
        return content;
    }

    private String checkType(String input){
        
        if(input.isEmpty() || input.charAt(0) == '\'') return "String";
        else if(input.charAt(0) == '=' ){
            if(Character.isDigit(input.charAt(1))) return "Reference";
            else return "Function";
        }
        else
            try{
                Integer.parseInt(input);
                return "Integer";
            } catch(NumberFormatException e) {
                return "";
            }
    }

    private String checkArgumentsType(String input){
        if(input.charAt(0) == '\'') return "String";
        else if (input.contains(";")) return "Reference";
        else{
            try{
                Integer.parseInt(input);
                return "Integer";
            } 
            catch(NumberFormatException e) {
                return "";
            }       
        }
    }
}
    
