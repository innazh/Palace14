package memorypalace.palace14;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import memorypalace.palace14.classes.ObjectDragListener;
import memorypalace.palace14.classes.Object_assoc;
import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;

public class MyPalaceDetail extends AppCompatActivity {

    public enum toolList{objStool, objBarStool, objDinningSet, objBookshelf};

    toolList toolChoice;
    ObjectDragListener lol;
    PalaceList listOfMyPalaces;
    Palace palaceClicked;
    private TextView mTextMessage;
    private ImageView myPalaceDetailImg; //ImageView for Palace Img
    private ImageView objStool, objBarStool, objDinningSet, objBookshelf; //ImageViews for objects
    private int palacePosition;
    //private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_palace_detail);

        //Function that initializes all the variables
        init();
        //Sets up all the dragging and dropping functionality
        initDraggingListening();

      /*  homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyPalaceDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        try {
            if (lol.getGlobalRes()) {
                this.listOfMyPalaces.writePalacesFile(this);
                lol.setGlobalRes(false);
            }
        }
        catch (RuntimeException e){}

        /*if (lol.getGlobalRes() && lol.objectRet() != null) {
            //palaceClicked.addObject(lol.objectRet());
            System.out.println("WILL THIS EVER HAPPEN?");
        }*/
    }

    private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }

    public void initDraggingListening(){
        lol = new ObjectDragListener(this, palaceClicked,listOfMyPalaces);
        //Sets the target for the dropping action
        findViewById(R.id.myPalaceImg).setOnDragListener(lol);
        // Assign Tags to objects for creation of ClipData Objects.
        objStool.setTag("stool.png");
        objBarStool.setTag("barstool.png");
        objBookshelf.setTag("bookcase.png");
        objDinningSet.setTag("diningset.png");

        //Select a tool. (Object to place)
        objStool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolChoice = toolList.objStool;
            }
        });
        objBarStool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolChoice = toolList.objBarStool;
            }
        });
        objBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolChoice = toolList.objBookshelf;
            }
        });
        objDinningSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolChoice = toolList.objDinningSet;
            }
        });

        //Determine what happens if the blueprint is clicked
        myPalaceDetailImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    if(toolChoice!=null) {
                        final EditText objName, objDesc;
                        Button saveBtn, cancelBtn;
                        final Dialog myDialog;
                        myDialog = new Dialog(MyPalaceDetail.this);
                        final float endX = (int) event.getX();
                        final float endY = (int) event.getY();

                        myDialog.setContentView(R.layout.add_object_pop_up);
                        myDialog.setCancelable(false);

                        objName=myDialog.findViewById(R.id.objectNameInput);
                        objDesc=myDialog.findViewById(R.id.objectDescInput);
                        saveBtn=myDialog.findViewById(R.id.saveBtnObjInpt);
                        cancelBtn=myDialog.findViewById(R.id.cancelBtnObjInpt);

                        myDialog.show();

                        // SAVES THE OBJECT
                        saveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Object_assoc newObj;
                                RelativeLayout myLayout = findViewById(R.id.relativeLayoutPalaceDetail);
                                ImageView newObjImgView = new ImageView(MyPalaceDetail.this);
                                newObjImgView.setElevation(7); //Why does it have to be 7? LOL

                                switch (toolChoice) {
                                    case objBarStool:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(),"barstool", "barstool.png", endX, endY);
                                        palaceClicked.addObject(newObj);
                                        newObjImgView.setImageResource(R.drawable.barstool);
                                        break;

                                    case objBookshelf:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(),"bookcase", "bookcase.png", endX, endY);
                                        palaceClicked.addObject(newObj);
                                        newObjImgView.setImageResource(R.drawable.bookcase);
                                        break;

                                    case objDinningSet:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(),"diningset", "diningset.png", endX, endY);
                                        palaceClicked.addObject(newObj);
                                        newObjImgView.setImageResource(R.drawable.diningset);
                                        break;

                                    case objStool:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(),"stool", "stool.png", endX, endY);
                                        palaceClicked.addObject(newObj);
                                        newObjImgView.setImageResource(R.drawable.stool);
                                        break;

                                    default:
                                        System.out.println("........................");
                                        break;
                                }
                                //Set the new coordinates
                                newObjImgView.setX(endX+50);
                                newObjImgView.setY(endY-50);
                                //Set layout of the new Object ImageView and add it to the current layout
                                newObjImgView.setLayoutParams(new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.imageview_obj_width), (int) getResources().getDimension(R.dimen.imageview_obj_height)));
                                myLayout.addView(newObjImgView);

                                //Write objects to the file
                                listOfMyPalaces.writePalacesFile(MyPalaceDetail.this);

                                myDialog.cancel();
