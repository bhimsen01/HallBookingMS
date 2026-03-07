package com.hbms.app.dao;

import com.hbms.app.model.Receipt;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAO {
    private final String file="data/receipts.txt";

    private String convertToLine(Receipt receipt){
        return receipt.getReceptID()+","
                +receipt.getBookingId()+","
                +receipt.getReceiptCreatedAt();
    }

    public void saveReceipt(Receipt receipt){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            bw.write(convertToLine(receipt));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save receipt. ",e);
        }
    }

    public List<Receipt> getAllReceipts(){
        List<Receipt> receipts=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line= br.readLine())!=null){
                if (line.isEmpty()) {
                    System.out.println("Empty line | DAO | getAllReceipts");
                    continue;}
                String[] parts=line.split(",");
                Receipt receipt=new Receipt(parts[0], parts[1], LocalDateTime.parse(parts[2]));
                receipts.add(receipt);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all receipts. ",e);
        }
        return receipts;
    }

    public Receipt findById(String id){
        return getAllReceipts().stream().filter(receipt -> receipt.getReceptID().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
