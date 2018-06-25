package com.dhbw.magicmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeActivity extends NavigationActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static User user;
    private List<Transaction> transactionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransactionAdapter mAdapter;


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

        user = (User) getApplication();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            user.setUsername((String) bundle.get("username"));
            user.setEmail((String) bundle.get("email"));
            user.setBalance((Double)bundle.get("balance"));
            user.setForename((String)bundle.get("forename"));
            user.setName((String) bundle.get("name"));
           //user = new User((String) bundle.get("username"),(String) bundle.get("email"),(String) bundle.get("password"),(String) bundle.get("name"),(String) bundle.get("forename"),(double) bundle.get("balance"));
        }
        //test
        TextView balanceView = (TextView) findViewById(R.id.home_balance_view);

        balanceView.setText(user.getEURBalance());

        getSupportActionBar().setTitle(user.getForename() + " " + user.getName());

        View hView =  navigationView.getHeaderView(0);
        TextView navHeaderName = (TextView)hView.findViewById(R.id.nav_header_name);
        TextView navHeaderEmail = (TextView)hView.findViewById(R.id.nav_header_email);
        navHeaderName.setText(HomeActivity.user.getUsername());
        navHeaderEmail.setText(HomeActivity.user.getEmail());


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new TransactionAdapter(transactionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        generateTransactions();
    }

    private void generateTransactions() {
        Transaction u = new Transaction(1,2,2.5);
        transactionList.add(u);

        u = new Transaction(1,2,2.5);
        transactionList.add(u);

        u = new Transaction(1,2,2.5);
        transactionList.add(u);
        u = new Transaction(1,2,2.5);
        transactionList.add(u);
        u = new Transaction(1,2,2.5);
        transactionList.add(u);
        u = new Transaction(1,2,2.5);
        transactionList.add(u);


        mAdapter.notifyDataSetChanged();
    }
}
