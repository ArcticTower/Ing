package org.ing;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Ing {


    public static void main(String[] args) {

        long startTime = System.nanoTime();
        String fileName;
//        String reg = "^\"\\d+\"$";
        String reg = "^\"\\d+\\.\\d+\"$";

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
        Pattern pattern = Pattern.compile(reg);

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
//        StringFilter stringFilter = new StringFilter();
        HashMap<String, Node>map = new HashMap<>(100_000);

        Matcher matcher;
        try{
            while ((line = bufferedReader.readLine()) != null){
                if(line.length()<3) continue;
                StringTokenizer tokenizer = new StringTokenizer(line,";");
                int count = tokenizer.countTokens();
                if(count<2) continue;
                HashSet<String> tokens_to_link = new HashSet<>();
                ///todo sort string to node
                HashSet<Node> links = new HashSet<>();
                HashSet<String> sb = new HashSet<>();
                for(int i =0;i<count;i++){
                    String tok = tokenizer.nextToken();
                    matcher = pattern.matcher(tok);
                    if(!matcher.matches()){
                        continue;
                    }
                    if(!tok.matches(reg)){ // better to pre-compile
                        continue;
                    }
                    /**this block for delitiong "" around the number*/
//                    StringBuilder stringBuilder = new StringBuilder(tok);
//                    stringBuilder.deleteCharAt(tok.length()-1);
//                    stringBuilder.deleteCharAt(0);
//                    tok = stringBuilder.toString();

                    sb.add(tok);
                    if(map.containsKey(tok)){
                        links.add(map.get(tok));
                    }else {
                        tokens_to_link.add(tok);
                    }
                }

                Node node = new Node(sb);
                if(links.size()>0){
                    for (Node n : links){
                        n.connect(node);
                    }
                }
                if(tokens_to_link.size()>0){
                    for( String tok : tokens_to_link){
                        map.put(tok,node);
                    }
                }

            }
        } catch (IOException e) {
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


        ArrayList<HashSet<HashSet<String>>> sizedList = new ArrayList<>();
        Node n;

        Iterator<Map.Entry<String,Node>> iter = map.entrySet().iterator();
//        HashSet<String> keys = new HashSet<>();

        while (iter.hasNext()){
            Map.Entry<String,Node> entry = iter.next();
//            if(keys.contains(entry.getKey())) continue;
            n = entry.getValue();
            if(!n.isWrapped()){
                    HashSet<HashSet<String>> group = new HashSet<>();

                    n.getConnectList(group);
                    sizedList.add(group);
            }
        }


        sizedList.sort(comparator);

        int counter = 0;
        int bigCounter=0;
        try {
            FileWriter fileWriter = new FileWriter(resultFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 1024 * 4);

            //write result

            for (HashSet<HashSet<String>> gr : sizedList){
                counter++;
                bufferedWriter.write("Group "+(counter)+":\n");

                if(gr.size()>1) bigCounter++;

                for (HashSet<String> str : gr){

                    for (String word : str){
                        bufferedWriter.write(word+" ");
                    }
                    bufferedWriter.write("\n");
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        double epsTime = (double)(endTime - startTime)/1_000_000_000;
        System.out.println("All groups: "+counter );
        System.out.println("Big groups: "+bigCounter );
        System.out.println("Time: "+epsTime);

    }

    static Comparator<HashSet<HashSet<String>>> comparator= new Comparator<>(){

        @Override
        public int compare(HashSet<HashSet<String>> hashSets, HashSet<HashSet<String>> t1) {
            return Integer.compare(t1.size(), hashSets.size());
        }
    };
}






//83510109514 200000644359490 100000075509630
//200000644359490 83
//100000564414389 200000543806071 83