package xxl.contents;

import java.util.Comparator;

public class FunctionComparator implements Comparator<String>{

    @Override
    public int compare(String s1, String s2){
        
        String content1 = s1.split("=")[1];
        String content2 = s2.split("=")[1];
        return content1.compareTo(content2);
    }
}
