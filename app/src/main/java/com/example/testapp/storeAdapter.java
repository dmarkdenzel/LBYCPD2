package com.example.testapp;

import android.content.Context;
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

public class storeAdapter extends RecyclerView.Adapter<storeAdapter.ViewHolder>{


    private ArrayList<stores> storeA=new ArrayList<>();
    private Context context;
    public storeAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.storelist, parent, false);
        ViewHolder holder=new ViewHolder(view);

        //WAY OF ADDING CLICK LISTENER
//        holder.store.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(parent.getContext(),"works: "+String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.store.setText(storeA.get(position).getName());
        holder.adress.setText(storeA.get(position).getAdress());
        holder.rating.setText(storeA.get(position).getRating());

        //ANOTHER WAY OF ADDING CLICK LISTENER
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(position);
                Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(context).load((storeA.get(position).getPicurl())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return storeA.size();
    }

    public void setStore(ArrayList<stores> store) {
        this.storeA = store;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView store;
        private MaterialCardView parent;
        private ImageView image;
        private TextView rating;
        private TextView adress;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            store=itemView.findViewById(R.id.namestorepic);
            parent=itemView.findViewById(R.id.singlestore);
            image=itemView.findViewById(R.id.imagestore);
            rating=itemView.findViewById(R.id.rating);
            adress=itemView.findViewById(R.id.adressimage);
        }
    }
}
