package com.hbms.app.dao;

import com.hbms.app.model.Payment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private final String file="data/payments.txt";

    public void savePayment(Payment payment){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            String line=payment.getPaymentId()+","
                    +payment.getBookingId()+","
                    +payment.getAmount()+","
                    +payment.getPaymentType()+","
                    +payment.getPaymentCreatedAt();
            bw.write(line);
            bw.newLine();
            System.out.println("Payment saved successfully.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Payment> getAllPayments(){
        List<Payment> payments = new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts=line.split(",");
                Payment payment=new Payment(parts[0], parts[1], Double.parseDouble(parts[2]), Payment.PaymentType.valueOf(parts[3]), LocalDateTime.parse(parts[4]));
                payments.add(payment);
            }
            return payments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Payment findById(String id){
        return getAllPayments().stream().filter(payment -> payment.getPaymentId().equals(id)).findFirst().orElse(null);
    }
}
