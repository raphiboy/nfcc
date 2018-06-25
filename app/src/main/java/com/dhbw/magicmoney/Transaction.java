package com.dhbw.magicmoney;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

@DatabaseTable(tableName = "db_transaktion")
public class Transaction {

    @DatabaseField(columnName = "TransaktionsID", generatedId = true)
    private int ID;
    @DatabaseField(columnName = "SenderKundenID", canBeNull = false)
    private int senderID;
    @DatabaseField(columnName = "EmpfängerKundenID", canBeNull = false)
    private int receiverID;
    @DatabaseField(columnName = "Betrag", canBeNull = false)
    private double transferValue;

    public Transaction(int senderID, int receiverID, double transferValue) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.transferValue = transferValue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(double transferValue) {
        this.transferValue = transferValue;
    }


    public String getEURBalance(){
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format((this.getTransferValue()));
    }

}
