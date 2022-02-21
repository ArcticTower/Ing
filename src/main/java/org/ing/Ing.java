package org.ing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class Ing {
    static GroupHandler groupHandler;
    static StringHandler stringHandler;

    public static void main(String[] args) {
        long startAllTime = System.nanoTime();
        groupHandler = new GroupHandler();

        //check if input correct
        if(args.length<1){
            System.out.println("Укажите файл аргументом программы, можно указать выражение для его валидации");
            System.out.println("Пример: java -jar Ing.jar lng.csv ^\\d+$");
            System.exit(1);
        }
        String regx = null;
        if (args.length<2){
            regx = "^\"\\d+\\.\\d\"$";
        }
        if(args.length == 2){
            regx = args[1];
            try{
                Pattern.compile(regx);
            } catch (PatternSyntaxException exception){
                System.out.println("Выражения для проверки не валидно!");
                return;
            }
        }
        if(regx!=null)
            stringHandler = new StringHandler(groupHandler, regx);
        else
            stringHandler = new StringHandler(groupHandler);
        String fileName = args[0];

        System.out.println("Ing v1.0");
        System.out.println("Обработка файла: "+fileName);

        //open file for input
        File file = new File(fileName);
        FileReader fileReader;
        try{
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            return;
        }
        if (!file.canRead()){
            System.out.println("Невозможно прочитать файл!");
            return;
        }

        //read file
        //todo: put into parallel thread stream
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        long startTime = System.nanoTime();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //todo: put calc into parallel thread stream
                stringHandler.addString(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода!");
            e.printStackTrace();
            return;
        }
        try{
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Ошибка вывода!");
            e.printStackTrace();
        }
        try{
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Ошибка вывода!");
            e.printStackTrace();
        }

        //print output
        long endTime = System.nanoTime();
        long elapsedTime = endTime-startTime;
        double elapsedTimeSeconds =(double) elapsedTime/1_000_000_000;
        System.out.println("Файл обработан за "+elapsedTimeSeconds+" секунд");
        System.out.println("Число групп с более чем 1 элементом: "+groupHandler.getBigGroupCounter());
        System.out.println("10 наибольших групп: ");
        groupHandler.sort();
        ArrayList<Group> list = groupHandler.getGroupList(10);
        for (Group g : list){
            System.out.println("Группа: "+g.getId());
            HashSet<HashSet<String>> strings = g.getStrings();
            for (HashSet<String> str: strings){
                for (String s: str) {
                    System.out.print(s + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
        System.out.println("Групп всего: \t\t"+ groupHandler.getGroupCounter());
        System.out.println("Строк всего: \t\t"+ stringHandler.getStringCounter());
        //todo: put errors into parallel thread stream
        System.out.println("Строк забраковано: \t"+stringHandler.getBadStringCounter());
//        System.out.println("Забракованные строки: ");
//        LinkedList<String> badstr = stringHandler.getBadStrings();
//        for (String s : badstr){
//            System.out.println(s);
//        }
        System.out.println("Строк одобрено: \t"+stringHandler.getValidStringCounter());

        //print result to file
        //todo: put into parallel thread stream
        String resFileName = "result.txt";
        File resultFile = new File(resFileName);
        try{
            boolean r = resultFile.createNewFile();
            if(!r){
                 r =resultFile.delete();
                 if(!r){
                     System.out.println("Невозможно перезаписать файл вывода!");
                     return;
                 }
                 r = resultFile.createNewFile();
                 if(!r){
                     System.out.println("Невозможно перезаписать файл вывода!");
                     return;
                 }
            }
            FileWriter wf = new FileWriter(resultFile);
            BufferedWriter bf = new BufferedWriter(wf);
            bf.write(groupHandler.getBigGroupCounter()+"\n");
            ArrayList<Group> groups = groupHandler.getGroupList(0);
            for(Group g : groups){
                bf.write("Group "+g.getId()+":\n");
                HashSet<HashSet<String>> strings = g.getStrings();
                for (HashSet<String> str: strings){
                    for (String s: str) {
                        bf.write(s+" ");
                    }
                    bf.write("\n");
                }

            }
            bf.close();
            wf.close();
            System.out.println("!!!\nРезультат сохранён в "+resFileName+"\n!!!");

        } catch (IOException e) {
            System.out.println("Невозможно создать файл для вывода результата!\nОшибка вывода!");
            e.printStackTrace();
        }


        long endAllTime = System.nanoTime();
        long elapsedAllTime = endAllTime - startAllTime;
        System.out.println("Время работы всей программы: "+ (double)elapsedAllTime/1_000_000_000);

    }

}
