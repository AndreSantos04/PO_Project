package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;

import xxl.exceptions.InvalidCellRangeCoreException;

/**
 * Paste command.
 */
class DoPaste extends Command<Spreadsheet> {

    DoPaste(Spreadsheet receiver) {
        super(Label.PASTE, receiver);
        addStringField("gama",Prompt.address());
    }

    @Override
    protected final void execute() throws CommandException {
        String gama = stringField("gama");
        try{
            _receiver.paste(gama);
        }
        catch(InvalidCellRangeCoreException e){
            throw new InvalidCellRangeException(gama);
        }
    }
}
