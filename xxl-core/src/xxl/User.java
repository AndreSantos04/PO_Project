package xxl;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String _name;
    private ArrayList<Spreadsheet> _spreadsheets;
    private boolean _isActive = false;

    public User(String name){
        _name = name;
        _spreadsheets = new ArrayList<Spreadsheet>();
    }

    public String getName() {
        return _name;
    }

    public final void setName(String name) {
        _name = name;
    }

    public ArrayList<Spreadsheet> getSpreadsheets(){
        return _spreadsheets;
    }
    
    public boolean isActive(){
        return _isActive;
    }

}
