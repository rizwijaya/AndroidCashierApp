package com.murphy.simplekasir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterKeranjang extends BaseAdapter {

    Context context;
    ArrayList<String> barang;
    ArrayList<String> jumlah;
    ArrayList<String> total;


    public ListAdapterKeranjang(
            Context context2,
            ArrayList<String> barang,
            ArrayList<String> jumlah,
            ArrayList<String> total
    ) {

        this.context = context2;
        this.barang = barang;
        this.jumlah = jumlah;
        this.total = total;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return barang.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listview_keranjang, null);

            holder = new Holder();

            holder.barang = (TextView) child.findViewById(R.id.barang);
            holder.jumlah = (TextView) child.findViewById(R.id.jumlah);
            holder.total = (TextView) child.findViewById(R.id.total);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.barang.setText(barang.get(position));
        holder.jumlah.setText(jumlah.get(position));
        holder.total.setText(total.get(position));

        return child;
    }

    public static class Holder {
        TextView barang;
        TextView jumlah;
        TextView total;
    }
}