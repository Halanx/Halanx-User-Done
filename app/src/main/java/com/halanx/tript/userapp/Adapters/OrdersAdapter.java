package com.halanx.tript.userapp.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.halanx.tript.userapp.POJO.CartItem;
import com.halanx.tript.userapp.POJO.OrderInfo;
import com.halanx.tript.userapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samarthgupta on 25/06/17.
 */


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.CompletedOrdersHolder> {
    List<OrderInfo> info = new ArrayList<>();
    Context c;
    boolean isCompleted;

    public OrdersAdapter(List<OrderInfo> info, Context ctx, boolean isCompleted) {
        this.info = info;
        this.c = ctx;
        this.isCompleted = isCompleted;
    }

    @Override
    public CompletedOrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_completed_orders_recycler, parent, false);
        return new CompletedOrdersHolder(view, isCompleted);
    }

    @Override
    public void onBindViewHolder(CompletedOrdersHolder holder, int position) {


        String orderTotal = Double.toString(info.get(position).getTotal());


//        String shopperMobile = Long.toString(info.get(position).getShopperPhoneNo());

        String orderId = info.get(position).getId().toString();
        holder.orderNo.setText(orderId);
        holder.orderTotal.setText(orderTotal);

        //      List<String> productNames = new ArrayList<>();
        List<CartItem> cartItems = info.get(position).getOrderItems();

//        for (int i = 0; i < cartItems.size(); i++) {
//            productNames.add(Integer.toString(i + 1) + ". " + cartItems.get(i).getItem().getProductName());
//        }

        ProductRecycler rvAdapter = new ProductRecycler(cartItems);
        holder.rvProducts.setAdapter(rvAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(c);
        holder.rvProducts.setLayoutManager(manager);

        //     holder.orders.setOrientation(LinearLayout.VERTICAL);

//        for (int i = 0; i < cartItems.size(); i++) {
//
//
//            TextView tv = new TextView(c);
//            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            tv.setText(productNames.get(i));
//            tv.setTextSize(20);
//            tv.setClickable(true);
//            holder.orders.addView(tv);
//        }

        if (isCompleted) {
            holder.llIOnGoing.setVisibility(View.GONE);
            holder.llCompleted.setVisibility(View.VISIBLE);
            holder.orderDeliverDate.setText(info.get(position).getDeliveryDate());
        } else {
            holder.llIOnGoing.setVisibility(View.VISIBLE);
            holder.llCompleted.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return info.size();
    }

    public class CompletedOrdersHolder extends RecyclerView.ViewHolder {
        TextView orderNo, orderTotal, orderDeliverDate, orderDeliveredBy;
        LinearLayout llIOnGoing, llCompleted;
        ImageView ivNav;

        RecyclerView rvProducts;

        public CompletedOrdersHolder(View itemView, boolean isCompleted) {
            super(itemView);

            orderNo = (TextView) itemView.findViewById(R.id.textView_order_number);
            orderTotal = (TextView) itemView.findViewById(R.id.textView_order_total);
            orderDeliverDate = (TextView) itemView.findViewById(R.id.textView_order_date);
            orderDeliveredBy = (TextView) itemView.findViewById(R.id.textView_order_delivered_by);
            llIOnGoing = (LinearLayout) itemView.findViewById(R.id.ll_onGoingOrder);
            llCompleted = (LinearLayout) itemView.findViewById(R.id.ll_completed);
            rvProducts = (RecyclerView) itemView.findViewById(R.id.rv_products);

            ivNav = (ImageView) itemView.findViewById(R.id.iv_navigate);

            ivNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c, "Navigate", Toast.LENGTH_SHORT).show();
                    Log.i("CLICK", "CLICK");

                }
            });


        }
    }

    class ProductRecycler extends RecyclerView.Adapter<ProductRecycler.ProductHolder> {

        List<CartItem> items;

        public ProductRecycler(List<CartItem> items) {
            this.items = items;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_products, parent, false));
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {

            String text = String.valueOf(position + 1) + ". " + items.get(position).getItem().getProductName();
            holder.tvProduct.setText(text);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ProductHolder extends RecyclerView.ViewHolder {
            TextView tvProduct;

            public ProductHolder(View itemView) {
                super(itemView);
                tvProduct = (TextView) itemView.findViewById(R.id.tv_product_orders);
            }
        }
    }
}

