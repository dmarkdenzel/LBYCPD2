package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.Set;

public class itemorder extends AppCompatActivity implements View.OnClickListener {
    public TextView itemordername,categorey,description,stock,brand,price,rating,quantity;
    public ImageButton inc,dec,returnbutton,arrow;
    public Button addtocart;
    public ImageView imageorder;
    public items item;
    public String itemuuid;
    public FirebaseStorage storage;
    public StorageReference storageReference;
    public MaterialCardView card;
    private DatabaseReference ref;
    private String uid;
    private FirebaseUser user;
    RelativeLayout hidden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemorder);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        item=getIntent().getParcelableExtra("item_profile");
        itemuuid=getIntent().getStringExtra("item_uuid");
        itemordername=findViewById(R.id.itemordername);
        categorey=findViewById(R.id.itemordercategory);
        description=findViewById(R.id.descriptionitemorder);
        stock=findViewById(R.id.actualstock);
        brand=findViewById(R.id.actualbrand);
        price=findViewById(R.id.actualprice);
        user= FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("users");
        uid=user.getUid();
        rating=findViewById(R.id.actualrating);
        quantity=findViewById(R.id.itemcount);
        imageorder=findViewById(R.id.imageitemorder);

        inc=findViewById(R.id.quantityleft);
        dec=findViewById(R.id.quantityright);
        returnbutton=findViewById(R.id.returnbutton);
        arrow=findViewById(R.id.clickbutton);
        addtocart=findViewById(R.id.addtocartbutton);

        inc.setOnClickListener(this);
        dec.setOnClickListener(this);
        returnbutton.setOnClickListener(this);
        arrow.setOnClickListener(this);
        addtocart.setOnClickListener(this);
        itemordername.setText(item.getName());
        stock.setText(item.getStock());
        brand.setText(item.getBrand());
        categorey.setText(item.getCategory());
        description.setText(item.getDescription());
        rating.setText(item.getRating());
        description.setMovementMethod(new ScrollingMovementMethod());
        price.setText(item.getPrice());

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        storageReference.child("images2/"+itemuuid+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getBaseContext())
                        .load(uri.toString().trim())
                        .into(imageorder);
            }
        });

        card=findViewById(R.id.collapsingcard);
        hidden=findViewById(R.id.nonvisible);
    }

    @Override
    public void onClick(View v) {
        int currentquantity=Integer.parseInt(quantity.getText().toString());
        switch (v.getId()){
            case R.id.quantityleft:
                quantity.setText(currentquantity<9?"0"+String.valueOf(currentquantity+1):String.valueOf(currentquantity+1));
                break;
            case R.id.quantityright:
                quantity.setText(currentquantity<3?"01":currentquantity<11?"0"+String.valueOf(currentquantity-1):String.valueOf(currentquantity-1));
                break;
            case R.id.addtocartbutton:
                finish();

                ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map <String,Map<String,String>> subTotalVal = (Map)snapshot.getValue();
                        double currentTotal=Double.parseDouble(subTotalVal.get("cart").get("subtotal"));
                        double priceToAdd=Double.parseDouble(item.getPrice())*currentquantity;
                        ref.child(uid).child("cart").child("subtotal").setValue(String.valueOf(currentTotal+priceToAdd));

                        if(snapshot.child("cart").hasChild("items")){
                            Map <String,Map<String,Map<String,Map<String,String>>>> map = (Map)snapshot.getValue();
                            if(map.get("cart").get("items").containsKey(itemuuid)){
                                int quantityInDatabase=Integer.parseInt(map.get("cart").get("items").get(itemuuid).get("quantity"));
                                ref.child(uid).child("cart").child("items").child(itemuuid).child("quantity").setValue(String.valueOf(currentquantity+quantityInDatabase));
                            }else {
                                ref.child(uid).child("cart").child("items").child(itemuuid).child("quantity").setValue(String.valueOf(currentquantity));
                            }
                        }else{
                            ref.child(uid).child("cart").child("items").child(itemuuid).child("quantity").setValue(String.valueOf(currentquantity));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return;
            case R.id.returnbutton:
                finish();
                return;
            case R.id.clickbutton:
                if (hidden.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    hidden.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_uparrow);
                }
                else {
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    hidden.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_downarrow);
                }
                break;
            default:
                break;
        }
    }
}