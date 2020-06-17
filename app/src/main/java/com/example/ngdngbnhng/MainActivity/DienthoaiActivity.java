package com.example.ngdngbnhng.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ngdngbnhng.Adapter.Adapterdienthoai;
import com.example.ngdngbnhng.Model.Giohang;
import com.example.ngdngbnhng.Model.Sanpham;
import com.example.ngdngbnhng.R;
import com.example.ngdngbnhng.util.Checkconnection;
import com.example.ngdngbnhng.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienthoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView recyclerView;
    int page=1;
     Boolean isLoading=false;
     Boolean limitdata=false;
    int iddt=0;
    Adapterdienthoai adapter;
    ArrayList<Sanpham> arrayList;
    mHanler mHanler;
    View footerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
        anhxa();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            getidloaisp();
            Actiontoolbar();
            getdata(page);
            Loadmore();
        }else {
            Toast.makeText(this, "Kiểm tra kết nối!!", Toast.LENGTH_SHORT).show();
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
    private void Loadmore() {
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(getApplicationContext(),Giohang_Activity.class);
               intent.putExtra("giohang",arrayList.get(position));
               startActivity(intent);
            }
        });
       recyclerView.setOnScrollListener(new AbsListView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(AbsListView view, int scrollState) {

           }

           @Override
           public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                 if (firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&isLoading==false&&limitdata==false){
                       isLoading=true;
                       ThreadData threadData=new ThreadData();
                       threadData.start();
                 }
           }
       });
    };


    private void getidloaisp() {
        Intent intent=getIntent();
        if(intent.hasExtra("dienthoai")){
            String dulieu=intent.getStringExtra("dienthoai");
            iddt=Integer.parseInt(dulieu);
        }
    }

    private void getdata(int Page) {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.đuongandienthoai+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 int id=0;
                 String tendt="";
                 int Giadt=0;
                 String mpta="";
                 int Idsp=0;
                 String hinhanhsp="";
                 if(response!=null&&response.length()!=2){
                     recyclerView.removeFooterView(footerview);
                     try {
                         JSONArray jsonArray=new JSONArray(response);
                         for (int i=0;i<jsonArray.length();i++){
                             JSONObject jsonObject=jsonArray.getJSONObject(i);
                             id=jsonObject.getInt("id");
                             tendt=jsonObject.getString("tensanpham");
                             Giadt=jsonObject.getInt("giasanpham");
                             hinhanhsp=jsonObject.getString("hinhsanpham");
                             mpta=jsonObject.getString("motasanpham");
                             Idsp=jsonObject.getInt("idsanpham");
                             arrayList.add(new Sanpham(id+"",tendt,Giadt,
                                     hinhanhsp,mpta,Idsp+""));
                             Log.d("AAA",arrayList.get(0).getHinhsanpham());
                             adapter.notifyDataSetChanged();

                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }else {
                     limitdata=true;
                     recyclerView.removeFooterView(footerview);
                     Toast.makeText(DienthoaiActivity.this, "Het du lieu", Toast.LENGTH_SHORT).show();
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
    requestQueue.add(stringRequest);

    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.Recycleview);
        arrayList=new ArrayList<>();
        adapter=new Adapterdienthoai(DienthoaiActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
       footerview= layoutInflater.inflate(R.layout.progressbar,null);
       mHanler=new mHanler();
       recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(getApplicationContext(),Giohang_Activity.class);
               intent.putExtra("giohang",arrayList.get(position));
               startActivity(intent);
           }
       });
    }
    public class mHanler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    recyclerView.addFooterView(footerview);
                    break;
                case 1:
                    getdata(++page);
                    isLoading=false;
                    break;
            }

            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHanler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHanler.obtainMessage(1);
            mHanler.sendMessage(message);
            super.run();
        }
    }
    public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        public  String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

        private int previousTotal = 0; // The total number of items in the dataset after the last load
        private boolean loading = true; // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int current_page = 1;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading ) {
                // End has been reached

                // Do something
                current_page++;
                Log.d("End", "Sucess");

                onLoadMore(current_page);

                loading = true;
            }
        }

        public abstract void onLoadMore(int current_page);
    }
}
