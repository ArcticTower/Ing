package org.ing;

import java.util.*;

public class GroupHandler {
    private int currentGroupId;
    private int groupCounter;
    private int bigGroupCounter;
    private ArrayList<Integer> bigGroupsId;
//    private HashMap<String,Integer> groupIdAndTokens;
    private ArrayList<Group> groupList;// todo hashset
    private Map<Integer, ArrayList<Integer>> groupIdList;

    ///todo если есть 2 угрппы, а затем появляется 3я что их объединяет, то в результате останется либо 1 группа,так как 2 стали одинаковыми, либо 2,
    GroupHandler(){
        currentGroupId = 1;
        groupCounter = 0;
        bigGroupCounter =0;
        groupList = new ArrayList<>();
//        groupIdAndTokens = new HashMap<>(200,0.75f);
        bigGroupsId = new ArrayList<>();
        groupIdList = new HashMap<>();
    }

    public int createGroup(HashSet<String> tokens){
        int id = currentGroupId;
        Group g = new Group(id,tokens);
        groupList.add(g);
        currentGroupId++;
        groupCounter++;
        return id;
    }

    public void addToGroup(int id, HashSet<String> list){
        Group g = groupList.get(id-1);
        g.addTokens(list);
        Integer size = g.size();
        if(groupIdList.containsKey(size)) {
            (groupIdList.get(size)).add(id);
        }else {
            ArrayList<Integer> lst = new ArrayList<>(id);
            groupIdList.put(size,lst);
        }
        if(!bigGroupsId.contains(id)){
            bigGroupsId.add(id);
            bigGroupCounter++;
        }
        //todo: add elements and sort simultaneously
    }

    public int getBigGroupCounter(){
        return bigGroupCounter;
    }


    public ArrayList<Integer> getAllSizes(){
        var arr =new ArrayList<Integer>(groupIdList.keySet());
        Collections.sort(arr);
        Collections.reverse(arr);
        return arr;
    }


    public ArrayList<Group> getGroupList(int size){
        ArrayList<Integer> ids = new ArrayList<Integer>(groupIdList.keySet());
        ArrayList<Group> res = new ArrayList<>();
        for (int id: ids){
            res.add(groupList.get(id-1));
        }
        return res;
    }

    public int getGroupCounter(){
        return groupCounter;
    }

    public Group getGroupById(int id){
        return groupList.get(id-1);
    }

}
