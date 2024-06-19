package com.example.loginscreen.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginscreen.R;

public class Faq extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.faq_layout);


        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        TextView text3 = findViewById(R.id.text3);
        TextView text4 = findViewById(R.id.text4);
        TextView text5 = findViewById(R.id.text5);
        TextView text6 = findViewById(R.id.text6);
        TextView text7 = findViewById(R.id.text7);
        TextView text8 = findViewById(R.id.text8);
        TextView text9 = findViewById(R.id.text9);
        TextView text10 = findViewById(R.id.text10);

        ImageView button1 = findViewById(R.id.button1);
        ImageView button2 = findViewById(R.id.button2);
        ImageView button3 = findViewById(R.id.button3);
        ImageView button4 = findViewById(R.id.button4);
        ImageView button5 = findViewById(R.id.button5);
        ImageView button6 = findViewById(R.id.button6);
        ImageView button7 = findViewById(R.id.button7);
        ImageView button8 = findViewById(R.id.button8);
        ImageView button9 = findViewById(R.id.button9);
        ImageView button10 = findViewById(R.id.button10);
        Button button11 = findViewById(R.id.button11);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getVisibility() == View.GONE){
                    text1.setVisibility(View.VISIBLE);
                    button1.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text1.setVisibility(View.GONE);
                    button1.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text2.getVisibility() == View.GONE){
                    text2.setVisibility(View.VISIBLE);
                    button2.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text2.setVisibility(View.GONE);
                    button2.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text3.getVisibility() == View.GONE){
                    text3.setVisibility(View.VISIBLE);
                    button3.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text3.setVisibility(View.GONE);
                    button3.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text4.getVisibility() == View.GONE){
                    text4.setVisibility(View.VISIBLE);
                    button4.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text4.setVisibility(View.GONE);
                    button4.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text5.getVisibility() == View.GONE){
                    text5.setVisibility(View.VISIBLE);
                    button5.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text5.setVisibility(View.GONE);
                    button5.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text6.getVisibility() == View.GONE){
                    text6.setVisibility(View.VISIBLE);
                    button6.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text6.setVisibility(View.GONE);
                    button6.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text7.getVisibility() == View.GONE){
                    text7.setVisibility(View.VISIBLE);
                    button7.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text7.setVisibility(View.GONE);
                    button7.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text8.getVisibility() == View.GONE){
                    text8.setVisibility(View.VISIBLE);
                    button8.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text8.setVisibility(View.GONE);
                    button8.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text9.getVisibility() == View.GONE){
                    text9.setVisibility(View.VISIBLE);
                    button9.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text9.setVisibility(View.GONE);
                    button9.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text10.getVisibility() == View.GONE){
                    text10.setVisibility(View.VISIBLE);
                    button10.setImageResource(R.drawable.ic_arrow_upward);
                }else {
                    text10.setVisibility(View.GONE);
                    button10.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
