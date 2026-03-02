package com.hbms.app.service;

import java.io.*;

public class SymphonyAccountService {
    private final String file="data/SymphonyBankAccount.txt";

    private final String SymphonyAccNme="Hall Symphony Main Account";

    private double balance;

    public SymphonyAccountService(){
        balance=readBalance();
    }

    public String getSymphonyAccNme(){
        return SymphonyAccNme;
    }

    public double getBalance() {
        return balance;
    }

    public double readBalance(){
        double initialBalance=0;
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            initialBalance=Double.parseDouble(br.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return initialBalance;
    }

    public void addBookingAmount(double amount){
        balance+=amount;
        writeBalanceToFile(balance);
    }

    public void refundAmount(double amount){
        double refund=amount*0.9;
        balance-=refund;
        writeBalanceToFile(balance);
    }

    private void writeBalanceToFile(double balance) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.valueOf(balance));
        } catch (IOException e) {
            throw new RuntimeException("Failed to update Symphony account balance", e);
        }
    }

//    static void main(String[] args) {
//        SymphonyAccountService symphonyAccountService=new SymphonyAccountService();
//        System.out.println(symphonyAccountService.readBalance());
//        symphonyAccountService.addBookingAmount(12.2);
//        System.out.println(symphonyAccountService.readBalance());
//        symphonyAccountService.refundAmount(12.2);
//        System.out.println(symphonyAccountService.readBalance());
//    }
}
