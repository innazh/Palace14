package memorypalace.palace14;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import memorypalace.palace14.classes.ObjectDragListener;
import memorypalace.palace14.classes.ObjectTouchListener;
import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;

public class MyPalaceDetail extends AppCompatActivity {

    ObjectDragListener lol;
    PalaceList listOfMyPalaces;
    Palace palaceClicked;
    private TextView mTextMessage;
    private ImageView myPalaceDetailImg; //ImageView for Palace Img
    private ImageView objStool, objBarStool, objDinningSet, objBookshelf; //ImageViews for objects
    private int palacePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_palace_detail);

        //Function that initializes all the variables
        init();
        //Sets up all the dragging and dropping functionality
        initDraggingListening();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        try {
            if (lol.getGlobalRes()) {
                this.listOfMyPalaces.writePalacesFile(this);
            }
        }
        catch (RuntimeException e){}
    }

    private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }

    public void initDraggingListening(){
        lol = new ObjectDragListener(this, palaceClicked);
        //Sets the target for the dropping action
        findViewById(R.id.myPalaceImg).setOnDragListener(lol);
        // Assign Tags to objects for creation of ClipData Objects.
        objStool.setTag("stool.png");
        objBarStool.setTag("barstool.png");
        objBookshelf.setTag("bookcase.png");
        objDinningSet.setTag("diningset.png");

        // Assign the touch listener to your view which you want to move
        objStool.setOnTouchListener(new ObjectTouchListener());
        objBarStool.setOnTouchListener(new ObjectTouchListener());
        objBookshelf.setOnTouchListener(new ObjectTouchListener());
        objDinningSet.setOnTouchListener(new ObjectTouchListener());
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

        //Display the right blueprint on the screen
        int resImgID = getResources().getIdentifier(palaceClicked.getImageName(), "drawable", getPackageName());
        myPalaceDetailImg.setImageResource(resImgID);
    }

    // creates Dialogs for this Activity
    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
        dialog = new AlertDialog.Builder(this).setMessage(
                "Are you sure you want to delete " + listOfMyPalaces.getPalace(palacePosition).getName() + " ?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println(listOfMyPalaces + "       " + palacePosition);
                                listOfMyPalaces.deletePalace(palacePosition, getApplicationContext());
                                finish();
                                startActivity(new Intent(MyPalaceDetail.this, ViewPalaceList.class));
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();
        return dialog;

    }

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
                    showDialog(1);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(MyPalaceDetail.this, ViewPalaceList.class);
            startActivity(intent);
        }
        return true;
    }

}
