package com.dhbw.magicmoney;

import android.os.AsyncTask;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class ChargeBalanceAsync extends AsyncTask<Integer, Integer, Boolean> {

    int amount;

    @Override
    protected Boolean doInBackground(Integer... amount) {

        boolean sucess;

        this.amount = amount[0];

        ConnectionSource connectionSource = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connectionSource = new JdbcConnectionSource("jdbc:mysql://den1.mysql2.gear.host:3306/magicmoney?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "magicmoney", "magic!");
            Dao<User, Integer> accountDao = DaoManager.createDao(connectionSource, User.class);
            UpdateBuilder<User, Integer> updateBuilder = accountDao.updateBuilder();
            updateBuilder.updateColumnValue("Kontostand", HomeActivity.user.getBalance() + this.amount);
            updateBuilder.where().eq("email",HomeActivity.user.getEmail());
            updateBuilder.update();
            sucess = true;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            sucess = false;
        }finally {
            // destroy the data source which should close underlying connections
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e){
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
        return sucess;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if(success){
            HomeActivity.user.riseBalance(amount);
        }
    }

}
