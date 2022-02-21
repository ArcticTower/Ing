package org.ing;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class Ing {


    public static void main(String[] args) {

        long startTime = System.nanoTime();
        String fileName;
        String reg = "^\"\\d+\"$";

        if (args.length<1){
            System.out.println("Usage:");
            System.out.println("java -jar Ing.jar file reg_exp");
            return;
        }

            fileName = args[0];

        if (args.length>=2){
            reg = args[1];
            try{
                Pattern.compile(reg);
            } catch (PatternSyntaxException exception){
                System.out.println("reg_exp not valid!");
                return;
            }
        }
        File file;
        if(fileName == null){
            System.out.println("File not valid");
            return;
        }else{
            file = new File(fileName);
        }

        BufferedReader bufferedReader;
        try{
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader, 10*1024*1024*4);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
            return;
        }
        String line;
        String token;

//        HashSet<Group> groups = new HashSet<>();
//        ArrayList<HashSet<String>> groups = new ArrayList<>();
//        HashMap<String,Group> map = new HashMap<>();
//        ArrayList<ArrayList<HashSet>> groups = new ArrayList<>();
//        HashMap<String,ArrayList<HashSet<String>>> map = new HashMap<>();

        ArrayList<String> strings_list = new ArrayList<>();
        HashMap <String,HashSet<String>> map = new HashMap<>();
//        SortedMap<String,HashSet<String>> map = new




        try{



            while ((line = bufferedReader.readLine()) != null){
                StringBuilder stringB;
                HashSet <String> tokens;
                boolean validline = true;
                //read by line
                if(line.length()>0){//if line not empty
                    StringTokenizer tokenizer = new StringTokenizer(line,";",false);
                    if(tokenizer.countTokens()<2){//and contains more than 1 element
                        continue;
                    }
                     stringB = new StringBuilder();
                     tokens = new HashSet<>();
                    while (tokenizer.hasMoreTokens()){
                        token = tokenizer.nextToken();
                        if(token.length()<3){
                            validline = false;
                            continue;
                        }
                        if(!token.matches(reg)){
                            validline=false;
                            continue;
                        }
                        if(!validline) continue;
                        StringBuilder builder = new StringBuilder(token);
                        builder.deleteCharAt(token.length()-1);
                        builder.deleteCharAt(0);
                        token = builder.toString();
                        stringB.append(token);
                        stringB.append(" ");
                        tokens.add(token);
                        ///todo

                    }
                }else {
                    continue;
                }



                if(validline){

                    String str = stringB.toString();
                    strings_list.add(str);

                    for (String t : tokens){
                        if(map.containsKey(t)){
                            HashSet<String> tmp = map.get(t);
                            tmp.add(str);
                        }else {
                            HashSet<String> set = new HashSet<>();
                            set.add(str);
                            map.put(t, set);
                        }
                    }

                }else {
//                    continue;
                }



            }
        } catch (IOException e) {
            System.out.println("File IO broke!");
            e.printStackTrace();
            return;
        }
        String resultFileName = fileName + "_result.txt";
        File resultFile = new File(resultFileName);
        if(resultFile.exists()){
            boolean a = resultFile.delete();
            if (!a){
                System.out.println("Cant delete existing file!");
                return;
            }
        }
        try {
            boolean a = resultFile.createNewFile();
            if(!a){
                System.out.println("Can not create file for output!");
                return;
            }
        } catch (IOException e) {
            System.out.println("Cant create output file!");
            e.printStackTrace();
            return;
        }
        long bigGroupCounter =0;

        //form groups
//        HashSet<String>used = new HashSet<>();
        HashMap<String,Integer>link = new HashMap<>();
//        LinkedList<ArrayList<String>> groups = new LinkedList<>();
//        Map<String,ArrayList<String>>dic = new HashMap<>();
        ArrayList<ArrayList<String>>dic = new ArrayList<>();

        for (Map.Entry<String, HashSet<String>> entry : map.entrySet()) {
            HashSet<String> values = entry.getValue();
            HashSet<Integer>back_link = new HashSet<>();


            for (String v : values){
                if(link.containsKey(v)) {
                    int lnk = link.get(v);
                    if(lnk == 1336){
                        System.out.println(lnk);
                        System.out.println(dic.get(1336));
                        System.out.println(values);
                    }
                    back_link.add(lnk);
                }
            }

            if (back_link.size()>=2){
//                System.out.println("WHY");
            }
            if (back_link.size()>0){//add to group
                ////todo

                Integer l = new ArrayList<Integer>(back_link).get(0);
                for (String v : values){
                    link.put(v ,l);
                }
                ArrayList<String> ob = dic.get(l);//todo array to hashset
                ob.addAll(values);
            }
            if(back_link.size()<1){//create group
                ArrayList<String> newDicEl = new ArrayList<>(values);
                int l;
                l = dic.size();
                dic.add(newDicEl);

//                String l = newDicEl.get(0);
//                dic.add(l);
//                dic.put(l,newDicEl);
                for(String v :values){
                    link.put(v,l);
                }
            }

        }

        Collections.sort(dic, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> strings, ArrayList<String> t1) {
                if(strings.size()==t1.size()) return 0;
                if(strings.size()>t1.size()) return -1;
                return 1;
            }
        });

        try {
            FileWriter fileWriter = new FileWriter(resultFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 1024*4);

            //write result
            long counter = 1;
            for (ArrayList<String> list : dic){
                bufferedWriter.write("Group "+ (counter++)+":\n");
                for (String s : list){
                    bufferedWriter.write(s+"\n");
                }
            }


//            Map<String, HashSet<String>> sorted_map = sortByValue(map);

//            for (Map.Entry<String, HashSet<String>> entry : sorted_map.entrySet()) {
//
//                //print group
//                bufferedWriter.write("Group "+counter+":\n");
//                for ()
//
//            }

//            LinkedHashMap<String,HashSet<String>> we;
//            List<Group> list = new ArrayList<>(groups);
//            list.sort(new Comparator<Group>() {
//                @Override
//                public int compare(Group group, Group t1) {
//                    return Integer.compare(group.size(), t1.size());
//                }
//            });
//            Collections.reverse(list);
//
//            for (Group g : list){
//                if(g.size()>1){
//                    bigGroupCounter++;
//                }
////                fileWriter.write("Group: "+(counter++)+":\n");
////                fileWriter.write(g.toString());
//
////                System.out.println("Group: "+(counter++)+":\n");
////                System.out.println(g.toString());
//
//                bufferedWriter.write("Group: "+(counter++)+":\n");
//                bufferedWriter.write(g.toString());
//            }



        } catch (IOException e) {
            System.out.println("Cant write to file!");
            e.printStackTrace();
            return;
        }
        System.out.println("Groups with more than 2 strings: "+ bigGroupCounter);
        long endTime = System.nanoTime();

        System.out.println( (double)(endTime-startTime)/1_000_000_000 );
    }


//    private static Map<String, HashSet<String>> sortByValue(Map<String, HashSet<String>> unsortMap)
//    {
//        List<Map.Entry<String, HashSet<String>>> list = new LinkedList<>(unsortMap.entrySet());
//
//
//        list.sort((stringHashSetEntry, t1) -> {
//            if (stringHashSetEntry.getValue().size() == t1.getValue().size()) return 0;
//            if (stringHashSetEntry.getValue().size()>t1.getValue().size()) return -1;
//            return 1;
//        });
//        // Sorting the list based on values
////        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
////                ? o1.getKey().compareTo(o2.getKey())
////                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
////                ? o2.getKey().compareTo(o1.getKey())
////                : o2.getValue().compareTo(o1.getValue()));
//        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
//
//    }
//
//    private static void printMap(Map<String, Integer> map)
//    {
//        map.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
//    }
}






//83510109514 200000644359490 100000075509630
//200000644359490 83
//100000564414389 200000543806071 83