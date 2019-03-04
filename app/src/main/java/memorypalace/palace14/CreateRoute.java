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

import java.util.LinkedList;
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
    private Queue<Object_assoc> selectedObjects;
    private Button createRouteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        init();

        createRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route route = new Route(routeNameET.getText().toString());
                System.out.println("EWFWEFWEFWEFEF");
                for(int i=0; i < selectedObjects.size(); i++){
                    //Retrieves and removes the head of this queue, or returns null if this queue is empty.
                    route.addObject(selectedObjects.poll());
                }

                currentPalace.addRoute(route);
                listOfMyPalaces.writePalacesFile(CreateRoute.this);
                Toast.makeText(getApplicationContext(), "The route " + routeNameET.getText().toString() + " is saved!", Toast.LENGTH_SHORT);
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
            adapter = new ObjectListAdapter(currentPalace.getObjectList(), getApplicationContext(), getLayoutInflater());
            //objectsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            objectsListView.setAdapter(adapter);
            System.out.println("ONE CHECK");

            //OnClick - add an object to the route queue
            objectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedObjects.add(currentPalace.getObject(position));
                    System.out.println(selectedObjects);
                }
            });

            Intent RouteIntent = new Intent(CreateRoute.this,ViewRoute.class );

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
            finish();
            Intent intent = new Intent(CreateRoute.this, ViewPalaceList.class);
            startActivity(intent);

        }
        return true;
    }
}
