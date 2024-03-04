package xxl.contents;

import xxl.contents.functions.Function;
import xxl.contents.literals.IntegerLiteral;
import xxl.contents.literals.StringLiteral;


public interface ContentVisitor {

    public String asString(Function function);
    public String asString(Reference reference);
    public String asString(StringLiteral string);
    public String asString(IntegerLiteral integer);

}
