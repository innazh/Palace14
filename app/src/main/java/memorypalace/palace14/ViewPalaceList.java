package memorypalace.palace14;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;

public class ViewPalaceList  extends AppCompatActivity {

    ListView palaceListView;
    PalaceList palacesCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_palace_list);

        //Initialize a path to the file and create a variable for the list of created palaces
        File f = new File(getFilesDir() + File.separator + "palaces.tmp");
        palacesCreated = new PalaceList();

        //Starts reading from the file if it exists, prints an empty list otherwise.
        if (f.exists()) {
            try {
                palacesCreated.readPalacesFile(f);
            } catch(IOException e) {System.out.println("ViewPalaceList IOE"); e.printStackTrace();}

        }
        System.out.println("!!!!!!!NUMBER OF PALACES: " + palacesCreated.getPalaceListSize());
        if(palacesCreated.getPalaceListSize()<1){
            //show an empty list
        }

    }

}

