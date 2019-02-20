package memorypalace.palace14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;
import memorypalace.palace14.classes.RouteListAdapter;

public class RouteListView extends AppCompatActivity {
    private PalaceList listOfMyPalaces;
    private Palace currentPalace;
    private int palacePosition;
    private ListView routeListView;
    private TextView emptyRouteList;
    private RouteListAdapter routeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list_view);
        init();
    }

    public void init(){
        Intent intent = getIntent();

        //Get data from the received intent:
        Bundle bundle = intent.getBundleExtra("list");
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("position");
        //Get position of the palace clicked on the list
        currentPalace = listOfMyPalaces.getPalace(palacePosition);

        emptyRouteList = findViewById(R.id.emptyRouteTV);
        routeListView = findViewById(R.id.viewRouteListID);

        if(currentPalace.getRouteListSize() < 1){
            //show an empty list
            emptyRouteList.setVisibility(View.VISIBLE);
        }
        else{
            //Creates an adapter and attaches it to the list object -> makes the list
            routeListAdapter = new RouteListAdapter(currentPalace.getRouteList(), getApplicationContext(), getLayoutInflater());
            routeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            routeListView.setAdapter(routeListAdapter);

            //OnClick - add an object to the route queue
            routeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    selectedObjects.add(currentPalace.getObject(position));
//                    System.out.println(selectedObjects);
                }
            });
        }
    }
}