//                                // For loop to check for added objects.
//                                for(int i = 0; i < palace.getListLength(); i++){
//                                    System.out.println("E1 Object Name: " + palace.getObject(i).getName());
//                                    System.out.println("E1 Object Desc: " + palace.getObject(i).getDesc());
//                                    System.out.println("E1 Object x: " + palace.getObject(i).getO_Xcoordinate());
//                                    System.out.println("E1 Object y: " + palace.getObject(i).getO_Ycoordinate());
//                                }
                            }
                        });

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.cancel();
                            }
                        });
                    }
                }
                return false;
            }
        });
    }

    public void init(){
        //Initialize the blueprint and objects:
        myPalaceDetailImg = findViewById(R.id.myPalaceImg);
        objStool = findViewById(R.id.objectStool);
        objBarStool = findViewById(R.id.objectBarstool);
        objDinningSet = findViewById(R.id.objectDiningset);
        objBookshelf = findViewById(R.id.objectBookShelf);

        objStool.setTag("stool.png");
        objBarStool.setTag("barstool.png");
        objBookshelf.setTag("bookcase.png");
        objDinningSet.setTag("diningset.png");

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

        System.out.println("number of objects: " + palaceClicked.getListLength());

        // Set the coordinates of any moved Objects
        if(palaceClicked.getListLength() > 0){

            //Iterate through the existent objects and place the image views accordingly
            for(int i = 0; i < palaceClicked.getListLength(); i++){
                final int objectNumber=i;
                final RelativeLayout thisLayout = findViewById(R.id.relativeLayoutPalaceDetail);
                final ImageView existentImageView = new ImageView(MyPalaceDetail.this);

                //Set the right picture
                if(palaceClicked.getObject(i).getView_tag().compareTo("barstool.png")==0)
                    existentImageView.setImageResource(R.drawable.barstool);
                else if(palaceClicked.getObject(i).getView_tag().compareTo("diningset.png")==0)
                    existentImageView.setImageResource(R.drawable.diningset);
                else if(palaceClicked.getObject(i).getView_tag().compareTo("stool.png")==0)
                    existentImageView.setImageResource(R.drawable.stool);
                else if(palaceClicked.getObject(i).getView_tag().compareTo("bookcase.png")==0)
                    existentImageView.setImageResource(R.drawable.bookcase);

                //Put the object on top
                existentImageView.setElevation(7); //Why does it have 7? LOL

                //Set the new coordinates
                existentImageView.setX(palaceClicked.getObject(i).getO_Xcoordinate()+50);
                existentImageView.setY(palaceClicked.getObject(i).getO_Ycoordinate()-50);

                //Onclick listener on the existent object
                existentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog myDialog;
                        myDialog = new Dialog(MyPalaceDetail.this);

                        myDialog.setContentView(R.layout.view_existing_obj_pop_up);
                        myDialog.setCancelable(false);

                        //Get all the fields from the XML file
                        final EditText clickedObjName=myDialog.findViewById(R.id.clickedObjName);
                        final EditText clickedObjDesc=myDialog.findViewById(R.id.clickedObjDesc);
                        Button clickedsaveBtn=myDialog.findViewById(R.id.editClickedObjSave);
                        Button clickedcancelBtn=myDialog.findViewById(R.id.editClickedObjCancel);
                        Button clickeddeleteBtn=myDialog.findViewById(R.id.clickedObjDeleteUnique);

                        //Set the data fields
                        clickedObjName.setText(palaceClicked.getObject(objectNumber).getName());
                        clickedObjDesc.setText(palaceClicked.getObject(objectNumber).getDesc());

                        myDialog.show();

                        clickedsaveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Change the object's data to the data in the XML data fields
                                palaceClicked.getObject(objectNumber).setO_name(clickedObjName.getText().toString());
                                palaceClicked.getObject(objectNumber).setO_desc(clickedObjDesc.getText().toString());
                                //Save objects to the file.
                                listOfMyPalaces.writePalacesFile(MyPalaceDetail.this);
                                myDialog.cancel();
                            }
                        });

                        clickedcancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.cancel();
                            }
                        });

                        clickeddeleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                palaceClicked.removeObject(objectNumber);
                                listOfMyPalaces.writePalacesFile(MyPalaceDetail.this);

                                //Remove the current picture
                                //Note*: Can we 'remove' the imageView from the layout?
                                existentImageView.setVisibility(View.GONE);

                                myDialog.cancel();
                            }
                        });
                    }
                });

                //Set layout of the new Object ImageView and add it to the current layout
                existentImageView.setLayoutParams(new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.imageview_obj_width), (int) getResources().getDimension(R.dimen.imageview_obj_height)));
                thisLayout.addView(existentImageView);
            }

        }
    }

    protected void changeLocation(View locToChange, float x, float y, float startX, float startY){


        TranslateAnimation animation = new TranslateAnimation(startX, x, startY, y-550);
        animation.setDuration(100);
        animation.setFillAfter(true);
        locToChange.startAnimation(animation);

        System.out.println("CURRENT LOC X: " + locToChange.getX());
        //locToChange.setY(y);
        System.out.println("CURRENT LOC X: " + locToChange.getX());
    }

    // creates a dialog for the deletion of a palace
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
