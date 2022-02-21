package org.ing;
import java.util.*;

public class StringHandler {
    //111;123;222
    //
    //200;123;100
    //
    //300;;100 is one group 123:123 100:100
    private String target_string;
    private int stringCounter;
    private int validStringCounter;
    String rx;
    private int badStringCounter;
    private HashMap<String,Integer> stringIdAndTokens;
    private final GroupHandler groupHandler;
//    private LinkedList<String> badStrings;

    StringHandler(GroupHandler groupHandler, String regx ){
        rx = regx;
        this.groupHandler = groupHandler;
        stringCounter = 0;
        validStringCounter=0;
        badStringCounter =0;
        stringIdAndTokens = new HashMap<>(100000,0.75f);
//        badStrings = new LinkedList<>();
    }

    StringHandler(GroupHandler groupHandler ){
        this.groupHandler = groupHandler;
        rx = "^\"\\d+\"$";
        stringCounter = 0;
        validStringCounter=0;
        badStringCounter =0;
        stringIdAndTokens = new HashMap<>(100000,0.75f);
//        badStrings = new LinkedList<>();
    }

    private void tokenizeString(){

        LinkedList<Integer> groupIdsToAdd = new LinkedList<>();
        HashSet<String> list = new HashSet<>();
        boolean flag = false;
        HashSet<String> noGroupTokens = new HashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(target_string,";",false);


        int amount = tokenizer.countTokens();
        //string with 1 element should be skipped by task
        if(amount<2){
            badStringCounter++;
//            badStrings.add(stringCounter+" : "+target_string);
            return;
        }

        StringBuilder element ;

        for(int i =0;i<amount;i++){// max 3 iter.
            String str = tokenizer.nextToken();
            if(str.length()<3){
                continue;
            }
            //only valid element is "[0-9]+"
            if(!str.matches(rx)){//^"\d+\.\d"$//^"\d+"$
                badStringCounter++;
//                badStrings.add(stringCounter+" : "+target_string);
                return;
            }
            element = new StringBuilder(str);
            element.deleteCharAt(str.length() - 1);
            element.deleteCharAt(0);
            String s = element.toString();

            list.add(s);
            if(stringIdAndTokens.containsKey(s)){
                groupIdsToAdd.add(stringIdAndTokens.get(s));
                flag = true;
            } else {
                noGroupTokens.add(s);
            }
        }

        if(flag){
            for(Integer id: groupIdsToAdd){// max 3 iter
                groupHandler.addToGroup(id,list);
            }
            for(String s : noGroupTokens){

            }
        }

        for(Integer id: groupIdsToAdd){// max 3 iter
            groupHandler.addToGroup(id,list);
        }
        if(noGroupTokens.size()>0){
            int id = groupHandler.createGroup(list);
            for (String s : noGroupTokens){// max 3 iter
                stringIdAndTokens.put(s,id);
            }
        }
        validStringCounter++;
    }

    public void addString(String str){
        target_string = str;
        stringCounter++;
        tokenizeString();
    }

    public int getStringCounter(){
        return stringCounter;
    }
    public int getValidStringCounter(){
        return validStringCounter;
    }
    public int getBadStringCounter(){
        return badStringCounter;
    }
//    public LinkedList<String> getBadStrings(){
//        return badStrings;
//    }
}
