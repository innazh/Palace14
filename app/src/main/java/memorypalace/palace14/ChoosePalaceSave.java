package memorypalace.palace14;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;

public class ChoosePalaceSave extends AppCompatActivity {

    String imageName; //The image name of the current palace
    ImageView palaceImg; //The image of the current palace
    TextView addName; //The name of the current palace
    final Context context = this;
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_palace_save);

        //Find things by id
        b = findViewById(R.id.createPalaceBtn);
        addName = findViewById(R.id.palaceChoiceName);
        palaceImg = findViewById(R.id.palaceChoice);

        //Get the image from the previous activity
        Intent intent = getIntent();
        if(intent.hasExtra("ImageName")){
            imageName = intent.getStringExtra("ImageName");
        }
        int resImgID = getResources().getIdentifier(imageName, "drawable", getPackageName());

        //Display the default name of the palace
        if(imageName.compareTo("one")==0) addName.setText("Luxury 1 Bedroom Bungalow");
        else if(imageName.compareTo("two")==0) addName.setText("First Floor Single House");
        else if(imageName.compareTo("three")==0) addName.setText("Standard Bungalow");
        else if (imageName.compareTo("four")==0) addName.setText("First Floor Luxury Single Cottage");

        //Set up buttons' onClick functionality
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater li =  LayoutInflater.from(context);
                View palaceNamePromptView = li.inflate(R.layout.palace_name_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(palaceNamePromptView);
                final EditText userInput = (EditText) palaceNamePromptView.findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        // Create a new palace with the Name and Image
                                        Palace myPalace = new Palace(userInput.getText().toString(),imageName);
                                        PalaceList palaces = new PalaceList();
                                        //test
                                        System.out.println(myPalace.getName());
                                        // Add palace to existing list of Palaces.
                                        palaces.addPalace(myPalace, getApplicationContext());
                                        finish();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();



            }
        });

        //Display the blueprint
        palaceImg.setImageResource(resImgID);
    }
}
