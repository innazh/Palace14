package memorypalace.palace14;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import memorypalace.palace14.classes.Palace;

public class ViewPalaceList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_palace_list);

        // Open Streams to read Palace from File.
        File f= new File(getFilesDir() + File.separator + "palaces.tmp");
        FileInputStream fis = null;
        ObjectInputStream objFromInternal = null;
        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
            Palace palaceToView = (Palace) in.readObject();
            System.out.println("*****************"+palaceToView+"*****************");
            // Needs .getFilesDir()
//            //getApplicationContext().
//            fis = new FileInputStream (getFilesDir() + File.separator + "palaces.tmp");
//            objFromInternal = new ObjectInputStream(fis);
//            try {
//                System.out.println("inside the 2nd try");
//                // Create palace by reading first object from file.
//                //The Line where it crashes
//                Palace palaceToView = (Palace) objFromInternal.readObject();
//
//                // Print name of Palace created.
//                System.out.println(palaceToView.getName());
//
//            }catch(java.lang.ClassNotFoundException CNFX){
//                CNFX.printStackTrace();
//                System.out.println("Palace class was not found");
//            }
//
//            objFromInternal.close();
//            fis.close();
//
        }catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Could not open Input Stream");
        } catch(ClassNotFoundException CNFX){
            CNFX.printStackTrace();
            System.out.println("Could not read object");
        }

        // Read Palace from File


    }

}
