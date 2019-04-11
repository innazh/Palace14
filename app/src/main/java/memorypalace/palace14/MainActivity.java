package memorypalace.palace14;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;

public class MainActivity extends AppCompatActivity {
    Button choosePalace, buildPalace, palaceList;
    private static final int DIALOG_REALLY_EXIT_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choosePalace = findViewById(R.id.choosePalace);
        buildPalace = findViewById(R.id.buildPalace);
        palaceList = findViewById(R.id.palaceList);

        choosePalace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChoosePalace.class);
                startActivity(intent);
            }
        });

        buildPalace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuildPalace.class);
                startActivity(intent);
            }
        });

        palaceList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPalaceList.class);
                startActivity(intent);
            }
        });
    }

    // creates Dialogs for this Activity
    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
                dialog = new AlertDialog.Builder(this).setMessage(
                        "Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
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

    // manages key presses not handled in other Views from this Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            showDialog(DIALOG_REALLY_EXIT_ID);

        return true;
        // use this instead if you want to preserve onKeyDown() behavior
        // return super.onKeyDown(keyCode, event);
    }
}
