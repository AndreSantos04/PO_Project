package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
import xxl.exceptions.InvalidCellRangeCoreException;


/**
 * Class for searching functions.
 */
class DoShow extends Command<Spreadsheet> {

    DoShow(Spreadsheet receiver) {
        super(Label.SHOW, receiver);
        addStringField("gama", Prompt.address());
    }

    @Override
    protected final void execute() throws CommandException {
        String gama = stringField("gama");

        try{
            _display.popup(_receiver.show(gama));

        }
        catch(InvalidCellRangeCoreException e){
            throw new InvalidCellRangeException(gama);
        }


    }

}
