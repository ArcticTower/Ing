package org.ing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Node {
    private HashSet<Node> connected = new HashSet<>();
    private HashSet<String> string;
    boolean wrapped = false;

    public Node(HashSet<String> s){
        string = s;
    }

    public void getConnectList(HashSet<HashSet<String>> set ){
        set.add(string);
//        keys.addAll(string);
        wrap();
        for (Node n : connected){
            if(!n.isWrapped()){

                n.getConnectList(set);
            }

        }
    }

    public void connect(Node other){
        connected.add(other);
        other.safeConnect(this);
    }

    public void safeConnect(Node other){
        connected.add(other);
    }

    public boolean isWrapped(){
        return wrapped;
    }
    public void wrap(){
        wrapped = true;
    }

}
