package com.murphy.simplekasir;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class BayarActivity extends AppCompatActivity {
    Button btnproses, btnkembali;
    EditText totalbayar, pembayaran, kembalian;

    SqlHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);
        dbcenter = new SqlHelper(this);

        pembayaran = (EditText) findViewById(R.id.pembayaran);
        kembalian = findViewById(R.id.kembalian);
        totalbayar = findViewById(R.id.totalbayar);
        btnkembali = (Button) findViewById(R.id.btnkembali);
        totalbayar.setText(String.valueOf(sumTotal()));
        //setText(String.valueOf(getIntent().getStringExtra("nama")));
        btnproses = (Button) findViewById(R.id.btnproses);
        btnproses.setOnClickListener(arg0 -> kembalian.setText(String.valueOf(hitungbayar())));
        btnkembali.setOnClickListener(arg0 -> {
            String nama = getIntent().getStringExtra("nama");
            Intent inte = new Intent(BayarActivity.this, KeranjangActivity.class);
            inte.putExtra("nama", nama);
            startActivity(inte);
        });
    }

    public int hitungbayar() {
        int subtotal = Integer.parseInt(totalbayar.getText().toString());
        int bayar = Integer.parseInt(pembayaran.getText().toString());
        return bayar - subtotal;
    }

    public int sumTotal(){
        int total=0;
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        try (Cursor sumtotal = db.rawQuery("SELECT SUM(total) FROM transaksi WHERE pelanggan = '" +
                getIntent().getStringExtra("nama") + "'", null)) {
            if (sumtotal.moveToFirst()) {
                total = sumtotal.getInt(0);
            }
        }
        return total;
    }
}
