package com.example.team14.memorypalace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ChoosePalace extends AppCompatActivity {

    ImageView image1, image2, image3, image4;
    TextView text1, text2, text3, text4;
    TableRow row1, row2, row3, row4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_palace);

        //Find all table rows by id
        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row3 = findViewById(R.id.row3);
        row4 = findViewById(R.id.row4);

        //Find all the images in XML by id
        image1 = findViewById(R.id.template1Image);
        image2 = findViewById(R.id.template2Image);
        image3 = findViewById(R.id.template3Image);
        image4 = findViewById(R.id.template4Image);

        //Find all the TextViews in XML by id
        text1 = findViewById(R.id.temp1Name);
        text2 = findViewById(R.id.temp2Name);
        text3 = findViewById(R.id.temp3Name);
        text4 = findViewById(R.id.temp4Name);

        //Set all the images to the images in drawable
        image1.setImageResource(R.drawable.one);
        image2.setImageResource(R.drawable.two);
        image3.setImageResource(R.drawable.three);
        image4.setImageResource(R.drawable.four);

        //Make all the rows clickable
        row1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePalace.this, ChoosePalaceSave.class);
                intent.putExtra("ImageName", "one");
                startActivity(intent);
            }
        });

        row2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePalace.this, ChoosePalaceSave.class);
                intent.putExtra("ImageName", "two");
                startActivity(intent);
            }
        });

        row3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePalace.this, ChoosePalaceSave.class);
                intent.putExtra("ImageName", "three");
                startActivity(intent);
            }
        });

        row4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePalace.this, ChoosePalaceSave.class);
                intent.putExtra("ImageName", "four");
                startActivity(intent);
            }
        });
    }


}
