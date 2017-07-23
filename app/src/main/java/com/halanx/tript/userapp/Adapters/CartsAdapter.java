package com.halanx.tript.userapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.halanx.tript.userapp.POJO.CartItem;
import com.halanx.tript.userapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samarthgupta on 14/06/17.
 */

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.TempViewHolder> {

    List<CartItem> listItems = new ArrayList<>();
    Context c;
    TextView totalitems;




    public CartsAdapter(List<CartItem> listItems, Context cont) {
        this.listItems = listItems;
        this.c = cont;
    }

    @Override
    public TempViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_recycler, parent, false);
        return new TempViewHolder(view, c,listItems);
    }

    @Override
    public void onBindViewHolder(final TempViewHolder holder, int position) {


        double quantity = listItems.get(position).getQuantity();
        int quantityInt = ((int) quantity)-1;
        holder.spinnerQuantity.setSelection(quantityInt);

        Picasso.with(c).load(listItems.get(position).getItem().getProductImage()).into(holder.cartImage);
        holder.cartName.setText(listItems.get(position).getItem().getProductName());
        holder.cartPrice.setText(listItems.get(position).getItem().getPrice().toString());
         holder.cartNotes.setText(listItems.get(position).getNotes());


    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class TempViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView cartImage;
        TextView cartPrice, cartName;
        Spinner spinnerQuantity;
        EditText cartNotes;

        ImageButton btnDelete, btNotesProceed;
        Context c;
        List<CartItem> holderCartItemList = new ArrayList<>();
        String notes;


        public TempViewHolder(View itemView, Context cont, List<CartItem> cartItems) {

            super(itemView);
            btnDelete = (ImageButton) itemView.findViewById(R.id.bt_product_delete);
            cartImage = (ImageView) itemView.findViewById(R.id.iv_product_image);
            cartName = (TextView) itemView.findViewById(R.id.tv_product_name);
            cartPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            spinnerQuantity = (Spinner) itemView.findViewById(R.id.sp_product_quantity);
            cartNotes = (EditText) itemView.findViewById(R.id.et_product_notes);
            btNotesProceed = (ImageButton) itemView.findViewById(R.id.bt_product_notes_proceed);


            c=cont;
            holderCartItemList=cartItems;


            btnDelete.setOnClickListener(this);
            btNotesProceed.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            final int pos = getAdapterPosition();
            String url = "http://ec2-34-208-181-152.us-west-2.compute.amazonaws.com/carts/items/"+holderCartItemList.get(pos).getId();
            switch (view.getId()){
                case R.id.bt_product_delete :
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("RemovedFromCart",true);
                        holderCartItemList.remove(pos);
                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(c, "Item removed", Toast.LENGTH_SHORT).show();

                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));

                    break;

                case R.id.bt_product_notes_proceed :

                    notes = cartNotes.getText().toString();
                    JSONObject objNotes = new JSONObject();
                    try {
                        objNotes.put("Notes",notes);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.PATCH, url, objNotes, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            holderCartItemList.get(pos).setNotes(notes);
                            notifyDataSetChanged();
                            Toast.makeText(c, "Note added", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(c, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    }));
                    break;

            }









        }


    }
}
