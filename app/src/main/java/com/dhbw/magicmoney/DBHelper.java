package com.dhbw.magicmoney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;


public class DBHelper extends OrmLiteSqliteOpenHelper {

    // Fields

    public static final String DB_NAME = "magicmoney";
    private static final int DB_VERSION = 1;

    // Public methods

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
        try {

            // Create Table with given table name with columnName
            TableUtils.createTable(cs, User.class);

        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(db, connectionSource);

        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }

    }

    public List getAll(Class clazz) throws SQLException {
        Dao<User, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public  User getById(Class clazz, Object aId) throws SQLException {
        Dao<User, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(User obj) throws SQLException {
        Dao<User, ?> dao = (Dao<User, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public  int deleteById(Class clazz, Object aId) throws SQLException {
        Dao<User, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }
}