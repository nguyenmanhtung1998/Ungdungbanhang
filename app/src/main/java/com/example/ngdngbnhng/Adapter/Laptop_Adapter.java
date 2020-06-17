package com.example.ngdngbnhng.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ngdngbnhng.Model.Sanpham;
import com.example.ngdngbnhng.R;

import java.util.ArrayList;

public class Laptop_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayList;

    public Laptop_Adapter(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.dong_dienthoai,parent,false);

        return null;
    }
}
