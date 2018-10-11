package com.example.team14.memorypalace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.team14.memorypalace.Palace;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class ViewPalaceList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_palaces);

        // Open Streams to read Palace from File.
        FileInputStream fis = null;
        ObjectInputStream objFromInternal = null;

        try {

            // Needs .getFilesDir()

            fis = new FileInputStream("palaces.tmp");
            objFromInternal = new ObjectInputStream(fis);

            try {

                // Create palace by reading first object from file.
                Palace palaceToView = (Palace) objFromInternal.readObject();

                // Print name of Palace created.
                System.out.println(palaceToView.getName());

            }catch(java.lang.ClassNotFoundException CNFX){
                CNFX.printStackTrace();
                System.out.println("Palace class was not found");
            }

            objFromInternal.close();
            fis.close();

        }catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Could not find file palaces.tmp");
        }

        // Read Palace from File


    }

}
