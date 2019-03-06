package memorypalace.palace14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import memorypalace.palace14.classes.MyAdapter;
import memorypalace.palace14.classes.ObjectListAdapter;
import memorypalace.palace14.classes.Object_assoc;
import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;
import memorypalace.palace14.classes.Route;

public class CreateRoute extends AppCompatActivity {
    private PalaceList listOfMyPalaces;
    private Palace currentPalace;
    private ListView objectsListView;
    private TextView emptyObjectList;
    private EditText routeNameET;
    private ObjectListAdapter adapter;
    private int palacePosition;
    private List<Object_assoc> selectedObjects;
    private Button createRouteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        init();

        createRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!routeNameET.getText().toString().matches("")) {
                    //Check if route with that name already exists
                    boolean flag=false;
                    for(int k=0; k<currentPalace.getRouteListSize() && !flag;k++) {
                        if(routeNameET.getText().toString().compareTo(currentPalace.getRoute(k).getName())==0)
                            flag=true;
                    }

                    if(!flag) {
                        //Check if any objects were selected:
                        if(!selectedObjects.isEmpty()) {

                            Route route = new Route(routeNameET.getText().toString());

                            //fix
                            for (int i = 0; i < selectedObjects.size(); i++) {
                                //Retrieves and removes the head of this queue, or returns null if this queue is empty.
                                route.addObject(selectedObjects.get(i));
                            }

                            currentPalace.addRoute(route);

                            //Reset the checkboxes in objects
                            for (int j = 0; j < currentPalace.getObjectListSize(); j++) {
                                currentPalace.getObject(j).setSelected(false);
                            }

                            listOfMyPalaces.writePalacesFile(CreateRoute.this);
                            Toast.makeText(CreateRoute.this, "The route " + routeNameET.getText().toString() + " is saved!", Toast.LENGTH_SHORT).show();

                            //Go back to viewing my palace Detail
                            Intent intent = new Intent(CreateRoute.this, MyPalaceDetail.class);

                            //Create a bundle to pass a PalaceList as an extra to the new activity
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("palaceList", listOfMyPalaces);

                            //Pass in the position of a clicked palace
                            bundle.putInt("position", palacePosition);

                            intent.putExtra("list", bundle);
                            finish();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(CreateRoute.this, "Please select a number of objects in sequence", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(CreateRoute.this, "A route with that name already exists, please enter a different name", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CreateRoute.this, "Please type in the route name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init(){
        Intent intent = getIntent();

        //Get data from the received intent:
        Bundle bundle = intent.getBundleExtra("list");
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("position");
        //Get position of the palace clicked on the list
        currentPalace = listOfMyPalaces.getPalace(palacePosition);
        //Because Queue is an interface - needs to be initialized with LinkedList
        selectedObjects = new LinkedList<Object_assoc>();

        //Find the list view in XML file and put it in our local variable
        objectsListView = findViewById(R.id.viewObjectListID);
        emptyObjectList = findViewById(R.id.emptyObjectsTV);
        createRouteBtn = findViewById(R.id.createRouteBtn);
        routeNameET = findViewById(R.id.routeName);

        if(currentPalace.getObjectListSize() < 1){
            //show an empty list
            emptyObjectList.setVisibility(View.VISIBLE);
        }
        else{
            //Creates an adapter and attaches it to the list object -> makes the list
            final ArrayList<Object_assoc> objects = currentPalace.getObjectList();
            adapter = new ObjectListAdapter(objects, getApplicationContext(), getLayoutInflater());

            objectsListView.setAdapter(adapter);

            //OnClick - add an object to the route queue
            objectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object_assoc one = objects.get(position);

                    if (one.isSelected()) {
                        one.setSelected(false);
                        if(selectedObjects.contains(currentPalace.getObject(position))) //probs don't need that line
                            selectedObjects.remove(currentPalace.getObject(position));
                    }
                    else {
                        one.setSelected(true);
                        selectedObjects.add(currentPalace.getObject(position));
                    }

                    objects.set(position, one);

                    //now update adapter
                    adapter.updateRecords(objects);
                    //System.out.println(selectedObjects);
                }
            });
        }
    }


//    //Bottom navigation menu listener
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_create_path:
//                    mTextMessage.setText(R.string.create_path);
//                    finish();
//                    Intent intent = new Intent(MyPalaceDetail.this, CreateRoute.class);
//                    startActivity(intent);
//                    return true;
//                case R.id.navigation_rename_palace:
//                    mTextMessage.setText(R.string.rename_palace);
//                    return true;
//                case R.id.navigation_delete_palace:
//                    mTextMessage.setText(R.string.delete_palace);
//                    showDialog(1);
//                    return true;
//            }
//            return false;
//        }
//    };

    //Back button.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(CreateRoute.this, MyPalaceDetail.class);

            //Create a bundle to pass a PalaceList as an extra to the new activity
            Bundle bundle = new Bundle();
            bundle.putSerializable("palaceList", listOfMyPalaces);

            //Pass in the position of a clicked palace
            bundle.putInt("position", palacePosition);

            intent.putExtra("list", bundle);
            finish();
            startActivity(intent);

        }
        return true;
    }
}
