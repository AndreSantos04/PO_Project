package xxl;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.util.Map;

import xxl.exceptions.ImportFileException;
import xxl.exceptions.InvalidCellRangeCoreException;
import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.exceptions.InvalidSpreadSheetSizeException;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;
import xxl.exceptions.UnknownCoreFunctionException;
import xxl.exceptions.UnrecognizedEntryException;
import xxl.exceptions.DuplicateUserException;
import xxl.exceptions.NoSuchUserException;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {

    /** The current spreadsheet. */
    private Spreadsheet _spreadsheet = null;

    private String _currentFileName = null;

    private boolean isSpreadsheetOpen = false;

    private Map<String,User> _users = new HashMap<>(){{
        put("root", new User("root"));
    }};

    /*will be defined in the getActiveUser method */
    private User _activeUser = _users.get("root");
    /**
     * Create spreadsheet
     * @param lines vertical dimension of the spreadsheet
     * @param columns horizontal dimension of the spreadsheet
     */
    public void createSpreadsheet(int lines, int columns){
        try{
            if(lines < 1 || columns < 1)
                throw new InvalidSpreadSheetSizeException();
            _spreadsheet = new Spreadsheet(lines,columns);
            addUserToSheetAndOposite();
            
            isSpreadsheetOpen = true;
            _currentFileName = null;
            
        }
        catch(InvalidSpreadSheetSizeException e){e.printStackTrace();}
        
    }

    /**
     * @return the spreadsheet
     */
    public Spreadsheet getSpreadsheet(){
        return _spreadsheet;
    }

    /**
     * @return true if the spreadsheet is changed else false
     */
    public boolean isSpreadsheetChanged(){
        return _spreadsheet.isChanged();
    }

    /**
     * @return true if the spreadsheet is open else false
     */
    public boolean isSpreadsheetOpen(){ 
        return isSpreadsheetOpen;
    }

    /**
     * @return the name of the file that contains the current spreadsheet
     */
    public String currentFileName(){
        return _currentFileName;
    }

    /**
     * Creates a new user if there is not one with the same nameand
     * inserts it in the map _users
     * @param name user's name
     * @throws DuplicateUserException no caso de já existir um 
     * utilizador com o nome name
     */
    public void registerUser(String name) throws DuplicateUserException{
        if(_users.containsKey(name))
            throw new DuplicateUserException(name);
        _users.put(name,new User(name));
    }
    
    /**
     * @return the active user at the moment, default is user root
     */
    public User getActiveUser(){
        return _activeUser;
    }

    /**
     * @param username new active user
     * @throws NoSuchUserException 
     */
    public void setActiveUser(String username)throws NoSuchUserException{
        if(_users.containsKey(username))
            _activeUser = _users.get(username);
        else throw new NoSuchUserException(username);
    }
    
    //Creates the link between the active user and the spreadsheet
    private void addUserToSheetAndOposite(){ 
        User activeUser = getActiveUser();  
        activeUser.getSpreadsheets().add(_spreadsheet);
        _spreadsheet.getUsers().add(activeUser);
    }
    
    /**
     * Saves the serialized application's state into the file associated to the current spreadsheet.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
     * @throws MissingFileAssociationException if the current spreadsheet does not have a file.
     * @throws IOException if there is some error while serializing the state of the spreadsheet to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {

        //Tries to serialize the spreadsheet and save it in the file that is open at the moment if there is any
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_currentFileName)))){
            oos.writeObject(_spreadsheet);
            _spreadsheet.setIsChanged(false);
        }
    }

    /**
     * Saves the serialized application's state into the specified file.
     *  The current spreadsheet is associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current spreadsheet does not have a file.
     * @throws IOException if there is some error while serializing the state of the spreadsheet to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, 
            MissingFileAssociationException, IOException {

        /* Updates the _currentFileName with the given filename so 
        this variable can be used when calling save() */
        _currentFileName = filename;
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     * @throws ClassNotFoundException if the class Spreadsheet is not found when deserializing
     * @throws IOException for all the exceptions that might occur and are not mentioned 
     */
    public void load(String filename) throws UnavailableFileException, ClassNotFoundException, IOException {

        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
            _spreadsheet = (Spreadsheet)ois.readObject();
            _currentFileName = filename;
            _spreadsheet.setIsChanged(false);
        }
        catch(FileNotFoundException e){ throw new UnavailableFileException(filename);}
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException if any exception occurs when importing a file
     */
    public void importFile(String filename) throws ImportFileException{
        
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            
            String line;
            int[] size = new int[2];
            for (int iX = 0; iX<2;iX++){
                line = reader.readLine().split("=")[1];
                size[iX] = Integer.parseInt(line); 
            }
            createSpreadsheet(size[0], size[1]);

            while((line = reader.readLine()) != null){
                //Processes each line of the file and creates domain entities based on the entries.            
                    String[] data = line.split("\\|",-1);
                    _spreadsheet.insertContents(data[0],data[1]);
                }
        }
        catch(FileNotFoundException e){throw new ImportFileException(filename, e);}
        catch ( UnrecognizedEntryException | IOException | InvalidCellRangeCoreException |
                UnknownCoreFunctionException | InvalidFunctionArgumentException e) {
            
                throw new ImportFileException(filename, e);
        }
    }
}
