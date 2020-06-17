package com.example.ngdngbnhng.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ngdngbnhng.R;
import com.example.ngdngbnhng.util.Checkconnection;
import com.example.ngdngbnhng.util.Server;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
EditText editTexttenkhachhang,editTextemailkhachhang,editTextsodienthoai;
Button buttonxacnhan,buttontrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        anhxa();
        buttontrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            Evenbutton();
        }else {
            Checkconnection.show_toat(getApplicationContext(),"Kiểm tra lại kết nối");
        }
    }

    private void Evenbutton() {
        buttonxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten=editTexttenkhachhang.getText().toString().trim();
                final String sdt=editTextsodienthoai.getText().toString().trim();
                final String email=editTextemailkhachhang.getText().toString().trim();
                if (ten.length()>0&&sdt.length()>0&&email.length()>0){
                    final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.thongtinkhachhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("AAA",madonhang);
                            if (Integer.parseInt(madonhang)>0){
                                RequestQueue requestQueue1=Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1=new StringRequest(Request.Method.POST, Server.chitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                          if (response.equals("1")){
                                              MainActivity.giohangArrayList.clear();
                                              Checkconnection.show_toat(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                              startActivity(intent);
                                          }
                                          else {
                                              Checkconnection.show_toat(getApplicationContext(),"Dữ liệu bị lỗi");
                                          }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray=new JSONArray();
                                        for (int i=0;i<MainActivity.giohangArrayList.size();i++){
                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.giohangArrayList.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.giohangArrayList.get(i).getTen());
                                                jsonObject.put("giasanpham",MainActivity.giohangArrayList.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.giohangArrayList.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap=new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }
                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Checkconnection.show_toat(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void anhxa() {
        editTextemailkhachhang=findViewById(R.id.emailkhachhang);
        editTextsodienthoai=findViewById(R.id.Edittextsodienthoai);
        editTexttenkhachhang=findViewById(R.id.EdittextTenkhachhang);
        buttonxacnhan=findViewById(R.id.buttonxacnhan);
        buttontrove=findViewById(R.id.buttontrove);
    }
}
