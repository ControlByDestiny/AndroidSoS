package com.sos.www.bean.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sos.www.bean.EmergencyContact;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EMERGENCY_CONTACT".
*/
public class EmergencyContactDao extends AbstractDao<EmergencyContact, Long> {

    public static final String TABLENAME = "EMERGENCY_CONTACT";

    /**
     * Properties of entity EmergencyContact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property TelNumber = new Property(2, String.class, "telNumber", false, "TEL_NUMBER");
        public final static Property MsgContent = new Property(3, String.class, "msgContent", false, "MSG_CONTENT");
    }


    public EmergencyContactDao(DaoConfig config) {
        super(config);
    }
    
    public EmergencyContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EMERGENCY_CONTACT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TEL_NUMBER\" TEXT," + // 2: telNumber
                "\"MSG_CONTENT\" TEXT);"); // 3: msgContent
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EMERGENCY_CONTACT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EmergencyContact entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String telNumber = entity.getTelNumber();
        if (telNumber != null) {
            stmt.bindString(3, telNumber);
        }
 
        String msgContent = entity.getMsgContent();
        if (msgContent != null) {
            stmt.bindString(4, msgContent);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EmergencyContact entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String telNumber = entity.getTelNumber();
        if (telNumber != null) {
            stmt.bindString(3, telNumber);
        }
 
        String msgContent = entity.getMsgContent();
        if (msgContent != null) {
            stmt.bindString(4, msgContent);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public EmergencyContact readEntity(Cursor cursor, int offset) {
        EmergencyContact entity = new EmergencyContact( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // telNumber
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // msgContent
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EmergencyContact entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTelNumber(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMsgContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(EmergencyContact entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(EmergencyContact entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EmergencyContact entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}