package memorypalace.palace14;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

        // This defines your touch listener


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

        //Function that initializes all the variables
        init();

        //Display the right blueprint on the screen
        int resImgID = getResources().getIdentifier(palaceClicked.getImagePath(), "drawable", getPackageName());
        myPalaceDetailImg.setImageResource(resImgID);

        // Assign the touch listener to your view which you want to move
        objStool.setOnTouchListener(new MyTouchListener());
        objBarStool.setOnTouchListener(new MyTouchListener());
        objBookshelf.setOnTouchListener(new MyTouchListener());
        objDinningSet.setOnTouchListener(new MyTouchListener());
    }

    //Create a class file for this
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    public void init(){
        //Initialize the blueprint and objects:
        myPalaceDetailImg = findViewById(R.id.myPalaceImg);
        objStool = findViewById(R.id.objectStool);
        objBarStool = findViewById(R.id.objectBarstool);
        objDinningSet = findViewById(R.id.objectDiningset);
        objBookshelf = findViewById(R.id.objectBookShelf);

        mTextMessage = (TextView) findViewById(R.id.message);

        //Initialize the bottom navigation menu
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent = getIntent();

        //Get data from the received intent:
        Bundle bundle = intent.getBundleExtra("list");
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("position");
        //Get position of the palace clicked on the list
        palaceClicked = listOfMyPalaces.getPalace(palacePosition);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }
}
