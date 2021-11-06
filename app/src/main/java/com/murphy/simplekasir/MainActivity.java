package com.murphy.simplekasir;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SqlHelper dbHelper;
    Button btnkeluar, btnsimpan, btnkeranjang;
    EditText pelanggan, barang, jmlbarang, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SqlHelper(this);
        pelanggan = (EditText) findViewById(R.id.pelanggan);
        barang = (EditText) findViewById(R.id.barang);
        jmlbarang = (EditText) findViewById(R.id.jmlbarang);
        harga = (EditText) findViewById(R.id.harga);
        //total = (EditText) findViewById(R.id.total);
        btnkeluar = (Button) findViewById(R.id.btnkeluar);
        btnsimpan = (Button) findViewById(R.id.btnsimpan);
        btnkeranjang = (Button) findViewById(R.id.btnkeranjang);

        btnkeranjang.setOnClickListener(arg0 -> dialogNama());

        btnsimpan.setOnClickListener(arg0 -> {
            int hrg = Integer.parseInt(harga.getText().toString());
            int jml = Integer.parseInt(jmlbarang.getText().toString());
            int total = hrg * jml;

            // TODO Auto-generated method stub
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("insert into transaksi(pelanggan, nama_barang, jumlah_barang, harga, total) values('" +
                    pelanggan.getText().toString() + "','" +
                    barang.getText().toString() + "','" +
                    jmlbarang.getText().toString() + "','" +
                    harga.getText().toString() + "','" +
                    total + "')");
            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
            pelanggan.setText("");
            barang.setText("");
            jmlbarang.setText("");
            harga.setText("");
        });
        btnkeluar.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            moveTaskToBack(true);
        });
    }



    public void dialogNama()
    {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pelanggan");
        builder.setMessage("Masukan Nama Pelanggan");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate( R.layout.activity_dialog, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton("OK", (dialog, which) -> {
            EditText nama = customLayout.findViewById(R.id.nama); // send data from the
            Intent inte = new Intent(getApplicationContext(), KeranjangActivity.class);
            inte.putExtra("nama", nama.getText().toString());
            startActivity(inte);
            Toast.makeText(this, nama.getText().toString(), Toast.LENGTH_SHORT).show();// AlertDialog to the Activity
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();  // create and show
        dialog.show(); // the alert dialog
    }
}