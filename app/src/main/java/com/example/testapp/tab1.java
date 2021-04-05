package com.example.testapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class tab1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        RecyclerView stores= rootView.findViewById(R.id.storeslist);
        RecyclerView hotitems= rootView.findViewById(R.id.hotitemslist);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 =new LinearLayoutManager(getActivity());
        stores.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        hotitems.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<stores> store=new ArrayList<>();
        store.add(new stores("SM","https://pngimage.net/wp-content/uploads/2018/06/sm-mall-logo-png-7.png","4","Quezon City"));
        store.add(new stores("PURE GOLD","https://www.clickthecity.com/img/restaurants/original/5747.jpg","5","Quezon City"));
        store.add(new stores("WALTER MART","https://assets.bossjob.com/companies/6830/logo/tcX2I61NBjgU5vaDTl00cOHspHsKmhca2EWenldp.png","3","Quezon City"));
        store.add(new stores("LANDMARK","https://static1.eyellowpages.ph/uploads/yp_business/photo/20364/thumb_The_LandMark_Logo.gif","2","Malabon City"));
        store.add(new stores("ROBINSONS","https://upload.wikimedia.org/wikipedia/en/f/ff/Robinsons_Supermarket_logo.jpg","4","Malabon City"));
        store.add(new stores("SUPER 8","https://ph.top10place.com/img_files/294917943966629","3","Malabon City"));

        ArrayList<items> item=new ArrayList<>();
        item.add(new items("Cupcakes","Bakery Goods","https://randomwordgenerator.com/img/picture-generator/52e1d64a435aae14f1dc8460962e33791c3ad6e04e507441722a72dd904ac1_640.jpg","4"));
        item.add(new items("Tomato","Fresh Produce","https://www.generatorslist.com/public/img/misc/objects/tomato.jpg","5"));
        item.add(new items("Soap","Hygiene","https://www.generatorslist.com/public/img/misc/objects/soap.jpg","3"));
        item.add(new items("Coloring Pencil","Stationary","https://www.generatorslist.com/public/img/misc/objects/colored-pencil.jpg","5"));
        item.add(new items("Gloves","Cleaning","https://www.generatorslist.com/public/img/misc/objects/glove.jpg","1"));
        item.add(new items("Controller","Gaming","https://www.generatorslist.com/public/img/misc/objects/controller.jpg","2"));
        item.add(new items("Butter Knife","Kitchen","https://www.generatorslist.com/public/img/misc/objects/knife.jpg","5"));
        item.add(new items("Hand Drill","Hardware","https://www.generatorslist.com/public/img/misc/objects/drill.jpg","3"));
        item.add(new items("Needle","Sewing","https://www.generatorslist.com/public/img/misc/objects/needle.jpg","4"));
        item.add(new items("Apron","Kitchen","https://www.generatorslist.com/public/img/misc/objects/apron.jpg","2"));


        storeAdapter test=new storeAdapter(rootView.getContext());
        itemAdapter test2=new itemAdapter(rootView.getContext());
        test.setStore(store);
        test2.setItem(item);

        stores.setAdapter(test);
        hotitems.setAdapter(test2);


        return rootView;
    }
}