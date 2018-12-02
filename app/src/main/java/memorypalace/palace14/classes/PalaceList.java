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
    public void createPalace(Palace plToAdd, Context context, File dir) {
        File f;

        try {
            f = new File(dir + File.separator + "palaces.tmp");

            if (f.exists()) {
                //if exists, then read all the existing objects in a file and write to it again with one more object added
                readPalacesFile(f);
            }

            this.addPalace(plToAdd);
            writePalacesFile(context);

            //Output the path where the file was saved
            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + "palaces.tmp", Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't create object stream");
        }
    }

    public void readPalacesFile(File _f)
    throws FileNotFoundException, IOException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(_f));

        try {

             this.myPalaces = (ArrayList<Palace>) in.readObject();

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

    /*Rename the function in a meaningful way*/
//    public void re_SavePalaceListToFileBecauseItsStateIsNowChanged(Context context){
//        writePalacesFile(context);
//    }
    public void writePalacesFile(Context context){
        ObjectOutputStream objOutStream = null;
        FileOutputStream fos = null;

        try {
            //Initialize FileOutPutStream to the file "palaces.tmp" & private access internal data storage.
            fos = context.openFileOutput("palaces.tmp", MODE_PRIVATE);
            //Initialize object output stream.
            objOutStream = new ObjectOutputStream(fos);
            //Writes an array list to a file
            objOutStream.writeObject(this.myPalaces);
        }
        catch (IOException e) {}

    }

    public boolean deletePalace(int position, Context context){
        // Remove the palace at position from the arrayList of Palaces
        boolean res=true;

        String deletedPalaceName;

        if(!this.myPalaces.isEmpty()) {
            deletedPalaceName = this.myPalaces.get(position).getName();
            this.myPalaces.remove(position);

            //Now we need to rewrite the file.
            writePalacesFile(context);

            //Output the path where the file was saved
            Toast.makeText(context, "Palace " + deletedPalaceName + " was successfully deleted", Toast.LENGTH_LONG).show();
        }
        else res=false;
        return res;
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
