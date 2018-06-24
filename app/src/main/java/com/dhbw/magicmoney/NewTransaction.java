package com.dhbw.magicmoney;

import android.app.Application;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "db_transaktion")
public class NewTransaction extends Application implements Serializable {

    public String getTransactionID() {
        return transactionID;
    }

    public void setTranactionID(String tranactionID) {
        this.transactionID = tranactionID;
    }

    public String getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(String receiverID) {
        ReceiverID = receiverID;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public String getTransferValue() {
        return TransferValue;
    }

    public void setTransferValue(String transferValue) {
        TransferValue = transferValue;
    }

    @DatabaseField(columnName = "TransaktionsID", generatedId = true)
    private String transactionID;

    @DatabaseField(columnName = "EmpfängerKundenID")
    private String ReceiverID;

    @DatabaseField(columnName = "SenderKundenID")
    private String SenderID;

    @DatabaseField(columnName = "Betrag")
    private String TransferValue;

    @DatabaseField(columnName = "BestätigtSender")
    private Boolean SenderConfirmed = true;

    @DatabaseField(columnName = "BestätigtEmpfänger")
    private Boolean ReceiverConfirmed = true;

    public NewTransaction(String transactionID, String ReceiverID, String SenderID, String TransferValue){
        this.transactionID = transactionID;
        this.ReceiverID = ReceiverID;
        this.SenderID = SenderID;
        this.TransferValue = TransferValue;
    }

}
