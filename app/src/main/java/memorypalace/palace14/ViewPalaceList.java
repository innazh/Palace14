package memorypalace.palace14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import memorypalace.palace14.classes.MyAdapter;
import memorypalace.palace14.classes.PalaceList;

public class ViewPalaceList  extends AppCompatActivity {

    private PalaceList palacesCreated;
    private ListView palaceListView;
    private MyAdapter adapter;
    private TextView emptyPalaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_palace_list);

        //Find the list view in XML file and put it in our local variable
        palaceListView = findViewById(R.id.viewPalaceListID);
        emptyPalaceList = findViewById(R.id.emptyPalacesTV);

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
            emptyPalaceList.setVisibility(View.VISIBLE);
        }
        else{
            //Creates an adapter and attaches it to the list object -> makes the list
            adapter = new MyAdapter(palacesCreated.getPalaceArrayList(), getApplicationContext(), getLayoutInflater());
            palaceListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            palaceListView.setAdapter(adapter);

            //Sets up a click function for each one of the items in the list
            palaceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ViewPalaceList.this, MyPalaceDetail.class);

                    //Create a bundle to pass a PalaceList as an extra to the new activity
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("palaceList", palacesCreated);

                    //Pass in the position of a clicked palace
                    bundle.putInt("position", position);

                    intent.putExtra("list", bundle);

                    startActivity(intent);
                }
            });
        }

    }

}
