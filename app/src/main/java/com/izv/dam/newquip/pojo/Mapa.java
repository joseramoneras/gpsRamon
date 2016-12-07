package com.izv.dam.newquip.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dam on 1/12/16.
 */

@DatabaseTable(tableName = "ubicacion")
public class Mapa implements Parcelable {

    @DatabaseField(columnName = "id", generatedId = true)
    private long id;

    @DatabaseField(columnName = "latitud")
    private double latitud;

    @DatabaseField(columnName = "longitud")
    private double longitud;

    public Mapa(){}

    public Mapa(long id, Double latitud, Double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    protected Mapa(Parcel in) {
        id = in.readLong();
    }

    public static final Creator<Mapa> CREATOR = new Creator<Mapa>() {
        @Override
        public Mapa createFromParcel(Parcel in) {
            return new Mapa(in);
        }

        @Override
        public Mapa[] newArray(int size) {
            return new Mapa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
    }

    @Override
    public String toString() {
        return "Mapa{" +
                "id=" + id +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
