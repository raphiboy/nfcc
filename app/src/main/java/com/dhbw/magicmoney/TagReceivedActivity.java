package com.dhbw.magicmoney;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class TagReceivedActivity extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback, NfcAdapter.CreateNdefMessageCallback {

    private ArrayList<String> dataToSendArray = new ArrayList<>();
    private ArrayList<String> dataReceivedArray = new ArrayList<>();

    private String insertedCode = null;

    private String transferValue = null;
    private String code = null;
    private String name = null;
    private String transactionID = null;

    private NfcAdapter mNfcAdapter;
    TextView tvShowText = null;
    Button btnConfirmCode = null;
    EditText etCode = null;

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_received);

        handleNfcIntent(getIntent());

        u = (User) getApplication();

        final Activity cont = this;

        tvShowText = findViewById(R.id.tagReceived_textView);
        etCode = findViewById(R.id.tagReceived_code);

        btnConfirmCode = (Button) findViewById(R.id.btnConfirmCode);
        btnConfirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertedCode = etCode.getText().toString();


                Log.d("Code to put in", code);
                Log.d("Inserted Code", insertedCode);

                if (insertedCode.equals(code)){
                    Log.d("Code", "confirmed");

                    attemptTransaction(cont);


                    Intent myIntent = new Intent(TagReceivedActivity.this, TransactionFeedbackActivity.class);
                    TagReceivedActivity.this.startActivity(myIntent);

                }
                else{
                    Log.d("Code","wrong");

                }
            }
        });
        tvShowText.setText(name + " möchte dir " + transferValue + " schicken. Um dies zu bestätigen trage in das Feld den vierstelligen Code ein, welcher auf " + name + "s Gerät angezeigt wird.");


        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(mNfcAdapter != null) {
            //Handle some NFC initialization here
        }
        else {
            Toast.makeText(this, "NFC not available on this device",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptTransaction(Activity cont){

        //Letzten 2 Stellen abschneiden, um das Euro Zeichen zu entfernen
        String toTransferWithoutCurrency = transferValue.substring(0, transferValue.length() -2);

        //Komma mit Punkt ersetzen
        toTransferWithoutCurrency = toTransferWithoutCurrency.replace(",", ".");

        //String to Int
        int transferValueInt = Integer.parseInt(toTransferWithoutCurrency);

        new ChargeBalanceAsync(cont).execute(transferValueInt);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        if (dataToSendArray.size() == 0) {
            return null;
        }

        NdefRecord[] recordsToAttach = createRecords();

        return new NdefMessage(recordsToAttach);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        Toast.makeText(this, "NFC signal sent!", Toast.LENGTH_SHORT);

    }

    public NdefRecord[] createRecords(){
        NdefRecord[] records = new NdefRecord[dataToSendArray.size() + 1];

        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < dataToSendArray.size(); i++){
                byte[] payload = dataToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));
                NdefRecord record = new NdefRecord(
                        NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,            //Description of our payload
                        new byte[0],                    //The optional id for our Record
                        payload);                       //Our payload for the Record

                records[i] = record;
            }
        }
        //API is high enough that we can use createMime, which is preferred.
        else {
            for (int i = 0; i < dataToSendArray.size(); i++){
                byte[] payload = dataToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain",payload);
                records[i] = record;
            }
        }
        records[dataToSendArray.size()] =
                NdefRecord.createApplicationRecord(getPackageName());
        return records;
    }

    private void handleNfcIntent(Intent NfcIntent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null) {
                dataReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record:attachedRecords) {
                    String string = new String(record.getPayload());

                    if (string.equals(getPackageName())) { continue; }
                    dataReceivedArray.add(string);
                }

                transferValue = dataReceivedArray.get(0);
                code = dataReceivedArray.get(1);
                name = dataReceivedArray.get(2);
                transactionID = dataReceivedArray.get(3);

            }
            else {
                Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handleNfcIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleNfcIntent(intent);
    }
}
