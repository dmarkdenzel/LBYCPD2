package com.example.testapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class tab3 extends Fragment {
    private FirebaseUser user;
    private DatabaseReference ref,ref2;
    private String uid;
    private FirebaseStorage storage;
    private FirebaseDatabase db;
    private StorageReference storageReference;
    public TextView price;
    public ArrayList<String> itemuuid;
    public LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);

        RecyclerView shoppingcart=rootView.findViewById(R.id.shoppingcartcontainer);

        user= FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("users");
        ref2=FirebaseDatabase.getInstance().getReference("items");
        uid=user.getUid();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        price=rootView.findViewById(R.id.totalprice);

        layoutManager =new LinearLayoutManager(getActivity());
        shoppingcart.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ref.child(uid).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("items")){
                    Map <String, Map<String,Map<String,String>>> quantity = (Map)snapshot.getValue();
                    Map <String, Map<String,String>> map = (Map)snapshot.getValue();
                    Map <String,String> cost=(Map)snapshot.getValue();
                    Set<String> test=map.get("items").keySet();
                    String[] myArray = new String[test.size()];
                    test.toArray(myArray);
                    price.setText(cost.get("subtotal"));

                    ArrayList<carts> cartitems=new ArrayList<>();
                    itemuuid=new ArrayList<>();

                    cartAdapter adapter=new cartAdapter(rootView);
                    adapter.setItem(cartitems,itemuuid);
                    shoppingcart.setAdapter(adapter);


                    for(int i=0;i<myArray.length;i++){
                        int quantityItem=Integer.parseInt(quantity.get("items").get(myArray[i]).get("quantity"));
                        itemuuid.add(myArray[i]);
                        ref2.child(myArray[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                items itemProf=snapshot.getValue(items.class);
                                cartitems.add(new carts(itemProf.getName(),itemProf.getCategory(),itemProf.getUrl(),itemProf.getRating(),itemProf.getPrice(),itemProf.getStock(),itemProf.getBrand(),itemProf.getDescription(),String.valueOf(quantityItem)));
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                            int position = viewHolder.getAdapterPosition();
                            double sum=Double.parseDouble(price.getText().toString());
                            sum-=(Double.parseDouble(cartitems.get(position).getPrice())*(Double.parseDouble(quantity.get("items").get(myArray[position]).get("quantity"))));

                            cartitems.remove(position);
                            itemuuid.remove(position);

                            ref.child(uid).child("cart").child("items").child(myArray[position]).removeValue();
                            ref.child(uid).child("cart").child("subtotal").setValue(String.valueOf(sum));

                            price.setText(String.valueOf(sum));
                            adapter.notifyDataSetChanged();

                        }
                    };
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                    itemTouchHelper.attachToRecyclerView(shoppingcart);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.hasChild("cart")){
//                    //get the key value pair from database
//                    Map <String, Map<String, Map<String,String>>> map = (Map)snapshot.getValue();
//                    Set<String> test=map.get("cart").keySet();
//                    String[] myArray = new String[test.size()];
//                    test.toArray(myArray);
//
//                    ref2.child(map.get())
//
//                    Log.d("current item ref", map.get());
//
//                    for(int i=0;i<myArray.length;i++){
//                        Map<String, String> fields = map.get("cart").get(myArray[i]);
//                        itemuuid.add(myArray[i]);
//                        carts.add(new carts(fields.get("name"), fields.get("category"), fields.get("url"), fields.get("rating"), fields.get("price"),fields.get("stock") , fields.get("brand"), fields.get("description"),fields.get("quantity")));
//                        sum+=Double.parseDouble(fields.get("price"))*Double.parseDouble(fields.get("quantity"));
//                    }
//                    cartAdapter test2=new cartAdapter(rootView);
//                    test2.setItem(carts,itemuuid);
//                    shoppingcart.setAdapter(test2);
//                    price.setText(String.valueOf(sum));
//
//                    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
//
//                        @Override
//                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                            Toast.makeText(rootView.getContext(), "on Move", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }
//
//                        @Override
//                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                            Toast.makeText(rootView.getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
//                            //Remove swiped item from list and notify the RecyclerView
//                            int position = viewHolder.getAdapterPosition();
//                            double sum=Double.parseDouble(price.getText().toString());
//                            sum-=Double.parseDouble(map.get("cart").get(myArray[position]).get("price"))* Double.parseDouble(carts.get(position).getQuantity());
//                            carts.remove(position);
//                            itemuuid.remove(position);
//                            ref.child(uid).child("cart").child(myArray[position]).removeValue();
//                            Log.d("quantity",map.get("cart").get(myArray[position]).get("quantity"));
//                            price.setText(String.valueOf(sum));
//                            test2.notifyDataSetChanged();
//                        }
//                    };
//                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//                    itemTouchHelper.attachToRecyclerView(shoppingcart);
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//        Log.d("items", String.valueOf(carts.size()));


        return rootView;
    }
}