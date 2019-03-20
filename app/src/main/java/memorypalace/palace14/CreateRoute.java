package memorypalace.palace14;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

public class CreateRoute extends AppCompatActivity implements ObjectListAdapter.ItemClickCallback {
    private PalaceList listOfMyPalaces;
    private Palace currentPalace;
    private RecyclerView objectsListView;
    private TextView emptyObjectList;
    private EditText routeNameET;
    private ObjectListAdapter adapter;
    private int palacePosition;
    private ArrayList<Object_assoc> objects;
    private List<Object_assoc> selectedObjects;
    private List<Integer> selectedObjectsIdx;
    private Button createRouteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        init();

        createRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!routeNameET.getText().toString().matches("")) {
                    //Check if route with that name already exists
                    boolean flag = false;
                    for (int k = 0; k < currentPalace.getRouteListSize() && !flag; k++) {
                        if (routeNameET.getText().toString().compareTo(currentPalace.getRoute(k).getName()) == 0)
                            flag = true;
                    }

                    if (!flag) {
                        //Check if any objects were selected:
                        if (!selectedObjects.isEmpty()) {

                            Route route = new Route(routeNameET.getText().toString());

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

                            currentPalace.addRoute(route);

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
                        } else {
                            Toast.makeText(CreateRoute.this, "Please select a number of objects in sequence", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CreateRoute.this, "A route with that name already exists, please enter a different name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateRoute.this, "Please type in the route name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(objectsListView);
    }

    public void init() {
        Intent intent = getIntent();

        //Get data from the received intent:
        Bundle bundle = intent.getBundleExtra("list");
        listOfMyPalaces = (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("position");
        //Get position of the palace clicked on the list
        currentPalace = listOfMyPalaces.getPalace(palacePosition);
        //Because Queue is an interface - needs to be initialized with LinkedList
        selectedObjects = new LinkedList<Object_assoc>();
        selectedObjectsIdx = new LinkedList<Integer>();
        //Find the list view in XML file and put it in our local variable
        objectsListView = findViewById(R.id.viewObjectListID);
        emptyObjectList = findViewById(R.id.emptyObjectsTV);
        createRouteBtn = findViewById(R.id.createRouteBtn);
        routeNameET = findViewById(R.id.routeName);

        if (currentPalace.getObjectListSize() < 1) {
            //show an empty list
            emptyObjectList.setVisibility(View.VISIBLE);
        } else {
            //Creates an adapter and attaches it to the list object -> makes the list
            objects = currentPalace.getObjectList();
            adapter = new ObjectListAdapter(objects, CreateRoute.this);
            objectsListView.setLayoutManager(new LinearLayoutManager(this));
            objectsListView.setAdapter(adapter);
            adapter.setItemClickCallback(this);

        }
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
