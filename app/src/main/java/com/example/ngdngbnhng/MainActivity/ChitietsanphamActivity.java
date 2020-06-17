package com.example.ngdngbnhng.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngdngbnhng.Adapter.ThanhtoanAdapter;
import com.example.ngdngbnhng.R;
import com.example.ngdngbnhng.util.Checkconnection;

import java.text.DecimalFormat;

public class ChitietsanphamActivity extends AppCompatActivity {
    Toolbar toolbarchitiet;
     ListView listViewchitiet;
     TextView tongtien;
    static TextView giatri;
    TextView thongbao;
     Button buttonmuatiep,buttonthanhtoan;
     ThanhtoanAdapter thanhtoanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        anhxa();
        Actionbar();
        getdata();
        Checkdata();
        Evenuntil();
        Sukienxoa();
        Evenbutton();
    }

    private void Actionbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Evenbutton() {
        buttonmuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        buttonthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.giohangArrayList.size()>0){
                    Intent intent=new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(intent);
                }else {
                    Checkconnection.show_toat(getApplicationContext(),"Giỏ hàng chưa có đồ.");
                }

            }
        });
    }

    private void Sukienxoa() {
        listViewchitiet.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.giohangArrayList.size()<=0){
                            thongbao.setVisibility(View.VISIBLE);

                        }else {
                            MainActivity.giohangArrayList.remove(position);
                            thanhtoanAdapter.notifyDataSetChanged();
                            Evenuntil();
                            if (MainActivity.giohangArrayList.size()<=0){
                                thongbao.setVisibility(View.VISIBLE);
                            }else {
                                thongbao.setVisibility(View.INVISIBLE);
                                thanhtoanAdapter.notifyDataSetChanged();
                                Evenuntil();
                            }
                        }
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thanhtoanAdapter.notifyDataSetChanged();
                        Evenuntil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void Evenuntil() {
        long tongien=0;
        for (int i=0;i<MainActivity.giohangArrayList.size();i++){
            tongien+=MainActivity.giohangArrayList.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        giatri.setText(decimalFormat.format(tongien)+" Đ");
    }

    private void Checkdata() {
        if (MainActivity.giohangArrayList.size()<=0){
            thanhtoanAdapter.notifyDataSetChanged();
            listViewchitiet.setVisibility(View.INVISIBLE);
             thongbao.setVisibility(View.VISIBLE);
        }else {
            thanhtoanAdapter.notifyDataSetChanged();
            listViewchitiet.setVisibility(View.VISIBLE);
            thongbao.setVisibility(View.INVISIBLE);
        }
    }

    private void getdata() {

    }

    private void anhxa() {
        toolbarchitiet=findViewById(R.id.toolbarchitietgiohang);
        listViewchitiet=findViewById(R.id.listviewchitietgiohang);
        tongtien=findViewById(R.id.tongtien);
        giatri=findViewById(R.id.giatri);
        buttonmuatiep=findViewById(R.id.tieptucmuahang);
        buttonthanhtoan=findViewById(R.id.thanhtoangiohang);
        thanhtoanAdapter=new ThanhtoanAdapter(getApplicationContext(),MainActivity.giohangArrayList);
        listViewchitiet.setAdapter(thanhtoanAdapter);
        thongbao=findViewById(R.id.thongbaohetdulieu);
    }
}
