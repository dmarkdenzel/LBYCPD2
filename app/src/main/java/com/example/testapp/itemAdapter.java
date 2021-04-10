package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder>{

    private ArrayList<items> itemA=new ArrayList<>();
    private Context context;
    public itemAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_itemlist, parent, false);
        itemAdapter.ViewHolder holder=new itemAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(itemA.get(position).getName());
        holder.category.setText(itemA.get(position).getCategory());
        holder.rating.setText(itemA.get(position).getRating());
        holder.price.setText(itemA.get(position).getPrice());

        //ANOTHER WAY OF ADDING CLICK LISTENER
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,itemorder.class);
                intent.putExtra("item_profile",itemA.get(position));
                context.startActivity(intent);
            }
        });

        Glide.with(context).asBitmap().load((itemA.get(position).getUrl())).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return itemA.size();
    }

    public void setItem(ArrayList<items> item) {
        this.itemA = item;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private MaterialCardView parent;
        private TextView rating;
        private TextView category;
        private ImageView picture;
        private TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameofhotitem);
            parent=itemView.findViewById(R.id.hotitemholder);
            rating=itemView.findViewById(R.id.ratingofhotitem);
            category=itemView.findViewById(R.id.categoryofhotitem);
            picture=itemView.findViewById(R.id.imagehotitem);
            price=itemView.findViewById(R.id.price);
        }
    }
}
