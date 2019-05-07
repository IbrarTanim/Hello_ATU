package com.dtec.helloatu.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.dao.CrimeDao;
import com.dtec.helloatu.dao.DaoMaster;
import com.dtec.helloatu.dao.DaoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * @author Octa
 */
public class DatabaseManager implements IDatabaseManager, AsyncOperationListener {

    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    /**
     * Instance of DatabaseManager
     */
    private static DatabaseManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link android.content.Context}.
     */
    public DatabaseManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, "sample-database", null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }

    /**
     * @param context The Android {@link android.content.Context}.
     * @return this.instance
     */
    public static DatabaseManager getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(Crime.class);    // clear all elements from a table

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Crime insertCrime(Crime crime) {
        try {
            if (crime != null) {
                openWritableDb();
                CrimeDao crimeDao = daoSession.getCrimeDao();
                crimeDao.insert(crime);
                Log.d(TAG, "Inserted Crime: " + crime.getCrimeInfo() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crime;
    }

    @Override
    public Crime getCrimeById(Long crimeId) {
        Crime crime = null;
        try {
            openReadableDb();
            CrimeDao crimeDao = daoSession.getCrimeDao();
            crime = crimeDao.load(crimeId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crime;
    }

    @Override
    public ArrayList<Crime> listCrime() {
        List<Crime> crimes = null;
        try {
            openReadableDb();
            CrimeDao crimeDao = daoSession.getCrimeDao();
            crimes = crimeDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (crimes != null) {
            return new ArrayList<>(crimes);
        }
        return null;
    }

    @Override
    public void updateCrime(Crime crime) {
        try {
            if (crime != null) {
                openWritableDb();
                daoSession.update(crime);
                Log.d(TAG, "Updated Crime: " + crime.getCrimeInfo() + " from the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteCrimeById(Long crimeId) {

        try {
            openWritableDb();
            CrimeDao crimeDao = daoSession.getCrimeDao();
            QueryBuilder<Crime> queryBuilder = crimeDao.queryBuilder().where(CrimeDao.Properties.Id.eq(crimeId));
            List<Crime> crimes = queryBuilder.list();
            for (Crime crime : crimes) {
                crimeDao.delete(crime);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean isCrime(int crimeId) {

        try {
            openWritableDb();
            CrimeDao crimeDao = daoSession.getCrimeDao();
            QueryBuilder<Crime> queryBuilder = crimeDao.queryBuilder().where(CrimeDao.Properties.Id.eq(crimeId));
            List<Crime> crimes = queryBuilder.list();
            if (crimes.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void deleteAllCrime() {
        try {
            openWritableDb();
            CrimeDao crimeDao = daoSession.getCrimeDao();
            crimeDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all Crime from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
