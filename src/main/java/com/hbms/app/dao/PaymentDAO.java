package com.hbms.app.dao;

import com.hbms.app.model.Payment;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private final String file="data/payments.txt";

    private String convertToLine(Payment payment){
        return payment.getPaymentId()+","
                +payment.getBookingId()+","
                +payment.getAmount()+","
                +payment.getPaymentType()+","
                +payment.getPaymentCreatedAt();
    }

    public void savePayment(Payment payment){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            bw.write(convertToLine(payment));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save payment. ",e);
        }
    }

    public List<Payment> getAllPayments(){
        List<Payment> payments = new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null){
                if (line.isEmpty()) {
                    System.out.println("Empty line | DAO |  getAllPayments");
                    continue;}
                String[] parts=line.split(",");
                Payment payment=new Payment(parts[0], parts[1], Double.parseDouble(parts[2]), Payment.PaymentType.valueOf(parts[3]), LocalDateTime.parse(parts[4]));
                payments.add(payment);
            }
            return payments;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all payments. ",e);
        }
    }

    public Payment findById(String id){
        return getAllPayments().stream().filter(payment -> payment.getPaymentId().equals(id)).findFirst().orElse(null);
    }
}
