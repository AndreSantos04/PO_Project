package xxl.cells;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xxl.Observer;
import xxl.Subject;
import xxl.contents.Content;
import xxl.contents.literals.*;

public class Cell implements Serializable, Subject{
    
    private Content _content;
    private String _key;
    private List<Observer> _observers = new ArrayList<Observer>();

    public Cell(Content content, String key){
        _content = content;
        _key = key;
    }
    
    public Literal getValue(){
        if(isChanged()){
            updateObserverValue();
        }
        return _content.getLiteral();
    }
    
    public Content getContent(){
        return _content;
    }

    public String getKey(){
        return _key;
    }

    public Content copyContent(){
        return _content.copyContent();
    }

    public boolean isChanged(){
        return _content.getChanged();
    }

    public void setChanged(boolean b){
        _content.setChanged(b);
    }

    public void setContent(Content content){
        _content.removeFromSubjects();
        _content = content;
        updateObserverValue();
    }
    public void setInCutBuffer(boolean b){
        _content.setInCutBuffer(b);
    }

    public boolean getInCutBuffer(){
        return _content.isInCutBuffer();
    }

    public void addObserver(Observer o){
        _observers.add(o);
    }

    public void removeObserver(Observer o){
        int iX = _observers.indexOf(o);
        if (iX>=0)
            _observers.remove(iX);
    }
    public void updateObserverValue(){
        setChanged(false);
        for(Observer o : _observers){
            o.updateValue();
        }
    }

    @Override
    public String toString(){
        return _key + "|" +_content.acceptAsString(_content.getAsStringVisitor());
    }

}
