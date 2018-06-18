package com.dhbw.magicmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Currency;

public class HomeActivity extends NavigationActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
           user = new User((String) bundle.get("username"),(String) bundle.get("email"),(String) bundle.get("password"),(String) bundle.get("name"),(String) bundle.get("forename"),(double) bundle.get("balance"));
        }

        TextView balanceView = (TextView) findViewById(R.id.home_balance_view);
        Currency c = Currency.getInstance("EUR");
        DecimalFormat decimalFormat = new DecimalFormat("0,00 €");
        decimalFormat.setCurrency(c);

        balanceView.setText(decimalFormat.format(user.getBalance()));

        getSupportActionBar().setTitle(user.getForename() + " " + user.getName());

        View hView =  navigationView.getHeaderView(0);
        TextView navHeaderName = (TextView)hView.findViewById(R.id.nav_header_name);
        TextView navHeaderEmail = (TextView)hView.findViewById(R.id.nav_header_email);
        navHeaderName.setText(HomeActivity.user.getUsername());
        navHeaderEmail.setText(HomeActivity.user.getEmail());
    }
}
