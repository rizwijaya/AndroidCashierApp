package com.murphy.simplekasir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kasir.db"; //Inisialisasi Database biodata
    private static final int DATABASE_VERSION = 1;
    public SqlHelper(Context context) { //Inherite
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Buat tabel baru kasir
        String sql = "create table transaksi (id integer primary key autoincrement, nama_barang text null, pelanggan text null, jumlah_barang integer null, harga integer null, total integer null);";
        Log.d("Data","onCreate"+sql);
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}