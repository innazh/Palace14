package memorypalace.palace14.classes;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PalaceList implements Serializable {
    ArrayList<Palace> myPalaces;

    public  PalaceList(){
            this.myPalaces = new ArrayList<Palace>();
        }

    public void addPalace(Palace plToAdd, Context context){
        ObjectOutputStream objToIntxernal = null;
        FileOutputStream fos = null;
        try {

            //Initialize FileOutPutStream to the file "palaces.tmp" & private access internal data storage.
            fos = context.openFileOutput("palaces.tmp", MODE_PRIVATE);
            //Initialize object output stream.
            objToIntxernal = new ObjectOutputStream(fos);
            // flush the Output
            //fos.flush();
            //objToIntxernal.flush();
            // Write new Palace to file(POSSIBLY NEEDS .writeObject() OVERRIDING)
            objToIntxernal.writeObject(plToAdd);
            //Output the path where the file was saved
            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + "palaces.tmp", Toast.LENGTH_LONG).show();
            // Close the Object and File Streams
            //objToInternal.close();
            fos.close();
            // Add palace to ArrayList of Palaces
            this.myPalaces.add(plToAdd);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't create object stream");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //I created this function here because I couln't think of a simplier way to read from a file as it also needs a context parameter passed to it
    // and I didn't really know how it's possible to do so with onCreate function

}
