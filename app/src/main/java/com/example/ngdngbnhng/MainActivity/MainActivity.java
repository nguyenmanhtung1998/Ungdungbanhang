package com.example.ngdngbnhng.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ngdngbnhng.Adapter.Loaisp_Adapter;
import com.example.ngdngbnhng.Adapter.Sanphammoinhat_Adapter;
import com.example.ngdngbnhng.Model.Giohang;
import com.example.ngdngbnhng.Model.Loaisanpham;
import com.example.ngdngbnhng.Model.Sanpham;
import com.example.ngdngbnhng.R;
import com.example.ngdngbnhng.util.Checkconnection;
import com.example.ngdngbnhng.util.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     RecyclerView recyclerView;
    public static ArrayList<Giohang> giohangArrayList=new ArrayList<>();
     Toolbar toolbar;
     ViewFlipper viewFlipper;
     NavigationView navigationView;
     ListView listView;
     DrawerLayout drawerLayout;
     Sanphammoinhat_Adapter sanphammoinhat_adapter;
     ArrayList<Loaisanpham> loaisanphamArrayList=new ArrayList<>();
     ArrayList<Sanpham> sanphamArrayList=new ArrayList<>();
     Loaisp_Adapter adapter;
     int id=0;
     String tenloaisp="";
     String hinhloaisp="";
    String tenloaispmoinhat="";
    String hinhloaispmoinhat="";
    int giasp=0;
    int idsp=0;
    String motasp="";
    int id2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
            Actionbar();
            Actionviewlipper();
            Getdulieuloaisp();
            Getdulieuspmoinhat();
            Checklistenner();
        }else {
            Checkconnection.show_toat(getApplicationContext(),"Hãy kiểm tra lại kêt nối mạng");
        }

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

    private void Checklistenner() {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                                Intent intent=new Intent(MainActivity.this,TrangchinhActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "Kiem tra lai ket noi", Toast.LENGTH_SHORT).show();
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case 1:
                            if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                                Intent intent=new Intent(MainActivity.this,DienthoaiActivity.class);
                                intent.putExtra("dienthoai",loaisanphamArrayList.get(position).getId());
                                Log.d("LLL",loaisanphamArrayList.get(position).getId());
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "Kiem tra lai ket noi", Toast.LENGTH_SHORT).show();
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case 2:
                            if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                                Intent intent=new Intent(MainActivity.this,DienthoaiActivity.class);
                                intent.putExtra("dienthoai",loaisanphamArrayList.get(position).getId());
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "Kiem tra lai ket noi", Toast.LENGTH_SHORT).show();
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case 3:
                            if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                                Intent intent=new Intent(MainActivity.this,LienheActiviti.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "Kiem tra lai ket noi", Toast.LENGTH_SHORT).show();
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case 4:
                            if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                                Intent intent=new Intent(MainActivity.this,ThongtinActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "Kiem tra lai ket noi", Toast.LENGTH_SHORT).show();
                            }
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    }

                }
            });

    }

    private void Getdulieuspmoinhat() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id2=jsonObject.getInt("id");
                            tenloaispmoinhat=jsonObject.getString("tensanpham");
                            giasp=jsonObject.getInt("giasanpham");
                            hinhloaispmoinhat=jsonObject.getString("hinhsanpham");
                            motasp=jsonObject.getString("motasanpham");
                            idsp=jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(id2+"",tenloaispmoinhat,giasp,
                                    hinhloaispmoinhat,motasp,idsp+""));

                            sanphammoinhat_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
            requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisp() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Duongdansp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                  for(int i=0;i<response.length();i++){
                      try {
                          JSONObject jsonObject=response.getJSONObject(i);
                          id=jsonObject.getInt("id");
                          tenloaisp=jsonObject.getString("tenloaisanpham");
                          hinhloaisp=jsonObject.getString("hinhanhsanpham");
                          loaisanphamArrayList.add(new Loaisanpham(id+"",tenloaisp,hinhloaisp));

                          adapter.notifyDataSetChanged();

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
                  loaisanphamArrayList.add(3,new Loaisanpham("","Liên hệ","https://hanghoatotvn.com/public/2016/01/contactusicon.png"));
                    loaisanphamArrayList.add(4,new Loaisanpham("","Thông tin","http://lis.tnmt.danang.gov.vn/lis-home-frontend-theme/img/icons/icon-3.svg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Actionviewlipper() {
        ArrayList<String> arrayListquangcao=new ArrayList<>();
        arrayListquangcao.add("https://cdn.tgdd.vn/Products/Images/44/198795/lenovo-ideapad-530s-14ikb-i7-8550u-8gb-256gb-win10-33397-thumb-600x600.jpg");
        arrayListquangcao.add("https://cdn.tgdd.vn/Products/Images/44/200052/asus-ux533fd-i5-8265u-8gb-256gb-2gb-gtx1050-156f-c-thumb-2-600x600.jpg");
        arrayListquangcao.add("https://cdn.tgdd.vn/Products/Images/44/201237/acer-swift-sf5-i7-8565u-8gb-256gb-win10-6-1-600x600.jpg");
        arrayListquangcao.add("https://cdn.tgdd.vn/Products/Images/7077/194536/apple-watch-s3-gps-42mm-vien-nhom-day-cao-su-den-nt-600x600.jpg");
        for (int i=0;i<arrayListquangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayListquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation animation_in_ringt= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slidein_ringt);
        Animation animation_out_right=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in_ringt);
        viewFlipper.setOutAnimation(animation_out_right);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu_sort);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhxa() {
        recyclerView=findViewById(R.id.Recycleviewsanphammoinhat);
        toolbar=findViewById(R.id.toolbar);
        viewFlipper=findViewById(R.id.viewflipper);
        navigationView=findViewById(R.id.navigation);
        listView=findViewById(R.id.listview);
        drawerLayout=findViewById(R.id.drawlayout);
        loaisanphamArrayList.add(0,new Loaisanpham("","Trang chính","https://png.pngtree.com/png-clipart/20190520/original/pngtree-home-icon-png-image_3568907.jpg"));
        adapter=new Loaisp_Adapter(MainActivity.this,loaisanphamArrayList);
        sanphammoinhat_adapter=new Sanphammoinhat_Adapter(sanphamArrayList,MainActivity.this);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        recyclerView.setAdapter(sanphammoinhat_adapter);
        listView.setAdapter(adapter);

    }


}
