package com.halanx.tript.userapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.halanx.tript.userapp.Adapters.ProductAdapter;
import com.halanx.tript.userapp.Interfaces.DataInterface;
import com.halanx.tript.userapp.POJO.ProductInfo;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    ProductAdapter adapter;
    RecyclerView storesRecycler;
    ProgressBar pb;

    Spinner storeSpinner, categorySpinner;
    List<String> storeNames;

    LinearLayout main;
    RelativeLayout stores;

    TextView brandName;
    ImageView brandLogo;
    int pos;
    String storelogo, storename;



    DataInterface client;
    Retrofit retrofit;
    Retrofit.Builder builder;
    int storeID, storePosition;
    List<StoreInfo> storesList = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);


        storeSpinner = (Spinner) view.findViewById(R.id.store_spinner);
        categorySpinner = (Spinner) view.findViewById(R.id.for_spinner);

        brandName = (TextView) view.findViewById(R.id.brandName);
        brandLogo = (ImageView) view.findViewById(R.id.logo);

        stores = (RelativeLayout) view.findViewById(R.id.stores);
        main = (LinearLayout) view.findViewById(R.id.main);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Store", Context.MODE_PRIVATE);
        Picasso.with(getActivity()).load(sharedPreferences.getString("storeLogo", null)).into(brandLogo);
        brandName.setText(sharedPreferences.getString("storeName", null));
        storeID = sharedPreferences.getInt("storeID", 0);
        storePosition = sharedPreferences.getInt("storePosition", 0);

        builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).
                addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

        if (getActivity().getSharedPreferences("Store", Context.MODE_PRIVATE).getBoolean("isMap", false)) {
            main.setVisibility(View.GONE);
            stores.setVisibility(View.VISIBLE);

        } else {
            main.setVisibility(View.VISIBLE);
            stores.setVisibility(View.GONE);
        }

        client = retrofit.create(DataInterface.class);
        storeSpinner.setOnItemSelectedListener(this);

        Call<List<StoreInfo>> callStores = client.getStoreInfo();
        callStores.enqueue(new Callback<List<StoreInfo>>() {
            @Override
            public void onResponse(Call<List<StoreInfo>> call, Response<List<StoreInfo>> response) {


                storesList = response.body();
                storeNames = new ArrayList<>();

                for (int i = 0; i < storesList.size(); i++) {

                    if (storesList.get(i) != null) {
                        storeNames.add(storesList.get(i).getStoreName());
                    }
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, storeNames);
                spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                storeSpinner.setAdapter(spinnerAdapter);
                storeSpinner.setSelection(storePosition);
                getProductsFromStore(storeID);

            }

            @Override
            public void onFailure(Call<List<StoreInfo>> call, Throwable t) {

            }
        });


        storesRecycler = (RecyclerView) view.findViewById(R.id.recycler_stores);
        pb = (ProgressBar) view.findViewById(R.id.pd_stores);
        pb.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).addConverterFactory(GsonConverterFactory.create());
        Retrofit retro = builder.build();
        DataInterface client = retro.create(DataInterface.class);

        Call<List<StoreInfo>> callStore = client.getStoreInfo();
        callStore.enqueue(new Callback<List<StoreInfo>>() {
            @Override
            public void onResponse(Call<List<StoreInfo>> call, Response<List<StoreInfo>> response) {
                List<StoreInfo> storesList = response.body();
                Log.i("TAG", "RESPONSE");
                pb.setVisibility(View.INVISIBLE);
                MainFragment.StoresAdapter adapter = new StoresAdapter(storesList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
                storesRecycler.setLayoutManager(layoutManager);
                storesRecycler.setAdapter(adapter);
                storesRecycler.setHasFixedSize(true);

            }

            @Override
            public void onFailure(Call<List<StoreInfo>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Network error ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    private void getProductsFromStore(int storeID) {
        Call<List<ProductInfo>> callProductsStore = client.getProductsFromStore(Integer.toString(storeID));
        callProductsStore.enqueue(new Callback<List<ProductInfo>>() {
            @Override
            public void onResponse(Call<List<ProductInfo>> call, Response<List<ProductInfo>> response) {

                if (response.body() != null) {
                    List<ProductInfo> products = response.body();
                    adapter = new ProductAdapter(products, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<ProductInfo>> call, Throwable t) {
                Log.i("TAG", "R" + t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("TAG", storesList.get(i).getStoreName());
        Picasso.with(getActivity()).load(storesList.get(i).getStoreLogo()).into(brandLogo);
        brandName.setText(storesList.get(i).getStoreName());
        getProductsFromStore(storesList.get(i).getId());

        getActivity().getSharedPreferences("Store", Context.MODE_PRIVATE).edit().
                putInt("storeID", storesList.get(i).getId()).
                putString("storeLogo", storesList.get(i).getStoreLogo()).
                putString("storeName", storesList.get(i).getStoreName()).
                putInt("storePosition", i).apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {

        List<StoreInfo> storeList;

        public StoresAdapter(List<StoreInfo> storeList) {
            this.storeList = storeList;
        }

        @Override
        public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store_recycler, parent, false);
            return new StoresViewHolder(v);
        }

        @Override
        public void onBindViewHolder(StoresViewHolder holder, int position) {
            Picasso.with(getActivity().getApplicationContext()).load(storeList.get(position).getStoreLogo()).into(holder.ivLogo);
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

                pos = getAdapterPosition();
                storeID = storeList.get(pos).getId();
                storelogo = storeList.get(pos).getStoreLogo();
                storename = storeList.get(pos).getStoreName();

                main.setVisibility(View.VISIBLE);
                stores.setVisibility(View.GONE);
                Log.d("position", String.valueOf(pos));
                Log.d("storeID", String.valueOf(storesList.get(pos).getId()));

                getActivity().getSharedPreferences("Store", Context.MODE_PRIVATE).edit().
                        putInt("storeID", storesList.get(pos).getId()).
                        putString("storeLogo", storesList.get(pos).getStoreLogo()).
                        putString("storeName", storesList.get(pos).getStoreName()).
                        putInt("storePosition", pos).apply();

                getActivity().getSharedPreferences("Store", Context.MODE_PRIVATE).edit().putBoolean("isMap", false).apply();
                Log.d("position", String.valueOf(pos));
                storeSpinner.setSelection(pos);


            }
        }
    }

}
