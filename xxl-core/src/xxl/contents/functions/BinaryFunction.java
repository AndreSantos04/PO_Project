package xxl.contents.functions;

import xxl.contents.Content;
import xxl.contents.literals.Literal;

public abstract class BinaryFunction extends Function  {

    private Content _content1;
    private Content _content2;

    public BinaryFunction(Content content1, Content content2, String functionName){
        super(functionName);
        _content1 = content1;
        _content2 = content2;
        evaluate();
    }

    @Override
    public void evaluate(){
        if (_content1.getChanged() || _content2.getChanged())
            updateValue(_content1.getLiteral(),_content2.getLiteral());
    }

    public Content getContent1() {return _content1;}

    public Content getContent2() {return _content2;}

    public abstract void updateValue(Literal literal1, Literal literal2);
}
