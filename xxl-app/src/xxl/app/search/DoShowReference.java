package xxl.app.search;

import pt.tecnico.uilib.menus.Command;
import xxl.Spreadsheet;


/**
 * Command for searching content values.
 */
class DoShowReference extends Command<Spreadsheet> {

    DoShowReference(Spreadsheet receiver) {
        super(Label.SEARCH_VALUES, receiver);
    }

    @Override
    protected final void execute(){
        _display.popup(_receiver.searchValue("REFERENCE"));
    }

}
