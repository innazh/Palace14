package com.example.team14.memorypalace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class PalaceList implements Serializable {
    ArrayList<Palace> myPalaces;

    public  PalaceList(){
        this.myPalaces = new ArrayList<Palace>();
    }

    public void addPalace(Palace plToAdd){

        File file;

        FileOutputStream fos = null;

        ObjectOutputStream objToInternal = null;

        // Add New Palace to list of Palaces
        try {

            file = new File("palaces.tmp");

            fos = new FileOutputStream(file);
            objToInternal = new ObjectOutputStream(fos);

            // Check if file Exists
            if( !file.exists() )
                file.createNewFile();

            // flush the Output
            fos.flush();
            objToInternal.flush();

            // Write new Palace to file(POSSIBLY NEEDS .writeObject() OVERRIDING)
            objToInternal.writeObject(plToAdd);

            // Add palace to ArrayList of Palaces
            this.myPalaces.add(plToAdd);

            // Close the Object and File Streams
            objToInternal.close();
            fos.close();

        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Could not find file palaces.tmp");
        }


    }

}
