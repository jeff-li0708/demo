package com.yeepay.sqkkseperator.bean;

/**
 * Created by liangl on 2019/4/17.
 */
public class PayToUserReq {
    private String batch_No;
    private String order_Id;
    private String bank_Code;
    private String bank_Name;
    private String amount;
    private String account_Name;
    private String account_Number;

    public String getBatch_No() {
        return batch_No;
    }

    public void setBatch_No(String batch_No) {
        this.batch_No = batch_No;
    }

    public String getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(String order_Id) {
        this.order_Id = order_Id;
    }

    public String getBank_Code() {
        return bank_Code;
    }

    public void setBank_Code(String bank_Code) {
        this.bank_Code = bank_Code;
    }

    public String getBank_Name() {
        return bank_Name;
    }

    public void setBank_Name(String bank_Name) {
        this.bank_Name = bank_Name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccount_Name() {
        return account_Name;
    }

    public void setAccount_Name(String account_Name) {
        this.account_Name = account_Name;
    }

    public String getAccount_Number() {
        return account_Number;
    }

    public void setAccount_Number(String account_Number) {
        this.account_Number = account_Number;
    }
}
