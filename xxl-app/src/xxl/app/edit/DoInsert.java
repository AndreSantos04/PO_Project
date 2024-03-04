package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
import xxl.exceptions.InvalidCellRangeCoreException;
import xxl.exceptions.UnknownCoreFunctionException;
import xxl.exceptions.InvalidFunctionArgumentException;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Class for inserting data.
 */
class DoInsert extends Command<Spreadsheet> {

    DoInsert(Spreadsheet receiver) {
        super(Label.INSERT, receiver);
        addStringField("gama",Prompt.address());
        addStringField("content", Prompt.content());
    }


    @Override
    protected final void execute() throws CommandException {
        
        String gama = stringField("gama");
        String content = stringField("content");
        
        try{
            _receiver.insertContents(gama,content);
        }
        catch(UnknownCoreFunctionException e){
            String functionName = content.split("(")[0];
            throw new UnknownFunctionException(functionName);
        }
        catch(InvalidCellRangeCoreException e){ 
            throw new InvalidCellRangeException(gama);
        } 
        catch(InvalidFunctionArgumentException | UnrecognizedEntryException e){
            e.printStackTrace();
        }
    }

}
