package com.example.testapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Map;
import java.util.Set;


public class tab3Seller extends Fragment {

    private FirebaseUser user;
    private DatabaseReference ref;
    private String uid;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private RecyclerView categorylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab3_seller, container, false);
        categorylist=rootView.findViewById(R.id.categorycontainer);

        user= FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("users");
        uid=user.getUid();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragSeller);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        GridLayoutManager layoutManager =new GridLayoutManager(getActivity(),2);
        categorylist.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<categories> category= new ArrayList<>();

                category.add(new categories("Click To Add Category","available"));

                if(snapshot.child("categories").exists()){
                    Map<String, Map<String,String>> map= (Map)snapshot.child("categories").getValue();
                    Set<String> test=map.keySet();
                    String[] myArray = new String[test.size()];
                    test.toArray(myArray);
                    for(int i=0;i<myArray.length;i++){
                        category.add(0,new categories(myArray[i],map.get(myArray[i]).get("url")));
                    }
                }

                categoriesAdapter cartAd=new categoriesAdapter(rootView,currentFragment,fragmentTransaction);
                cartAd.setCategory(category);
                cartAd.notifyDataSetChanged();

                categorylist.setAdapter(cartAd);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }
}