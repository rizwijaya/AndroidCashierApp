package com.murphy.simplekasir;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    protected Cursor cursor;
    SqlHelper dbHelper;
    Button btnsimpan, btnkembali;
    EditText barang, harga, jmlbarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbHelper = new SqlHelper(this);
        barang = (EditText) findViewById(R.id.barang);
        jmlbarang = (EditText) findViewById(R.id.jmlbarang);
        harga = (EditText) findViewById(R.id.harga);
        //totalbayar = (EditText) findViewById(R.id.totalbayar);

        btnkembali = (Button) findViewById(R.id.btnkembali);
        btnsimpan = (Button) findViewById(R.id.btnsimpan);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE id = '" +
                getIntent().getStringExtra("id") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            barang.setText(cursor.getString(1));
            jmlbarang.setText(cursor.getString(4));
            harga.setText(cursor.getString(3));
            //totalbayar.setText(cursor.getString(5));
        }

        // daftarkan even onClick pada btnSimpan
        btnsimpan.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub

            String nama = cursor.getString(2);
            int hrg = Integer.parseInt(jmlbarang.getText().toString());
            int jml = Integer.parseInt(harga.getText().toString());
            int total = hrg * jml;

            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
            db1.execSQL("update transaksi set id ='" +
                    getIntent().getStringExtra("id") + "', nama_barang ='" +
                    barang.getText().toString() + "', pelanggan ='" +
                    nama + "', jumlah_barang ='" +
                    harga.getText().toString() + "', harga ='" +
                    jmlbarang.getText().toString() + "', total ='" +
                    total + "' where id = '" +
                    getIntent().getStringExtra("id") + "'");
            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
            KeranjangActivity.ma.RefreshList();
            finish();
        });

        btnkembali.setOnClickListener(arg0 -> finish());
    }
}
