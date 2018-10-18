package memorypalace.palace14;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;

public class MyPalaceDetail extends AppCompatActivity {

    PalaceList listOfMyPalaces;
    Palace palaceClicked;
    private TextView mTextMessage;
    private ImageView myPalaceDetailImg; //ImageView for Palace Img
    private ImageView objStool, objBarStool, objDinningSet, objBookshelf; //ImageViews for objects
    private int palacePosition;

    //Bottom navigation menu listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_create_path:
                    mTextMessage.setText(R.string.create_path);
                    return true;
                case R.id.navigation_rename_palace:
                    mTextMessage.setText(R.string.rename_palace);
                    return true;
                case R.id.navigation_delete_palace:
                    mTextMessage.setText(R.string.delete_palace);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_palace_detail);

        //Initialize variables:
        myPalaceDetailImg = findViewById(R.id.myPalaceImg);
        objStool = findViewById(R.id.objectStool);
        objBarStool = findViewById(R.id.objectBarstool);
        objDinningSet = findViewById(R.id.objectDiningset);
        objBookshelf = findViewById(R.id.objectBookShelf);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();

        //Get data from the received intent:
        //Get position of the palace clicked on the list
        Bundle bundle = intent.getBundleExtra("list");
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("position");

        palaceClicked = listOfMyPalaces.getPalace(palacePosition);

        //Display the right blueprint on the screen
        int resImgID = getResources().getIdentifier(palaceClicked.getImagePath(), "drawable", getPackageName());
        myPalaceDetailImg.setImageResource(resImgID);
    }

}
