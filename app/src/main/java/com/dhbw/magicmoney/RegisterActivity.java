package com.dhbw.magicmoney;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class RegisterActivity extends AppCompatActivity {

    private UserRegisterTask registerTask = null;

    private EditText usernameView;
    private EditText nameView;
    private EditText forenameView;
    private EditText emailView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        usernameView = (EditText) findViewById(R.id.register_username);
        nameView = (EditText) findViewById(R.id.register_name);
        forenameView = (EditText) findViewById(R.id.register_forename);
        emailView = (EditText) findViewById(R.id.register_email);
        passwordView = (EditText) findViewById(R.id.register_password);

        Button registerButton = (Button) findViewById(R.id.register_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button backButton = (Button) findViewById(R.id.register_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            }
        });
    }

    private void attemptRegister() {
        if (registerTask != null) {
            return;
        }

        usernameView.setError(null);
        nameView.setError(null);
        forenameView.setError(null);
        emailView.setError(null);
        passwordView.setError(null);

        String username = usernameView.getText().toString();
        String name = nameView.getText().toString();
        String forename = forenameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (false) {

        } else {
            System.out.println("ELSE");
            //showProgress(true);
            registerTask = new UserRegisterTask(username, name, forename, email, password);
            registerTask.execute((Void) null);
        }
    }

    //CHECK STUFF


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String name;
        private final String forename;
        private final String email;
        private final String password;

        UserRegisterTask(String username, String name, String forename, String email, String password) {
            this.username = username;
            this.name = name;
            this.forename = forename;
            this.email = email;
            this.password = password;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            System.out.println("BG");

            User user = new User(username,email,password,name,forename, 0);

            ConnectionSource connectionSource = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                // create our data-source for the database
                connectionSource = new JdbcConnectionSource("jdbc:mysql://den1.mysql2.gear.host:3306/magicmoney", "magicmoney", "magic!");
                // setup our database and DAOs
                Dao<User, Integer> accountDao = DaoManager.createDao(connectionSource, User.class);
                // read and write some data
                accountDao.create(user);
                System.out.println("\n\nIt seems to have worked\n\n");
            } catch (Exception e) {
                System.out.println(e);
            }
            finally {
                // destroy the data source which should close underlying connections
                if (connectionSource != null) {
                    try {
                        connectionSource.close();
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
            /*
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            return true; //CHANGE
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            registerTask = null;
            //showProgress(false);

            if (success) {
                finish();
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            } else {
                emailView.setError(getString(R.string.error_alreadyInUse_email));
                emailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            registerTask = null;
            //showProgress(false);
        }
    }

}
