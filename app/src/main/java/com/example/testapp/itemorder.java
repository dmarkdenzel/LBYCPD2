package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

public class itemorder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemorder);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        items item=getIntent().getParcelableExtra("item_profile");

        TextView itemordername=findViewById(R.id.itemordername);
        TextView categorey=findViewById(R.id.itemordercategory);
        TextView description=findViewById(R.id.descriptionitemorder);
        ImageView imageorder=findViewById(R.id.imageitemorder);
        TextView stock=findViewById(R.id.actualstock);
        TextView brand=findViewById(R.id.actualbrand);
        TextView price=findViewById(R.id.actualprice);
        TextView rating=findViewById(R.id.actualrating);
        ImageButton returnbutton=findViewById(R.id.returnbutton);

        itemordername.setText(item.getName());
        stock.setText(item.getStock().toString());
        brand.setText(item.getBrand());
        categorey.setText(item.getCategory());
        description.setText(item.getDescription());
        rating.setText(item.getRating());
        description.setMovementMethod(new ScrollingMovementMethod());
        price.setText(item.getPrice());


        Glide.with(getBaseContext()).asBitmap().load((item.getUrl())).into(imageorder);
        MaterialCardView card=findViewById(R.id.collapsingcard);
        ImageButton arrow=findViewById(R.id.clickbutton);
        RelativeLayout hidden=findViewById(R.id.nonvisible);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidden.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(card,
                            new AutoTransition());
                    hidden.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_uparrow);
                }

                else {
                    TransitionManager.beginDelayedTransition(card,
                            new AutoTransition());
                    hidden.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_downarrow);
                }
            }
        });

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }
}