package org.ing;

import java.util.*;

public class GroupHandler {
    private int currentGroupId;
    private int groupCounter;
    private int bigGroupCounter;
    private ArrayList<Integer> bigGroupsId;
//    private HashMap<String,Integer> groupIdAndTokens;
    private ArrayList<Group> groupList;// = new ArrayList<Group>();


    GroupHandler(){
        currentGroupId = 1;
        groupCounter = 0;
        bigGroupCounter =0;
        groupList = new ArrayList<Group>();
//        groupIdAndTokens = new HashMap<>(200,0.75f);
        bigGroupsId = new ArrayList<>();
    }

    public int createGroup(ArrayList<String> tokens){
        int id = currentGroupId;
        Group g = new Group(id,tokens);
        groupList.add(g);
        currentGroupId++;
        groupCounter++;
        return id;
    }

    public void addToGroup(int id, ArrayList<String> list){
        Group g = groupList.get(id-1);
        g.addTokens(list);
        if(!bigGroupsId.contains(id)){
            bigGroupsId.add(id);
            bigGroupCounter++;
        }
        //todo: add elements and sort simultaneously
    }

    public int getBigGroupCounter(){
        return bigGroupCounter;
    }

    public void sort(){
        Comparator<Group> C = Group::compare;

        groupList.sort(C.reversed());


    }


    public ArrayList<Group> getGroupList(int amount){
        if(amount >0){
            ArrayList<Group> groups = new ArrayList<>();
            for (int i =0; i<amount;i++){
                groups.add(groupList.get(i));
            }
            return groups;
        }else {
            return groupList;
        }
    }

    public int getGroupCounter(){
        return groupCounter;
    }

    public Group getGroupById(int id){
        return groupList.get(id-1);
    }

}
