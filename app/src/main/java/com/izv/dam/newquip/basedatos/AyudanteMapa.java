package com.izv.dam.newquip.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.izv.dam.newquip.pojo.Mapa;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by dam on 1/12/16.
 */

public class AyudanteMapa extends OrmLiteSqliteOpenHelper {
    private Dao<Mapa, Integer> mapaDao;
    private RuntimeExceptionDao<Mapa, Integer> simpleRunTimeDao = null;

    public AyudanteMapa(Context context) {

        super(context, "ubicacion", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Mapa.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Mapa, Integer> getMapaDao(){

        if ( mapaDao == null ) {

            try {
                mapaDao = getDao(Mapa.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mapaDao;
    }
    public RuntimeExceptionDao<Mapa, Integer> getDataDao(){
        if (simpleRunTimeDao == null){
            simpleRunTimeDao = getRuntimeExceptionDao(Mapa.class);
        }
        return simpleRunTimeDao;
    }

    @Override
    public void close(){
        super.close();
        mapaDao = null;
        simpleRunTimeDao = null;

    }
}
