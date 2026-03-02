package com.hbms.app.dao;

import com.hbms.app.model.Receipt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAO {
    private final String file="data/receipts.txt";

    public void saveReceipt(Receipt receipt){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            String line=receipt.getReceptID()+","
                    +receipt.getBookingId()+","
                    +receipt.getReceiptCreatedAt();
            bw.write(line);
            bw.newLine();
            System.out.println("Receipt saved successfully.");
        } catch (Exception e) {
            System.out.println("Failed to save receipt. "+e.getMessage());
        }
    }

    public List<Receipt> getAllReceipts(){
        List<Receipt> receipts=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line= br.readLine())!=null){
                String[] parts=line.split(",");
                Receipt receipt=new Receipt(parts[0], parts[1], LocalDateTime.parse(parts[2]));
                receipts.add(receipt);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return receipts;
    }

    public Receipt findById(String id){
        return getAllReceipts().stream().filter(receipt -> receipt.getReceptID().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
