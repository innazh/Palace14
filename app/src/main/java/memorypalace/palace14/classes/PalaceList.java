package memorypalace.palace14.classes;

import android.content.Context;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PalaceList implements Serializable {
    private ArrayList<Palace> myPalaces;

    public  PalaceList(){
            this.myPalaces = new ArrayList<Palace>();
        }

    /*Creates a palace and adds it to the file and to the arrayList*/
    public void createPalace(Palace plToAdd, Context context, File dir){
        File f;
        ObjectOutputStream objOutStream = null;
        FileOutputStream fos = null;
        try {
            f = new File(dir + File.separator + "palaces.tmp");

            if (f.exists()) {
                //if exists, then read all the existing objects in a file and write to it again with one more object added
                readPalacesFile(f);
            }

            //Initialize FileOutPutStream to the file "palaces.tmp" & private access internal data storage.
            fos = context.openFileOutput("palaces.tmp", MODE_PRIVATE);
            this.addPalace(plToAdd);
            //Initialize object output stream.
            objOutStream = new ObjectOutputStream(fos);

            //Writes an array list to a file
            objOutStream.writeObject(this.myPalaces);

            //Output the path where the file was saved
            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + "palaces.tmp", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File's not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't create object stream");
        } finally {
            if (fos != null) {
                try {
                    // Close the Object and File Streams
                    objOutStream.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readPalacesFile(File _f)
    throws FileNotFoundException, IOException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(_f));

        try {
            //Object obj = null;
            //While it's possible to read from the file:
            // Read a Palace object from a file, print it out and add it to the list of created palaces
            //try {
                //while ((
             this.myPalaces = (ArrayList<Palace>) in.readObject();;//) != null) {
             //list.addPalace(palaceListRead);
               // }
            //} catch(EOFException e) {
             in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ViewPalaceList: File is not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ViewPalaceList: IOE");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ViewPalaceList: Class not found exception");
        } finally { in.close(); }
    }


    public void addPalace(Palace plToAdd){
        // Add palace to ArrayList of Palaces
        this.myPalaces.add(plToAdd);
    }

    public int getPalaceListSize(){
        return this.myPalaces.size();
    }

    public Palace getPalace(int indx){
        return this.myPalaces.get(indx);
    }

    public ArrayList<Palace> getPalaceArrayList(){
        return this.myPalaces;
    }
}
