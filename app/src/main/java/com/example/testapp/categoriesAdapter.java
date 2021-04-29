package com.example.testapp;


import android.media.Image;
import android.net.Uri;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.ViewHolder> {

    private ArrayList<categories> categoryA=new ArrayList<>();
    private View rootView;
    private Fragment current;
    private FragmentTransaction fragmentTransaction;
    public categoriesAdapter(View rootview, Fragment current, FragmentTransaction fragmentTransaction) {
        this.rootView=rootview;
        this.current=current;
        this.fragmentTransaction=fragmentTransaction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categorylist, parent, false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(categoryA.get(position).name);

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference();

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==categoryA.size()-1){
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    String uid=user.getUid();


//                    Fragment currentFragment = getFragmentManager().findFragmentByTag("YourFragmentTag");
//                    Fragment currentFragment = rootView.getActivity().getSupportFragmentManager().findFragmentById(R.id.container);

                    MaterialCardView popup=rootView.findViewById(R.id.addcategorypage);
                    ImageButton close=rootView.findViewById(R.id.returnbuttonaddcategory);
                    Button submit=rootView.findViewById(R.id.submitnewcategory);
                    EditText name=rootView.findViewById(R.id.nameofnewcategory);
                    ProgressBar progress=rootView.findViewById(R.id.progressbarnewcategory);
                    FloatingActionButton picselect=rootView.findViewById(R.id.picselectnewcategory);
                    ImageView image=rootView.findViewById(R.id.newcategoryimage);

                    popup.setVisibility(View.VISIBLE);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup.setVisibility(View.GONE);
                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            categoryA.add(0, new categories(name.getText().toString(),"na"));

                            ref.child(uid).child("categories").child(name.getText().toString()).child("url").setValue("na");
                            name.setText("");
                            notifyDataSetChanged();

                            fragmentTransaction.detach(current);
                            fragmentTransaction.attach(current);
                            fragmentTransaction.commit();
                            popup.setVisibility(View.GONE);
                        }
                    });

                }else{
                    Log.d("position",String.valueOf(position));
                }
            }
        });

        if(categoryA.get(position).getUrl().equals("available")){
            if(categoryA.get(position).name.equals("Click To Add Category")){
                storageReference.child("imageholders/add.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(rootView.getContext())
                                .load(uri.toString().trim())
                                .into(holder.picture);
                    }
                });
            }else{
                storageReference.child("image3/"+uid+"/"+categoryA.get(position).name+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(rootView.getContext())
                                .load(uri.toString().trim())
                                .into(holder.picture);
                    }
                });
//                Log.d("loc","image3/"+uid+"/"+categoryA.get(position).name+".png");
            }
        }

    }

    @Override
    public int getItemCount() {
        return categoryA.size();
    }

    public void setCategory(ArrayList<categories> categoryA) {
        this.categoryA = categoryA;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView picture;
        private MaterialCardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameCategory);
            picture=itemView.findViewById(R.id.imageCategory);
            parent=itemView.findViewById(R.id.imageCategoryHolder);
        }
    }

}
