package org.ing;

import java.util.ArrayList;
import java.util.HashSet;

public class Group {
    private final int id;
    private HashSet<String> tokens = new HashSet<>(3, 0.6f);
    private HashSet<HashSet<String>> strings = new HashSet<>();
    private int stringCounter;
    private int elementCounter;

    Group(int id, ArrayList<String> list){
        this.id = id;
        tokens.addAll(list);//add tokens to hashset
        strings.add(new HashSet<String>(list) );// add list to group
        stringCounter =1;
        elementCounter = list.size();


    }

    public void addTokens(ArrayList<String> list){
        tokens.addAll(list);
        strings.add(new HashSet<String>(list));
        stringCounter++;
        elementCounter+=list.size();
    }

    public ArrayList<String> getTokens(){
        return new ArrayList<>(tokens);
    }

    public int getId(){
        return id;
    }

    public int length(){
        return tokens.size();
    }

    //comparator is for sorting
    public int compare(Group g){
        int len = length();
        int glen = g.length();
        if(len == glen) return 0;
        return len>glen? 1:-1;
    }

    public HashSet<HashSet<String>> getStrings(){
        return new HashSet<HashSet<String>>(strings);
    }

}
