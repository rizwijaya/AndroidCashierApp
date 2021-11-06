package com.murphy.simplekasir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class KeranjangActivity extends AppCompatActivity {
    String[] daftar;
    ListView ListView01;
    protected Cursor cursor;

    SqlHelper dbcenter;
    ListAdapterKeranjang listAdapter;
    ArrayList<String> arrbarang = new ArrayList<>();
    ArrayList<String> arrjumlah = new ArrayList<>();
    ArrayList<String> arrtotal = new ArrayList<>();
    Button btnkembali, btnproses, btnhapus;

    @SuppressLint("StaticFieldLeak")
    public static KeranjangActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        ma = this;
        dbcenter = new SqlHelper(this);
        //Find View
        btnproses = findViewById(R.id.btnproses);
        btnkembali= findViewById(R.id.btnkembali);
        btnhapus = findViewById(R.id.btnhapus);
        String nama = getIntent().getStringExtra("nama");
        btnproses.setOnClickListener(arg0 -> {
                    Intent inte2 = new Intent(KeranjangActivity.this, BayarActivity.class);
                    inte2.putExtra("nama", nama);
                    startActivity(inte2);
        });
        btnkembali.setOnClickListener(arg0 -> {
            Intent inte = new Intent(KeranjangActivity.this, MainActivity.class);
            startActivity(inte);
        });
        btnhapus.setOnClickListener(arg0 -> {
            SQLiteDatabase db1 = dbcenter.getWritableDatabase();
            db1.execSQL("delete from transaksi where pelanggan = '"+ nama + "'");
            RefreshList();
        });

        ma = this;
        dbcenter = new SqlHelper(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transaksi WHERE pelanggan = '" +
                getIntent().getStringExtra("nama") + "'", null);

        ListView01 = findViewById(R.id.listView1);
        arrbarang.clear();
        arrjumlah.clear();
        arrtotal.clear();
        daftar = new String[cursor.getCount()];
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(0);
            arrbarang.add(cursor.getString(cursor.getColumnIndex("nama_barang")));

            arrjumlah.add(cursor.getString(cursor.getColumnIndex("harga")) + " buah");

            arrtotal.add("Rp " + cursor.getString(cursor.getColumnIndex("total")));
        }

        //ListView01.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftar));
        listAdapter = new ListAdapterKeranjang(KeranjangActivity.this,
                arrbarang,
                arrjumlah,
                arrtotal
            );
        ListView01.setAdapter(listAdapter);
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
            final CharSequence[] dialogitem = {"Cek Barang", "Update Barang", "Hapus Barang"};
            AlertDialog.Builder builder = new AlertDialog.Builder(KeranjangActivity.this);
            builder.setTitle("Pilihan");
            builder.setItems(dialogitem, (dialog, item) -> {
                switch(item){
                    case 0 :
                        Intent i = new Intent(getApplicationContext(), ViewBarangActivity.class);
                        i.putExtra("id", selection);
                        startActivity(i);
                        break;
                    case 1 :
                        Intent in = new Intent(getApplicationContext(), UpdateActivity.class);
                        in.putExtra("id", selection);
                        startActivity(in);
                        break;
                    case 2 :
                        SQLiteDatabase db1 = dbcenter.getWritableDatabase();
                        db1.execSQL("delete from transaksi where id = '"+selection+"'");
                        RefreshList();
                        break;
                }
            });
            builder.create().show();
        });
        cursor.close();
       ((ListAdapterKeranjang)ListView01.getAdapter()).notifyDataSetInvalidated();
    }
}
