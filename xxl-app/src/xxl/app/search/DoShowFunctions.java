package xxl.app.search;

import pt.tecnico.uilib.menus.Command;
import xxl.Spreadsheet;


/**
 * Command for searching function names.
 */
class DoShowFunctions extends Command<Spreadsheet> {

    DoShowFunctions(Spreadsheet receiver) {
        super(Label.SEARCH_FUNCTIONS, receiver);
        addStringField("value",Prompt.searchFunction());
    
    }

    @Override
    protected final void execute() {
        String value = stringField("value");
        _display.popup(_receiver.searchFunction(value));
    }

}
