package com.example.team14.memorypalace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChoosePalace extends AppCompatActivity {

    ImageView image1, image2, image3, image4;
    TextView text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_palace);

        //Find all the images in XML by id
        image1 = findViewById(R.id.template1Image);
        image2 = findViewById(R.id.template2Image);
        image3 = findViewById(R.id.template3Image);
        image4 = findViewById(R.id.template4Image);

        //Find all the TextViews in XML by id
        text1 = findViewById(R.id.namenamename);
        text2 = findViewById(R.id.temp2Name);
        text3 = findViewById(R.id.temp3Name);
        text4 = findViewById(R.id.temp4Name);
        text1.setText("Name!!!!!!!!!!!111");

        //Set all the images to the images in drawable
        image1.setImageResource(R.drawable.amelie);
        image2.setImageResource(R.drawable.amelie);
        image3.setImageResource(R.drawable.amelie);
        image4.setImageResource(R.drawable.amelie);
    }
}
