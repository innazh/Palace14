package memorypalace.palace14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import memorypalace.palace14.classes.MyAdapter;
import memorypalace.palace14.classes.Palace;
import memorypalace.palace14.classes.PalaceList;
import memorypalace.palace14.classes.RouteListAdapter;

public class ViewEntireRouteAsList extends AppCompatActivity {

    private PalaceList listOfMyPalaces;
    private int palacePosition;
    private ListView viewRouteContents;
    private TextView emptyList;
    private RouteListAdapter routeListAdapter;
    private Palace currentPalace;
    private TextView emptyRouteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entire_route);
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
        viewRouteContents = findViewById(R.id.viewRouteListID);


    }




}