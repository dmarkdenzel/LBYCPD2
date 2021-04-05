package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class registerv1 extends AppCompatActivity {

    private TextView clickabletext;
    public Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerv1);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        clickabletext=findViewById(R.id.signclickabletext);
        signup=findViewById(R.id.signbutton);
        String text="Already have an account? Click Here";
        SpannableString click=new SpannableString(text);
        ClickableSpan heretext=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent=new Intent(registerv1.this,MainActivity.class);
                startActivity(intent);
            }
        };
        click.setSpan(heretext,31,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        clickabletext.setText(click);
        clickabletext.setMovementMethod(LinkMovementMethod.getInstance());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registerv1.this,profilecreation.class);
                startActivity(intent);
            }
        });
    }
}