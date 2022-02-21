package org.ing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Group {

    private HashSet<String> tokens;
    private ArrayList<String> strings;

    public ArrayList<String> getStrings() {
        return strings;
    }

    public int size(){
        if(strings == null) return 0;
        return strings.size();
    }



    public HashSet<String> getTokens() {
        return tokens;
    }


    private void putTokens(HashSet<String> set){
        tokens.addAll(set);
    }

    private void putStrings(ArrayList<String> strs){
        strings.addAll(strs);
    }



    Group(){
        strings = new ArrayList<>();
        tokens = new HashSet<>();
    }

    public void delete(){
        strings = null;
        tokens = null;
    }

    public void merge(Group g){
        putTokens(g.getTokens());
        putStrings(g.getStrings());
        g.delete();

    }

    @Override
    public String toString(){
        if (strings == null){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (String string : strings){
            stringBuilder.append(string);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public void addString(HashSet<String> str){
        tokens.addAll(str);

        StringBuilder st = new StringBuilder("");
        for (String s : str){
            st.append(s);
            st.append(" ");
        }
        strings.add(st.toString());
    }

    public boolean containsAll(HashSet<String> set){
        return tokens.containsAll(set);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return tokens.equals(group.tokens);
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }
}
