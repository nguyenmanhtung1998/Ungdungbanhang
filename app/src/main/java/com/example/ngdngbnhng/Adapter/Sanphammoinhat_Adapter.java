package com.example.ngdngbnhng.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngdngbnhng.MainActivity.Giohang_Activity;
import com.example.ngdngbnhng.MainActivity.MainActivity;
import com.example.ngdngbnhng.Model.Sanpham;
import com.example.ngdngbnhng.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Sanphammoinhat_Adapter  extends RecyclerView.Adapter<Sanphammoinhat_Adapter.Viewholder> {
     ArrayList<Sanpham> sanphamArrayList;
     Context context;

    public Sanphammoinhat_Adapter(ArrayList<Sanpham> sanphamArrayList, Context context) {
        this.sanphamArrayList = sanphamArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_sanphammoinhat,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
           Sanpham sanpham=sanphamArrayList.get(position);
           holder.textViewtensp.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
           holder.textViewtengia.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        Picasso.with(context).load(sanpham.getHinhsanpham()).into(holder.imageViewhinhsanhsp);
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
         ImageView imageViewhinhsanhsp;
         TextView textViewtensp;
         TextView textViewtengia;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageViewhinhsanhsp=itemView.findViewById(R.id.imagesp);
            textViewtengia=itemView.findViewById(R.id.texttengiasp);
            textViewtensp=itemView.findViewById(R.id.texttensp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Giohang_Activity.class);
                    intent.putExtra("giohang",sanphamArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
