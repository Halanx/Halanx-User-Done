package com.halanx.tript.userapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.StoreInfo;
import com.halanx.tript.userapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.halanx.tript.userapp.GlobalAccess.djangoBaseUrl;

public class StoresActivity extends AppCompatActivity {

    RecyclerView storesRecycler;
    ProgressBar pb;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        storesRecycler = (RecyclerView) findViewById(R.id.recycler_stores);
        pb = (ProgressBar) findViewById(R.id.pd_stores);
        pb.setVisibility(View.VISIBLE);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).addConverterFactory(GsonConverterFactory.create());
        Retrofit retro = builder.build();
        DataInterface client = retro.create(DataInterface.class);

        Call<List<StoreInfo>> callStores = client.getStoreInfo();
        callStores.enqueue(new Callback<List<StoreInfo>>() {
            @Override
            public void onResponse(Call<List<StoreInfo>> call, Response<List<StoreInfo>> response) {
                List<StoreInfo> storesList = response.body();
                Log.i("TAG", "RESPONSE");
                pb.setVisibility(View.INVISIBLE);

                List<StoreInfo> stores = new ArrayList<StoreInfo>();
                for(int i = 0 ;i<storesList.size();i++){
                    if(storesList.get(i)!=null){
                        stores.add(storesList.get(i));
                    }
                }

                StoresAdapter adapter = new StoresAdapter(stores);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(StoresActivity.this, 2);
                storesRecycler.setLayoutManager(layoutManager);
                storesRecycler.setAdapter(adapter);
                storesRecycler.setHasFixedSize(true);

            }

            @Override
            public void onFailure(Call<List<StoreInfo>> call, Throwable t) {
                Toast.makeText(StoresActivity.this, "Network error ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {

        List<StoreInfo> storeList;

        public StoresAdapter(List<StoreInfo> storeList) {
            this.storeList = storeList;
        }

        @Override
        public StoresAdapter.StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store_recycler, parent, false);
            return new StoresViewHolder(v);
        }

        @Override
        public void onBindViewHolder(StoresAdapter.StoresViewHolder holder, int position) {
            Picasso.with(StoresActivity.this).load(storeList.get(position).getStoreLogo()).into(holder.ivLogo);
            holder.tvName.setText(storeList.get(position).getStoreName());
            holder.tvAddress.setText(storeList.get(position).getStoreAddress());

        }

        @Override
        public int getItemCount() {
            return storeList.size();
        }

        public class StoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView ivLogo;
            TextView tvName, tvAddress;

            public StoresViewHolder(View itemView) {
                super(itemView);
                ivLogo = (ImageView) itemView.findViewById(R.id.iv_store_logo);
                tvName = (TextView) itemView.findViewById(R.id.tv_store_name);
                tvAddress = (TextView) itemView.findViewById(R.id.tv_store_address);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int pos = getAdapterPosition();
                Log.d("pos", String.valueOf(pos));
                storeList.get(pos).getId();
                Intent intent = new Intent(StoresActivity.this, HomeActivity.class);

                getSharedPreferences("Store", Context.MODE_PRIVATE).edit().
                        putInt("storeID", storeList.get(pos).getId()).
                        putString("storeLogo", storeList.get(pos).getStoreLogo()).putString("storeName", storeList.get(pos).getStoreName()).
                        putInt("storePosition", pos).apply();

                startActivity(intent);
            }
        }
    }


}