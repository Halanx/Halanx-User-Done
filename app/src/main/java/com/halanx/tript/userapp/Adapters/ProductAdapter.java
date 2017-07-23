package com.halanx.tript.userapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.halanx.tript.userapp.Activities.ItemDisplayActivity;
import com.halanx.tript.userapp.POJO.ProductInfo;
import com.halanx.tript.userapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samarthgupta on 23/05/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    List<ProductInfo> products = new ArrayList<>();

    Context c;
    public ProductAdapter(List<ProductInfo> products,Context c) {
        this.products = products;
        this.c=c;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_recycler, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view,products,c);

        return holder;

    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        if(products.get(position).getProductImage()!=null){
        Picasso.with(c).load(products.get(position).getProductImage()).into(holder.productImage);}

        else {
            Picasso.with(c).load(R.drawable.fav_48).into(holder.productImage);
        }

        holder.productName.setText(products.get(position).getProductName());
        holder.productPrice.setText("Rs."+String.valueOf(products.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productName, productPrice;
        List<ProductInfo> products;
        Context c;

        public ProductViewHolder(View itemView, List<ProductInfo> products, Context c) {
            super(itemView);
            this.products=products;
            this.c=c;
            itemView.setOnClickListener(this);
            productImage= (ImageView)itemView.findViewById(R.id.itemImage);
            productName= (TextView) itemView.findViewById(R.id.itemName);
            productPrice= (TextView) itemView.findViewById(R.id.itemPrice);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Intent intent = new Intent(c,ItemDisplayActivity.class);
            intent.putExtra("Name",products.get(position).getProductName());
            intent.putExtra("Price", products.get(position).getPrice());
            intent.putExtra("Features",products.get(position).getFeatures());
            intent.putExtra("Image",products.get(position).getProductImage());
            intent.putExtra("ID",products.get(position).getId());
            c.startActivity(intent);

        }
    }



}