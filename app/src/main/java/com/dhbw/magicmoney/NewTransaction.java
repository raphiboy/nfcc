package com.dhbw.magicmoney;

import android.app.Application;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "db_transaktion")
public class NewTransaction extends Application implements Serializable {

    @DatabaseField(columnName = "TransaktionsID", generatedId = false)
    private String ID;

    @DatabaseField(columnName = "EmpfängerKundenID", generatedId = false)
    private String KundenID;


}
