package memorypalace.palace14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import memorypalace.palace14.classes.ObjectListAdapter;
import memorypalace.palace14.classes.Object_assoc;
import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;
import memorypalace.palace14.classes.Route;
import memorypalace.palace14.classes.RouteListAdapter;

public class ViewEntireRouteAsList extends AppCompatActivity {

    private PalaceList listOfMyPalaces;
    private int palacePosition;
    private TextView RouteName;
    private int routePosition;
    private Palace currentPalace;
    private ArrayList<Object_assoc> objects;
    private List<Object_assoc> selectedObjects;
    private List<Integer> selectedObjectsIdx;
    private Button reorderBtn;
    private RecyclerView objectsListView;
    private ObjectListAdapter adapter;
    private TextView emptyObjectList;
    private EditText routeNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entire_route);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        init();

        reorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        //Check if any objects were selected:
                        if (!selectedObjects.isEmpty()) {

                                // Recreate Route with previous routename
                                Route route = new Route(listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getName());

                                //Go through all the objects, if selected -> put on the list
                                for (int i = 0; i < objects.size(); i++) {
                                    if (objects.get(i).isSelected()) {
                                        //reset the checkboxes
                                        objects.get(i).setSelected(false);
                                    }
                                }

                                //Sort small to big
                                int temp;
                                Object_assoc temp2;
                                for (int i = 0; i < selectedObjectsIdx.size(); i++) {
                                    for (int j = 1; j < (selectedObjectsIdx.size() - i); j++) {
                                        if (selectedObjectsIdx.get(j - 1) > selectedObjectsIdx.get(j)) {
                                            //swap elements
                                            temp = selectedObjectsIdx.get(j - 1);
                                            selectedObjectsIdx.set(j - 1, selectedObjectsIdx.get(j));
                                            selectedObjectsIdx.set(j, temp);

                                            temp2 = selectedObjects.get(j - 1);
                                            selectedObjects.set(j - 1, selectedObjects.get(j));
                                            selectedObjects.set(j, temp2);
                                        }

                                    }
                                }
                                for (int i = 0; i < selectedObjects.size(); i++) {
                                    route.addObject(selectedObjects.get(i));
                                }
                                System.out.println(selectedObjectsIdx);
                                System.out.println(selectedObjects);

                                //Remove the modified Route
                                listOfMyPalaces.getPalace(palacePosition).removeRoute(routePosition,getApplicationContext());

                                // Add new route to palace
                                currentPalace.addRoute(route);

                                // Save the state
                                listOfMyPalaces.writePalacesFile(getApplicationContext());
                                Toast.makeText(ViewEntireRouteAsList.this, "The route " + routeNameET.getText().toString() + " is reordered!", Toast.LENGTH_SHORT).show();

                                //Go back to viewing my palace Detail
                                Intent intent = new Intent(ViewEntireRouteAsList.this, RouteListView.class);

                                //Create a bundle to pass a PalaceList as an extra to the new activity
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("palaceList", listOfMyPalaces);

                                //Pass in the position of a clicked palace
                                bundle.putInt("position", palacePosition);

                                intent.putExtra("list", bundle);


                                startActivity(intent);
                                finish();
                                startActivity(intent);



                        } else {
                            Toast.makeText(ViewEntireRouteAsList.this, "Please select a number of objects in sequence", Toast.LENGTH_SHORT).show();
                        }
            }
        });

        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(objectsListView);
        init();
    }

    public void init(){
        Intent intent = getIntent();

        RouteName = findViewById(R.id.crTitle);

        //Get data from the received intent:
        Bundle bundle = intent.getExtras();
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("palacePosition");
        routePosition = bundle.getInt("routePosition");
        RouteName.setText(listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getName());
        //Because Queue is an interface - needs to be initialized with LinkedList
        selectedObjects = new LinkedList<Object_assoc>();
        selectedObjectsIdx = new LinkedList<Integer>();
        //Find the list view in XML file and put it in our local variable
        objectsListView = findViewById(R.id.viewObjectListID);
        emptyObjectList = findViewById(R.id.emptyObjectsTV);
        reorderBtn = findViewById(R.id.createRouteBtn);
        routeNameET = findViewById(R.id.routeName);
        //Get position of the palace clicked on the list
        currentPalace = listOfMyPalaces.getPalace(palacePosition);

        //Creates an adapter and attaches it to the list object -> makes the list
        objects = listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getObjectList();
        adapter = new ObjectListAdapter(objects, ViewEntireRouteAsList.this);
        objectsListView.setLayoutManager(new LinearLayoutManager(this));
        objectsListView.setAdapter(adapter);
        adapter.setItemClickCallback(this); // Why is there a problem here?

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        0 | 0) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }


    private void moveItem(int oldPos, int newPos) {

        Object_assoc item = objects.get(oldPos);
        objects.remove(oldPos);
        objects.add(newPos, item);
        adapter.notifyItemMoved(oldPos, newPos);
    }

    @Override
    public void onItemClick(int p) {
        System.out.println("ONITEMCLICK!!!!!!!!!!!!");
        System.out.println("Position:" + p );
        Object_assoc item = objects.get(p);
        if (item.isSelected()) {
            item.setSelected(false);
            selectedObjects.remove(currentPalace.getObject(p));
            selectedObjectsIdx.remove(p);
        }
        else {
            item.setSelected(true);
            selectedObjects.add(currentPalace.getObject(p));
            selectedObjectsIdx.add(p);
        }
        System.out.println(selectedObjectsIdx);
        System.out.println(selectedObjects);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSecondaryIconClick(int p) {

        System.out.println("ONSECONDARYITEMCLICK!!!!!!!!!!!!");
        System.out.println("Position:" + p );
        Object_assoc item = objects.get(p);
        //update our data
        if (item.isSelected()) {
            item.setSelected(false);
            selectedObjects.remove(currentPalace.getObject(p));
            selectedObjectsIdx.remove(p);
        } else {
            item.setSelected(true);
            selectedObjects.add(currentPalace.getObject(p));
            selectedObjectsIdx.add(p);
        }
        System.out.println(selectedObjectsIdx);
        System.out.println(selectedObjects);
        //pass new data to adapter and update
        //adapter.setListData(objects);
        adapter.notifyDataSetChanged();
    }

    //Back button.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ViewEntireRouteAsList.this, MyPalaceDetail.class);

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