package org.ing;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

        HashSet<Group> groups = new HashSet<>();
        HashMap<String,Group> map = new HashMap<>();

        try{



            while ((line = bufferedReader.readLine()) != null){
                HashSet<String> string;
                boolean validline = true;
                //read by line
                if(line.length()>0){//if line not empty
                    StringTokenizer tokenizer = new StringTokenizer(line,";",false);
                    if(tokenizer.countTokens()<2){//and contains more than 1 element
                        continue;
                    }
                     string = new HashSet<>();
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
                        string.add(token);
                        ///todo

                    }
                }else {
                    continue;
                }



                if(validline){

                    Group group = new Group();
                    group.addString(string);
                    HashSet<Group> gset = new HashSet<>();
                    for (String s :string){
                        if(map.containsKey(s)){
                            gset.add(map.get(s));
                        }
                    }
                    if(gset.size()>0){
                        for (Group g : gset){
                            group.merge(g);
                        }
                        HashSet<String> tokens = group.getTokens();
                        for(String tok : tokens){
                            map.put(tok, group);
                        }
                        groups.add(group);
                    }else {
                        groups.add(group);
                        for(String s :string){
                            map.put(s,group);
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
        try {
            FileWriter fileWriter = new FileWriter(resultFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 1024*4);

            //write result
            long counter = 1;

            List<Group> list = new ArrayList<>(groups);
            list.sort(new Comparator<Group>() {
                @Override
                public int compare(Group group, Group t1) {
                    return Integer.compare(group.size(), t1.size());
                }
            });
            Collections.reverse(list);

            for (Group g : list){
                if(g.size()>1){
                    bigGroupCounter++;
                }
//                fileWriter.write("Group: "+(counter++)+":\n");
//                fileWriter.write(g.toString());

//                System.out.println("Group: "+(counter++)+":\n");
//                System.out.println(g.toString());

                bufferedWriter.write("Group: "+(counter++)+":\n");
                bufferedWriter.write(g.toString());
            }



        } catch (IOException e) {
            System.out.println("Cant write to file!");
            e.printStackTrace();
            return;
        }
        System.out.println("Groups with more than 2 strings: "+ bigGroupCounter);
        long endTime = System.nanoTime();

        System.out.println( (double)(endTime-startTime)/1_000_000_000 );
    }

}
//83510109514 200000644359490 100000075509630
//200000644359490 83
//100000564414389 200000543806071 83