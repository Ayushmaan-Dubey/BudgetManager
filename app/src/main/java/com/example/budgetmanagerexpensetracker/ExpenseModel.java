package com.example.budgetmanagerexpensetracker;

import java.io.Serializable;

public class ExpenseModel implements Serializable {
    private String expenseId;
    private String note;
    private String category;
    private String day;
    private long amount;
    private long time;
    private String type;
    private String uid;

   public ExpenseModel(){
   }

    public ExpenseModel(String expenseId, String note, String category, String day, long amount, long time, String type, String uid) {
        this.expenseId = expenseId;
        this.note = note;
        this.category = category;
        this.day = day;
        this.amount = amount;
        this.time = time;
        this.type = type;
        this.uid = uid;
    }


    public String getType(){
       return type;
   }

   public void setType(String type){
       this.type = type;
   }

    public String getExpenseId(){
       return expenseId;
    }

    public void setExpenseId(String expenseId){
       this.expenseId = expenseId;
    }

    public String getCategory() {
       return category;
    }

    public void setCategory(String category){
       this.category = category;
    }

    public String getDay(){ return day;}

    public void setDay(String day) { this.day = day;}

    public long getAmount(){
       return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }

    public long getTime(){
       return time;
    }

    public void setTime(long time){
       this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNote() {
       return note;
    }

    public void setNote(){
       this.note = note;
    }


}
