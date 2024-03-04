package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;

import xxl.exceptions.InvalidCellRangeCoreException;

/**
 * Delete command.
 */
class DoDelete extends Command<Spreadsheet> {

    DoDelete(Spreadsheet receiver) {
        super(Label.DELETE, receiver);
        addStringField("gama",Prompt.address());
    }

    @Override
    protected final void execute() throws CommandException {
        String gama = stringField("gama");
        try{
            _receiver.delete(gama);
        }
        catch(InvalidCellRangeCoreException e){
            throw new InvalidCellRangeException(gama);
        }
    }
}
