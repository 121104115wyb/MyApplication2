package com.ytlz.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;



import java.util.List;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.annotation.Index;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverterFactory;
import nl.qbusict.cupboard.internal.IndexStatement;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by INTG-02 on 2018/3/21.
 */
public abstract class BaseDatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = BaseDatabaseHelper.class.getSimpleName();

    private boolean mIsClosed;
    private static final String DATABASE_NAME = "intg.db";
    private static final int DATABASE_VERSION = 2;
    private static final int LAST_DATABASE_NUKE_VERSION = 3;

    private static final Class[] ENTITIES = new Class[]{

            TestBean.class,
            PersonalInfoData.class

    };

    static {
        EntityConverterFactory factory = new EntityConverterFactory() {

            @Override
            public <T> EntityConverter<T> create(Cupboard cupboard, Class<T> type) {
                return null;
            }
        };

        for (Class<?> clazz : ENTITIES) {
            cupboard().register(clazz);
        }
    }


    public BaseDatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        for (Class<?> entity : cupboard().getRegisteredEntities()) {
            if (cupboard().getRegisteredEntities().contains(entity)) {
                EntityConverter<?> converter = cupboard().getEntityConverter(entity);
                createNewTable(db, converter.getTable(), converter.getColumns());
            }
        }
    }

    private boolean createNewTable(SQLiteDatabase db, String table, List<EntityConverter.Column> cols) {
        StringBuilder sql = new StringBuilder("create table if not exists '").append(table).append(
                "' (_id integer primary key autoincrement");

        IndexStatement.Builder builder = new IndexStatement.Builder();
        for (EntityConverter.Column col : cols) {
            if (col.type == EntityConverter.ColumnType.JOIN) {
                continue;
            }
            String name = col.name;
            if (!name.equals(BaseColumns._ID)) {
                sql.append(", '").append(name).append("'");
                sql.append(" ").append(col.type.toString());
            }
            Index index = col.index;
            if (index != null) {
                builder.addIndexedColumn(table, name, index);
            }
        }
        sql.append(");");
        db.execSQL(sql.toString());

        for (IndexStatement stmt : builder.build()) {
            db.execSQL(stmt.getCreationSql(table));
        }
        return true;
    }

    /**
     * 升级数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < LAST_DATABASE_NUKE_VERSION) {
            cupboard().withDatabase(db).dropAllTables();
            onCreate(db);
        } else {
            cupboard().withDatabase(db).upgradeTables();
        }
    }

    @Override
    public synchronized void close() {
        super.close();
        mIsClosed = true;
    }

    protected boolean isClosed() {
        return mIsClosed;

    }
}
