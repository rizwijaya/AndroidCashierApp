package com.murphy.simplekasir;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewBarangActivity extends AppCompatActivity {
    protected Cursor cursor;
    SqlHelper dbHelper;
    Button kembali;
    TextView nama, jumlah, harga, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbarang);

        dbHelper = new SqlHelper(this);
        nama = (TextView) findViewById(R.id.nama);
        harga = (TextView) findViewById(R.id.harga);
        jumlah = (TextView) findViewById(R.id.jumlah);
        total = (TextView) findViewById(R.id.total);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE id = '" +
                getIntent().getStringExtra("id") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            nama.setText(cursor.getString(1));
            harga.setText(cursor.getString(3));
            jumlah.setText(cursor.getString(4));
            total.setText(cursor.getString(5));
        }
        kembali = (Button) findViewById(R.id.kembali);
        kembali.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            finish();
        });
    }

}
