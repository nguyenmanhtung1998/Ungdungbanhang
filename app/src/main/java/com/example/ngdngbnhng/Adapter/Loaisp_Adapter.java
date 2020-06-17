package com.example.ngdngbnhng.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ngdngbnhng.Model.Loaisanpham;
import com.example.ngdngbnhng.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Loaisp_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Loaisanpham> loaisanphamArrayList;

    public Loaisp_Adapter(Context context, ArrayList<Loaisanpham> loaisanphamArrayList) {
        this.context = context;
        this.loaisanphamArrayList = loaisanphamArrayList;
    }

    @Override
    public int getCount() {
        return loaisanphamArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaisanphamArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Viewholder{
        TextView textView;
        ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        if(viewholder==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.dong_loaisp,null);
            viewholder=new Viewholder();
            viewholder.textView=convertView.findViewById(R.id.textsp);
            viewholder.imageView=convertView.findViewById(R.id.hínhloaisp);
            convertView.setTag(viewholder);
        }
        else {
            viewholder= (Viewholder) convertView.getTag();

        }
        Loaisanpham loaisanpham= (Loaisanpham) getItem(position);
        viewholder.textView.setText(loaisanpham.getTenloaisanpham());
        Picasso.with(context).load(loaisanpham.getHinhanhsanpham()).placeholder(R.drawable.
                ic_broken_image_black_24dp).error(R.drawable.ic_error_black_24dp).into(viewholder.imageView);
        return convertView;
    }
}
