package memorypalace.palace14;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                    showDialog(1);
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
        findViewById(R.id.myPalaceImg).setOnDragListener(new MyDragListener());

        // Assign Tags to objects for creation of ClipData Objects.
        objStool.setTag("STOOL_TAG");
        objBarStool.setTag("BARSTOOL_TAG");
        objBookshelf.setTag("BOOKSHELF_TAG");
        objDinningSet.setTag("DININGSET_TAG");

        // Assign the touch listener to your view which you want to move
        objStool.setOnTouchListener(new MyTouchListener());
        objBarStool.setOnTouchListener(new MyTouchListener());
        objBookshelf.setOnTouchListener(new MyTouchListener());
        objDinningSet.setOnTouchListener(new MyTouchListener());
    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics (Point size, Point touch) {
            // Defines local variables Should be private but ANDROID STUDIO IS A PIECE OF FUCK THAT WONT LET ME.
            int width, height;

            // Sets the width of the shadow to half the width of the original View
            width = getView().getWidth() / 2;

            // Sets the height of the shadow to half the height of the original View
            height = getView().getHeight() / 2;

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {

            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas);
        }
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
    class MyDragListener implements View.OnDragListener {


        ImageView palaceBluePrint;
        ImageView imageToDrag;

        // We need to replace this potentially.

/*         ImageView enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);*/


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            View dragView = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    System.out.println("ACTION_DRAG_STARTED: NAHUY");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    System.out.println("ACTION_DRAG_ENTERED: YOBBA");

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    System.out.println("ACTION_DRAG_EXITED: BLYA");

                    break;
                case DragEvent.ACTION_DROP:
                    System.out.println("ACTION_DROP: GAVNO");
                    float X = event.getX();
                    float Y = event.getY();
                    System.out.println("ACTION_DROP_CURR_X: " + X);
                    System.out.println("ACTION_DROP_CURR_Y: " + Y);
                    //Log.d(LOGCAT, "X " + (int) X + "Y " + (int) Y);
                    View view = (View) event.getLocalState();
                    dragView.setX(X-(view.getWidth()/2));
                    dragView.setY(Y-(view.getHeight()/2));
                        // Api Level was changed from 15 to 21 for this to work.
                    //dragView.setElevation(500);
                    // the Image view itself seems to be readjusting with the dragview
                    // readjusting its own z value, need to find a workaround this.

                    /*  dragView.bringToFront();
                    dragView.invalidate();
                    view.invalidate();*/
                    System.out.println("ACTION_DROP_SET_X: " + dragView.getX());
                    System.out.println("ACTION_DROP_SET_Y: " + dragView.getY());
                    System.out.println("ACTION_DROP_SET_Z: " + dragView.getZ());
                    System.out.println("ACTION_DROP_IMG_Z: " + view.getZ());
                    dragView.setVisibility(View.VISIBLE);

                    // DragView needs to be sent to the front
                    // addView and removeView can be used to manipulate the arrangements
                    // It is likely that the objects are still there however, they
                    // are behind the Palace Image View

                    // Returns true. DragEvent.getResult() will return true.
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    System.out.println("ACTION_DRAG_EXITED: PIZDETS");

                    // Check for drag within the Palace Blueprint.
                    if (!event.getResult()) {
                        // Sets the dragged object to its original place
                        dragView.setVisibility(View.VISIBLE);
                    }
                    // SUBJECT TO CHANGE
                    dragView.setVisibility(View.VISIBLE);
                    System.out.println("ACTION_DROP_FINAL_X: " + dragView.getX());
                    System.out.println("ACTION_DROP_FINAL_Y: " + dragView.getY());
                    break;


                default:
                    break;
            }
            return true;
        }


    }

    private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }


}
