package org.ing;

import java.util.ArrayList;
import java.util.HashSet;

public class Group {
    private final int id;
    private final HashSet<String> tokens = new HashSet<>(3, 0.6f);
    private final HashSet<HashSet<String>> strings = new HashSet<>();
    private int stringCounter;
    private int elementCounter;

    Group(int id, HashSet<String> list){
        this.id = id;
        tokens.addAll(list);//add tokens to hashset
        strings.add(new HashSet<>(list) );// add list to group
        stringCounter =1;
        elementCounter = list.size();


    }

    // hashmap -> hashset -> hashcode -> ??? -> profit
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return strings != null ? strings.equals(group.strings) : group.strings == null;
    }

    @Override
    public int hashCode() {
        return strings != null ? strings.hashCode() : 0;
    }

    public void addTokens(HashSet<String> list){
        tokens.addAll(list);
        strings.add(list);
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

    public int size(){return stringCounter;}

    //comparator is for sorting
    public int compare(Group g){
        int len = length();
        int glen = g.length();
        if(len == glen) return 0;
        return len>glen? 1:-1;
    }

    public HashSet<HashSet<String>> getStrings(){
        return strings;
    }

}
