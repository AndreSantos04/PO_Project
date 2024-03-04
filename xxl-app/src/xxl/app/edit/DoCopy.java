package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;

import xxl.exceptions.InvalidCellRangeCoreException;


/**
 * Copy command.
 */
class DoCopy extends Command<Spreadsheet> {

    DoCopy(Spreadsheet receiver) {
        super(Label.COPY, receiver);
        addStringField("gama",Prompt.address());
    }

    @Override
    protected final void execute() throws CommandException {
        String gama = stringField("gama");
        try{
            _receiver.copy(gama);
        }
        catch(InvalidCellRangeCoreException e){
            throw new InvalidCellRangeException(gama);
        }
    }

}
