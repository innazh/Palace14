package memorypalace.palace14;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public enum toolList {objStool, objBarStool, objDinningSet, objBookshelf}

    toolList toolChoice;
    ObjectDragListener lol;
    PalaceList listOfMyPalaces;
    Palace palaceClicked;
    private TextView mTextMessage;
    private ImageView myPalaceDetailImg; //ImageView for Palace Img
    private ImageView objStool, objBarStool, objDinningSet, objBookshelf; //ImageViews for objects
    private int palacePosition, objectNumber;
    private Button routeListBtn;
    private Object_assoc newObj;
//

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
        } catch (RuntimeException e) {
        }

        /*if (lol.getGlobalRes() && lol.objectRet() != null) {
            //currentPalace.addObject(lol.objectRet());
            System.out.println("WILL THIS EVER HAPPEN?");
        }*/
    }

    private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initDraggingListening() {
        lol = new ObjectDragListener(this, palaceClicked, listOfMyPalaces);
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

        routeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPalaceDetail.this, RouteListView.class);
                //Create a bundle to pass a PalaceList as an extra to the new activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("palaceList", listOfMyPalaces);

                //Pass in the position of a clicked palace
                bundle.putInt("position", palacePosition);

                intent.putExtra("list", bundle);
                startActivity(intent);
            }
        });

        //Determine what happens if the blueprint is clicked
        myPalaceDetailImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (toolChoice != null) {
                        final EditText objName, objDesc;
                        Button saveBtn, cancelBtn, snapBtn;
                        final Dialog myDialog;
                        myDialog = new Dialog(MyPalaceDetail.this);
                        final float endX = (int) event.getX();
                        final float endY = (int) event.getY();

                        myDialog.setContentView(R.layout.add_object_pop_up);
                        myDialog.setCancelable(false);

                        objName = myDialog.findViewById(R.id.objectNameInput);
                        objDesc = myDialog.findViewById(R.id.objectDescInput);

                        // Set a unique identifier for the object, this may not work as newObj, can be null when viewing a palace with existing objects,
                        // Without creating new objects



                        // Set the key of Open Object

                        // Check for an object that is open
                        System.out.println("SCOTT BLYAT");

                        saveBtn = myDialog.findViewById(R.id.saveBtnObjInpt);
                        cancelBtn = myDialog.findViewById(R.id.cancelBtnObjInpt);
//                        snapBtn=myDialog.findViewById(R.id.sn);

                        myDialog.show();

                        // SAVES THE OBJECT
                        saveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //declared final for the img onclick
                                //final Object_assoc newObj;

                                RelativeLayout myLayout = findViewById(R.id.relativeLayoutPalaceDetail);
                                final ImageView newObjImgView = new ImageView(MyPalaceDetail.this);
                                newObjImgView.setElevation(7); //Why does it have to be 7? LOL

                                switch (toolChoice) {
                                    case objBarStool:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(), "barstool", "barstool.png", endX, endY);
                                        newObj.setO_identifier(palaceClicked.getValidIdentifier());
                                        palaceClicked.addObject(newObj);
                                        System.out.println("Key: " + newObj.get_identifier());
                                        newObjImgView.setImageResource(R.drawable.barstool);
                                        break;

                                    case objBookshelf:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(), "bookcase", "bookcase.png", endX, endY);
                                        newObj.setO_identifier(palaceClicked.getValidIdentifier());
                                        palaceClicked.addObject(newObj);
                                        System.out.println("Key: " + newObj.get_identifier());
                                        newObjImgView.setImageResource(R.drawable.bookcase);
                                        break;

                                    case objDinningSet:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(), "diningset", "diningset.png", endX, endY);
                                        newObj.setO_identifier(palaceClicked.getValidIdentifier());
                                        palaceClicked.addObject(newObj);
                                        System.out.println("Key: " + newObj.get_identifier());
                                        newObjImgView.setImageResource(R.drawable.diningset);
                                        break;

                                    case objStool:
                                        newObj = new Object_assoc(objName.getText().toString(), objDesc.getText().toString(), "stool", "stool.png", endX, endY);
                                        newObj.setO_identifier(palaceClicked.getValidIdentifier());
                                        palaceClicked.addObject(newObj);
                                        System.out.println("Key: " + newObj.get_identifier());
                                        newObjImgView.setImageResource(R.drawable.stool);
                                        break;

                                    default:
                                        newObj = new Object_assoc("", "", "", "", endX, endY);
                                        newObj.setO_identifier(palaceClicked.getValidIdentifier());
                                        System.out.println("Key: " + newObj.get_identifier());
                                        break;
                                }
                                //Set the new coordinates
                                newObjImgView.setX(endX + 50);
                                newObjImgView.setY(endY - 50);
                                //Set layout of the new Object ImageView and add it to the current layout
                                newObjImgView.setLayoutParams(new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.imageview_obj_width), (int) getResources().getDimension(R.dimen.imageview_obj_height)));
                                myLayout.addView(newObjImgView);

                                //keyOfOpenObject = newObj.get_identifier();
                                System.out.println("Set Key: " + newObj.get_identifier());

                                /*This code is needed for the following case:
                                We're looking at the blueprint and decide to add and object.
                                After we've added the object, we want to modify it without exiting the palace view*/

                                newObjImgView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Dialog myDialog;

                                        myDialog = new Dialog(MyPalaceDetail.this);

                                        // Set the identifier of the object that has been opened

                                        myDialog.setContentView(R.layout.view_existing_obj_pop_up);
                                        myDialog.setCancelable(false);

                                        //Get all the fields from the XML file
                                        //final ImageView clickedObjImage = myDialog.findViewById(R.id.clickedObjImage);
                                        final EditText clickedObjName = myDialog.findViewById(R.id.clickedObjName);
                                        final EditText clickedObjDesc = myDialog.findViewById(R.id.clickedObjDesc);
                                        Button clickedsaveBtn = myDialog.findViewById(R.id.editClickedObjSave);
                                        Button clickedcancelBtn = myDialog.findViewById(R.id.editClickedObjCancel);
                                        Button clickeddeleteBtn = myDialog.findViewById(R.id.clickedObjDeleteUnique);

                                        //Set the data fields
                                        clickedObjName.setText(objName.getText().toString());
                                        clickedObjDesc.setText(objDesc.getText().toString());
                                        //clickedObjImage.setImageBitmap(palaceClicked.getObject(objectNumber).getMemory());

                                        myDialog.show();

                                        clickedsaveBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Change the object's data to the data in the XML data fields
                                                newObj.setO_name(clickedObjName.getText().toString());
                                                newObj.setO_desc(clickedObjDesc.getText().toString());
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
                                                int pos = palaceClicked.findObject(newObj);
                                                palaceClicked.removeObject(pos);
                                                listOfMyPalaces.writePalacesFile(MyPalaceDetail.this);

                                                //Remove the current picture
                                                //Note*: Can we 'remove' the imageView from the layout?
                                                newObjImgView.setVisibility(View.GONE);

                                                myDialog.cancel();
                                            }
                                        });

                                    }
                                });
                                /////////////////////////////////////////////////////////////////////////////////////////////////

                                //Write objects to the file
                                listOfMyPalaces.writePalacesFile(MyPalaceDetail.this);

                                myDialog.cancel();
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

    public void init() {
        //Initialize the blueprint and objects:
        myPalaceDetailImg = findViewById(R.id.myPalaceImg);
        objStool = findViewById(R.id.objectStool);
        objBarStool = findViewById(R.id.objectBarstool);
        objDinningSet = findViewById(R.id.objectDiningset);
        objBookshelf = findViewById(R.id.objectBookShelf);
        routeListBtn = findViewById(R.id.viewRouteListBtn);

        objStool.setTag("stool.png");
        objBarStool.setTag("barstool.png");
        objBookshelf.setTag("bookcase.png");
        objDinningSet.setTag("diningset.png");

        mTextMessage = (TextView) findViewById(R.id.message);

        //Initialize the bottom navigation menu
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        final Intent intent = getIntent();

        //Get data from the received intent:
        Bundle bundle = intent.getBundleExtra("list");
        listOfMyPalaces = (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("position");
        //Get position of the palace clicked on the list
        palaceClicked = listOfMyPalaces.getPalace(palacePosition);

        //Display the right blueprint on the screen
        int resImgID = getResources().getIdentifier(palaceClicked.getImageName(), "drawable", getPackageName());
        myPalaceDetailImg.setImageResource(resImgID);

        System.out.println("number of objects: " + palaceClicked.getListLength());

        // Set the coordinates of any moved Objects
        if (palaceClicked.getListLength() > 0) {

            //Iterate through the existent objects and place the image views accordingly
            for (int i = 0; i < palaceClicked.getListLength(); i++) {
                //final int objectNumber=i;
                objectNumber = i;
                final RelativeLayout thisLayout = findViewById(R.id.relativeLayoutPalaceDetail);
                final ImageView existentImageView = new ImageView(MyPalaceDetail.this);

                //Set the right picture
                if (palaceClicked.getObject(i).getView_tag().compareTo("barstool.png") == 0)
                    existentImageView.setImageResource(R.drawable.barstool);
                else if (palaceClicked.getObject(i).getView_tag().compareTo("diningset.png") == 0)
                    existentImageView.setImageResource(R.drawable.diningset);
                else if (palaceClicked.getObject(i).getView_tag().compareTo("stool.png") == 0)
                    existentImageView.setImageResource(R.drawable.stool);
                else if (palaceClicked.getObject(i).getView_tag().compareTo("bookcase.png") == 0)
                    existentImageView.setImageResource(R.drawable.bookcase);

                //Put the object on top
                existentImageView.setElevation(7); //Why does it have 7? LOL

                //Set the new coordinates
                existentImageView.setX(palaceClicked.getObject(i).getO_Xcoordinate() + 50);
                existentImageView.setY(palaceClicked.getObject(i).getO_Ycoordinate() - 50);

                //Onclick listener on the existent object
                existentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog myDialog;
                        myDialog = new Dialog(MyPalaceDetail.this);

                        myDialog.setContentView(R.layout.view_existing_obj_pop_up);
                        myDialog.setCancelable(false);

                        //Get all the fields from the XML file
                        final EditText clickedObjName = myDialog.findViewById(R.id.clickedObjName);
                        final EditText clickedObjDesc = myDialog.findViewById(R.id.clickedObjDesc);
                        Button clickedsaveBtn = myDialog.findViewById(R.id.editClickedObjSave);
                        Button clickedcancelBtn = myDialog.findViewById(R.id.editClickedObjCancel);
                        Button clickeddeleteBtn = myDialog.findViewById(R.id.clickedObjDeleteUnique);


                        //Set the data fields
                        clickedObjName.setText(palaceClicked.getObject(objectNumber).getName());
                        clickedObjDesc.setText(palaceClicked.getObject(objectNumber).getDesc());

                        newObj = palaceClicked.getObject(objectNumber);

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

    protected void changeLocation(View locToChange, float x, float y, float startX, float startY) {


        TranslateAnimation animation = new TranslateAnimation(startX, x, startY, y - 550);
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
                    Intent intent = new Intent(MyPalaceDetail.this, CreateRoute.class);
                    //Create a bundle to pass a PalaceList as an extra to the new activity
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("palaceList", listOfMyPalaces);

                    //Pass in the position of a clicked palace
                    bundle.putInt("position", palacePosition);

                    intent.putExtra("list", bundle);
                    startActivity(intent);

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


  //  public int keyOfOpenObject;
//
//    // For taking images
//    private final int REQUEST_IMAGE_CAPTURE = 101;
//
//    public void takePicture(View view) {
//        System.out.println("wayat");
//        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        System.out.println("wayat");
//        System.out.println("wayatKEY: " + keyOfOpenObject);
//        if (imageTakeIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
//        }
//        else{
//            System.out.println("wayat not working");
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            // Need to set image of Object here to object.
//            // Any way to know which object is currently open?
//
//            System.out.println("Crash Check 1");
//
//            // newObj is a nullpointer, We need to track which object is open in the dialog, but how???????
//            //newObj = new Object_assoc();
//
//
//            //System.out.println("ObjName: " + newObj.getName()+ "ObjDesc" + newObj.getDesc());
//
//            System.out.println("Crash Check 2");
//            System.out.println("Current key: " + keyOfOpenObject);
//            //newObj = palaceClicked.getObjectByKey(keyOfOpenObject);
//
//            System.out.println("Crash Check 2.5 ");
//
//            newObj.setO_memory(imageBitmap );
//
//            System.out.println("Crash Check 3");
//
//            setContentView(R.layout.view_existing_obj_pop_up);
//
//            System.out.println("Crash Check 4");
//
//            final ImageView mImageView = findViewById(R.id.clickedObjImage);
//
//            //System.out.println("This one ACTIVITY");
//            // Write object to Palace File
//            listOfMyPalaces.writePalacesFile(MyPalaceDetail.this);
//
//            // Set image
//            mImageView.setImageBitmap(imageBitmap);
//
//            // Sets the current view back to the palace detail(Unsuccessful though)
//            //setContentView(R.layout.activity_my_palace_detail);
//            // Resets the Dialog to include the image that has been taken with the camera
//
//            //init();
//            //initDraggingListening();
//
//
//
//        }
//    }