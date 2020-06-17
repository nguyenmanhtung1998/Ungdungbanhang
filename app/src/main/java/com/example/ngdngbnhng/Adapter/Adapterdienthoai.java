package com.example.ngdngbnhng.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ngdngbnhng.Model.Sanpham;
import com.example.ngdngbnhng.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapterdienthoai extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> sanphamArrayList;

    public Adapterdienthoai(Context context, ArrayList<Sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @Override
    public int getCount() {
        return sanphamArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return sanphamArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Viewholder{
        ImageView imageViewdienthoai;
        TextView tendienthoai,giadienthoai,motadienthoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        if (viewholder==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.dong_dienthoai,null);
            viewholder=new Viewholder();
            viewholder.imageViewdienthoai=convertView.findViewById(R.id.imagedienthoai);
            viewholder.tendienthoai=convertView.findViewById(R.id.tendienthoai);
           viewholder.giadienthoai=convertView.findViewById(R.id.giadienthoai);
            viewholder.motadienthoai=convertView.findViewById(R.id.motadienthoai);
            convertView.setTag(viewholder);
        }else {
            viewholder= (Viewholder) convertView.getTag();
        }
        Sanpham sanpham=sanphamArrayList.get(position);
        viewholder.tendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewholder.giadienthoai.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        viewholder.motadienthoai.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham()).into(viewholder.imageViewdienthoai);
        return convertView;

    }
}
