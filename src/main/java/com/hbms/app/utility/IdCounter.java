package com.hbms.app.utility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IdCounter {
    private static final String fileName="data/counter.txt";
    private Map<String, Integer> counter=new HashMap<>();

    public IdCounter(){
        loadCounter();
    }

    private void loadCounter(){
        try{
            File file=new File(fileName);

            if (!file.exists()){
                counter.put("USER",100);
                counter.put("BOOKING",100);
                counter.put("ISSUE",100);
                counter.put("PAYMENT",100);
                counter.put("RECEIPT",100);
                saveCounter();
                return;
            }

            BufferedReader br=new BufferedReader(new FileReader(file));
            String line;

            while ((line=br.readLine())!=null){
                String[] parts=line.split("=");
                counter.put(parts[0], Integer.parseInt(parts[1]));
            }
            br.close();
            System.out.println("Counter loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading counter.");
        }
    }

    public void saveCounter(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(fileName));){
            for (String key:counter.keySet()){
                bw.write(key+"="+counter.get(key));
                bw.newLine();
            }
            System.out.println("Counter saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving counter.");
        }
    }

    public String generateId(String type, String prefix){
        int number = counter.get(type);
        String newId=prefix+number;

        counter.put(type,number+1);
        saveCounter();

        return newId;
    }
}
