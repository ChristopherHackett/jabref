package net.sf.jabref.logic.layout.format;

import net.sf.jabref.logic.layout.LayoutFormatter;
import net.sf.jabref.model.util.RemoveLatexCommands;


public class RemoveLatexCommandsFormatter implements LayoutFormatter {

    private final RemoveLatexCommands removeFormatter = new RemoveLatexCommands();

    @Override
    public String format(String fieldText) {
        return removeFormatter.format(fieldText);
    }

}
