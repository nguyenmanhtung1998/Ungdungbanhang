package com.example.ngdngbnhng.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ngdngbnhng.Model.Giohang;
import com.example.ngdngbnhng.Model.Sanpham;
import com.example.ngdngbnhng.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Giohang_Activity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button buttonmua;
    ImageView imageView;
    int id=0;
    String tensp="";
    int Giasp=0;
    String mpta="";
    int Idsp=0;
    String hinhanhsp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang_);
        anhxa();
        getdata();
        CatcheventSpinner();
        evenButton();
        Actionbar();
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),ChitietsanphamActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void evenButton() {
            buttonmua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.giohangArrayList.size()>0){
                        Boolean exit=false;
                        int sl1=Integer.parseInt(spinner.getSelectedItem().toString());
                        for (int i=0;i<MainActivity.giohangArrayList.size();i++) {
                            if (MainActivity.giohangArrayList.get(i).getIdsp() == id) {
                                MainActivity.giohangArrayList.get(i).setSoluongsp(sl1 + MainActivity.giohangArrayList.get(i).getSoluongsp());
                                if (MainActivity.giohangArrayList.get(i).getSoluongsp() >= 10) {
                                    MainActivity.giohangArrayList.get(i).setSoluongsp(10);
                                }
                                MainActivity.giohangArrayList.get(i).setGiasp(Giasp * MainActivity.giohangArrayList.get(i).getSoluongsp());
                                exit = true;
                            }
                        }
                        if (exit==false){
                            int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                            long Giamoi=soluong*Giasp;
                            MainActivity.giohangArrayList.add(new Giohang(id,tensp,Giamoi,hinhanhsp,soluong));

                        }
                    }else {
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi=soluong*Giasp;
                        MainActivity.giohangArrayList.add(new Giohang(id,tensp,Giamoi,hinhanhsp,soluong));

                    }
                    Intent intent=new Intent(getApplicationContext(),ChitietsanphamActivity.class);
                    startActivity(intent);
                }
            });
    }

    private void CatcheventSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);

    }

    private void getdata() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("giohang");
        id=Integer.parseInt(sanpham.getId());
        tensp=sanpham.getTensanpham();
        Giasp=sanpham.getGiasanpham();
        mpta=sanpham.getMotasanpham();
        Idsp=Integer.parseInt(sanpham.getIdsanpham());
        hinhanhsp=sanpham.getHinhsanpham();
        txtten.setText(tensp);
        txtmota.setText(mpta);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+decimalFormat.format(Giasp)+" Đ");
        Picasso.with(Giohang_Activity.this).load(hinhanhsp).into(imageView);
    }

    private void anhxa() {
        imageView=findViewById(R.id.hínhanhgiohang);
        buttonmua=findViewById(R.id.buttonthemgiohang);
        spinner=findViewById(R.id.spiner);
        txtten=findViewById(R.id.chitietsp);
        txtgia=findViewById(R.id.giasp);
        txtmota=findViewById(R.id.motasp);
        toolbar=findViewById(R.id.toolbargiohang);

    }
}
