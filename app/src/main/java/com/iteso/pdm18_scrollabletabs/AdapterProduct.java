package com.iteso.pdm18_scrollabletabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteso.pdm18_scrollabletabs.beans.ItemProduct;
import com.iteso.pdm18_scrollabletabs.tools.Constant;

import java.util.ArrayList;

/**
 * @author Oscar Vargas
 * @since 26/02/18.
 */

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{

    private ArrayList<ItemProduct> products;
    private Context context;
    private int fragment;

    AdapterProduct(int fragment, Context context, ArrayList<ItemProduct> products){
        this.fragment = fragment;
        this.products = products;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mStore;
        TextView mLocation;
        TextView mPhone;
        RelativeLayout mDetail;
        ImageView mImage;

        ViewHolder(View v){
            super(v);
            mTitle = v.findViewById(R.id.item_product_title);
            mStore = v.findViewById(R.id.item_product_store);
            mLocation = v.findViewById(R.id.item_product_location);
            mPhone = v.findViewById(R.id.item_product_phone);
            mImage = v.findViewById(R.id.item_product_image);
            mDetail = v.findViewById(R.id.item_product_layout);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitle.setText(products.get(holder.getAdapterPosition()).getTitle());
        holder.mStore.setText(products.get(holder.getAdapterPosition()).getStore());
        holder.mLocation.setText(products.get(holder.getAdapterPosition()).getLocation());
        holder.mPhone.setText(products.get(holder.getAdapterPosition()).getPhone());

        switch(products.get(holder.getAdapterPosition()).getImage()){
            case Constant.TYPE_MAC:
                holder.mImage.setImageResource(R.drawable.mac); break;
            case Constant.TYPE_ALIENWARE:
                holder.mImage.setImageResource(R.drawable.alienware); break;
            case Constant.TYPE_SHEETS:
                holder.mImage.setImageResource(R.drawable.sheets); break;
            case Constant.TYPE_PILLOW:
                holder.mImage.setImageResource(R.drawable.pillows); break;
            case Constant.TYPE_REFRIGERATOR:
                holder.mImage.setImageResource(R.drawable.refrigerator); break;
            case Constant.TYPE_MICRO:
                holder.mImage.setImageResource(R.drawable.micro); break;
        }
        Bitmap bitmap = ((BitmapDrawable)holder.mImage.getDrawable()).getBitmap();
        holder.mImage.setImageBitmap(bitmap);

        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, products.get(holder.getAdapterPosition()).toString(),
                //        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, ActivityProduct.class);
                intent.putExtra(Constant.EXTRA_PRODUCT, products.get(holder.getAdapterPosition()));
                intent.putExtra(Constant.EXTRA_FRAGMENT, fragment);
                ((ActivityMain) context).startActivityForResult(intent, Constant.ACTIVITY_DETAIL);
            }
        });

        holder.mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + products.get(holder.getAdapterPosition()).getPhone()));
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
