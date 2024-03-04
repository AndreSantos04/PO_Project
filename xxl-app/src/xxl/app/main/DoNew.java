package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Calculator;


/**
 * Open a new file.
 */
class DoNew extends Command<Calculator> {

    DoNew(Calculator receiver) {
        super(Label.NEW, receiver);
        
        
    }
    

    @Override
    protected final void execute() throws CommandException {

        
        if(_receiver.isSpreadsheetOpen() && _receiver.isSpreadsheetChanged()
         && Form.confirm(Prompt.saveBeforeExit())){
        
            DoSave save = new DoSave(_receiver);
            save.execute(); 

        }
        int lines = Form.requestInteger(Prompt.lines());
        int columns = Form.requestInteger(Prompt.columns());

        _receiver.createSpreadsheet(lines, columns);
        
    }

}
