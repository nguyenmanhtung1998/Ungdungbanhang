package com.example.ngdngbnhng.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ngdngbnhng.MainActivity.ChitietsanphamActivity;
import com.example.ngdngbnhng.MainActivity.MainActivity;
import com.example.ngdngbnhng.Model.Giohang;
import com.example.ngdngbnhng.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhtoanAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> giohangArrayList;

    public ThanhtoanAdapter(Context context, ArrayList<Giohang> giohangArrayList) {
        this.context = context;
        this.giohangArrayList = giohangArrayList;
    }

    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return giohangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Viewhoilder{
        public ImageView imageView;
        public TextView ten;
        public TextView gia;
        public Button buttontru,buttoncong,buttonsoluong;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewhoilder viewhoilder=null;
        if (viewhoilder==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.dong_chitietsanpham,parent,false);
            viewhoilder=new Viewhoilder();
            viewhoilder.imageView=convertView.findViewById(R.id.imagedongchitiet);
            viewhoilder.ten=convertView.findViewById(R.id.texttenmonhan);
            viewhoilder.gia=convertView.findViewById(R.id.Giamonhang);
            viewhoilder.buttontru=convertView.findViewById(R.id.dongtru);
            viewhoilder.buttoncong=convertView.findViewById(R.id.dongcong);
            viewhoilder.buttonsoluong=convertView.findViewById(R.id.dongsoluong);
            convertView.setTag(viewhoilder);
        }else {
            viewhoilder= (Viewhoilder) convertView.getTag();
        }
        Giohang giohang=giohangArrayList.get(position);
        viewhoilder.ten.setText(giohang.getTen());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewhoilder.gia.setText(decimalFormat.format(giohang.getGiasp())+" Đ");
        Picasso.with(context).load(giohang.getHinhsp()).into(viewhoilder.imageView);
        viewhoilder.buttonsoluong.setText(giohang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewhoilder.buttonsoluong.getText().toString());
        if (sl >= 10){
            viewhoilder.buttoncong.setVisibility(View.INVISIBLE);
            viewhoilder.buttontru.setVisibility(View.VISIBLE);
        }else if (sl <= 1){
            viewhoilder.buttontru.setVisibility(View.INVISIBLE);
        }else if (sl >= 1){
            viewhoilder.buttontru.setVisibility(View.VISIBLE);
            viewhoilder.buttoncong.setVisibility(View.VISIBLE);
        }
        final Viewhoilder finalViewHolder = viewhoilder;
        final Viewhoilder finalViewHolder1 = viewhoilder;
        viewhoilder.buttoncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.buttonsoluong.getText().toString()) + 1;
                int slhientai = MainActivity.giohangArrayList.get(position).getSoluongsp();
                long giaht = MainActivity.giohangArrayList.get(position).getGiasp();
                MainActivity.giohangArrayList.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.giohangArrayList.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.gia.setText(decimalFormat.format(giamoinhat)+ " Đ");
                ChitietsanphamActivity.Evenuntil();
                if (slmoinhat > 14){
                    finalViewHolder1.buttoncong.setVisibility(View.INVISIBLE);
                    finalViewHolder1.buttontru.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonsoluong.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.buttontru. setVisibility(View.VISIBLE);
                    finalViewHolder.buttoncong. setVisibility(View.VISIBLE);
                    finalViewHolder.buttonsoluong.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewhoilder.buttontru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.buttonsoluong.getText().toString()) - 1;
                int slhientai = MainActivity.giohangArrayList.get(position).getSoluongsp();
                long giaht = MainActivity.giohangArrayList.get(position).getGiasp();
                MainActivity.giohangArrayList.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.giohangArrayList.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.gia.setText(decimalFormat.format(giamoinhat)+ " Đ");
                ChitietsanphamActivity.Evenuntil();
                if (slmoinhat < 2){
                    finalViewHolder.buttontru.setVisibility(View.INVISIBLE);
                    finalViewHolder.buttoncong.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonsoluong.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.buttontru. setVisibility(View.VISIBLE);
                    finalViewHolder.buttoncong. setVisibility(View.VISIBLE);
                    finalViewHolder.buttonsoluong.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
