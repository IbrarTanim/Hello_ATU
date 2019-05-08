package com.dtec.helloatu.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dtec.helloatu.dao.Crime;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CRIME".
*/
public class CrimeDao extends AbstractDao<Crime, Long> {

    public static final String TABLENAME = "CRIME";

    /**
     * Properties of entity Crime.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CrimeInfo = new Property(1, String.class, "crimeInfo", false, "CRIME_INFO");
        public final static Property OccurrancePlace = new Property(2, String.class, "occurrancePlace", false, "OCCURRANCE_PLACE");
        public final static Property InformerName = new Property(3, String.class, "informerName", false, "INFORMER_NAME");
        public final static Property InformerPhone = new Property(4, String.class, "informerPhone", false, "INFORMER_PHONE");
        public final static Property InformerAddress = new Property(5, String.class, "informerAddress", false, "INFORMER_ADDRESS");
        public final static Property InformerPlace = new Property(6, String.class, "informerPlace", false, "INFORMER_PLACE");
        public final static Property InfoDocument = new Property(7, String.class, "infoDocument", false, "INFO_DOCUMENT");
        public final static Property InfoPicture = new Property(8, String.class, "infoPicture", false, "INFO_PICTURE");
        public final static Property InfoVideo = new Property(9, String.class, "infoVideo", false, "INFO_VIDEO");
        public final static Property InfoAudio = new Property(10, String.class, "infoAudio", false, "INFO_AUDIO");
    };


    public CrimeDao(DaoConfig config) {
        super(config);
    }
    
    public CrimeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CRIME\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CRIME_INFO\" TEXT NOT NULL ," + // 1: crimeInfo
                "\"OCCURRANCE_PLACE\" TEXT NOT NULL ," + // 2: occurrancePlace
                "\"INFORMER_NAME\" TEXT NOT NULL ," + // 3: informerName
                "\"INFORMER_PHONE\" TEXT NOT NULL ," + // 4: informerPhone
                "\"INFORMER_ADDRESS\" TEXT NOT NULL ," + // 5: informerAddress
                "\"INFORMER_PLACE\" TEXT NOT NULL ," + // 6: informerPlace
                "\"INFO_DOCUMENT\" TEXT NOT NULL ," + // 7: infoDocument
                "\"INFO_PICTURE\" TEXT NOT NULL ," + // 8: infoPicture
                "\"INFO_VIDEO\" TEXT NOT NULL ," + // 9: infoVideo
                "\"INFO_AUDIO\" TEXT NOT NULL );"); // 10: infoAudio
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CRIME\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Crime entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getCrimeInfo());
        stmt.bindString(3, entity.getOccurrancePlace());
        stmt.bindString(4, entity.getInformerName());
        stmt.bindString(5, entity.getInformerPhone());
        stmt.bindString(6, entity.getInformerAddress());
        stmt.bindString(7, entity.getInformerPlace());
        stmt.bindString(8, entity.getInfoDocument());
        stmt.bindString(9, entity.getInfoPicture());
        stmt.bindString(10, entity.getInfoVideo());
        stmt.bindString(11, entity.getInfoAudio());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Crime readEntity(Cursor cursor, int offset) {
        Crime entity = new Crime( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // crimeInfo
            cursor.getString(offset + 2), // occurrancePlace
            cursor.getString(offset + 3), // informerName
            cursor.getString(offset + 4), // informerPhone
            cursor.getString(offset + 5), // informerAddress
            cursor.getString(offset + 6), // informerPlace
            cursor.getString(offset + 7), // infoDocument
            cursor.getString(offset + 8), // infoPicture
            cursor.getString(offset + 9), // infoVideo
            cursor.getString(offset + 10) // infoAudio
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Crime entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCrimeInfo(cursor.getString(offset + 1));
        entity.setOccurrancePlace(cursor.getString(offset + 2));
        entity.setInformerName(cursor.getString(offset + 3));
        entity.setInformerPhone(cursor.getString(offset + 4));
        entity.setInformerAddress(cursor.getString(offset + 5));
        entity.setInformerPlace(cursor.getString(offset + 6));
        entity.setInfoDocument(cursor.getString(offset + 7));
        entity.setInfoPicture(cursor.getString(offset + 8));
        entity.setInfoVideo(cursor.getString(offset + 9));
        entity.setInfoAudio(cursor.getString(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Crime entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Crime entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}