package memorypalace.palace14;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import memorypalace.palace14.classes.PalaceList;
import memorypalace.palace14.classes.Palace;

public class routeSelector  extends AppCompatActivity {

    Button traverseRoute, viewEntireRoute, deleteRoute;
    private PalaceList listOfMyPalaces;
    private int palacePosition;
    private int routePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selector);


        // All the code here may have to fo to the OnCreate.

        Intent intent = getIntent();
        //Get data from the received intent:
        Bundle bundle = intent.getExtras();
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("palacePosition");
        routePosition = bundle.getInt("routePosition");

        traverseRoute = findViewById(R.id.traverse_route);
        viewEntireRoute = findViewById(R.id.view_entire_route);
        deleteRoute = findViewById(R.id.delete_route);

        traverseRoute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(routeSelector.this, ViewRoute.class);

                //Create a bundle to pass a PalaceList as an extra to the new activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("palaceList", listOfMyPalaces);
                //Pass in the position of a current palace
                bundle.putInt("palacePosition", palacePosition);
                //Pass in the position of selected route
                bundle.putInt("routePosition", routePosition);
                intent.putExtras(bundle);

                finish();
                startActivity(intent);
            }
        });
        viewEntireRoute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(routeSelector.this, ViewEntireRouteAsList.class);


                //Create a bundle to pass a PalaceList as an extra to the new activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("palaceList", listOfMyPalaces);
                //Pass in the position of a current palace
                bundle.putInt("palacePosition", palacePosition);
                //Pass in the position of selected route
                bundle.putInt("routePosition", routePosition);
                intent.putExtras(bundle);

                finish();
                startActivity(intent);
            }
        });

        deleteRoute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {/*
                Intent intent = new Intent(routeSelector.this, RouteListView.class);


                //Create a bundle to pass a PalaceList as an extra to the new activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("palaceList", listOfMyPalaces);
                //Pass in the position of a current palace
                bundle.putInt("palacePosition", palacePosition);
                //Pass in the position of selected route
                bundle.putInt("routePosition", routePosition);
                intent.putExtras(bundle);


                finish();
                startActivity(intent);*/




                final Dialog dialog;
                dialog = new AlertDialog.Builder(routeSelector.this).setMessage(
                        "Are you sure you want to delete " + listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getName() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        System.out.println(listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getName()  + "       " + palacePosition);
                                        listOfMyPalaces.getPalace(palacePosition).removeRoute(routePosition,getApplicationContext());
                                        // Save the state
                                        listOfMyPalaces.writePalacesFile(getApplicationContext());
                                        finish();

                                        Intent intent = new Intent(routeSelector.this, RouteListView.class);

                                        //Create a bundle to pass a PalaceList as an extra to the new activity
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("palaceList", listOfMyPalaces);

                                        //Pass in the position of a clicked palace
                                        bundle.putInt("position", palacePosition);

                                        intent.putExtra("list", bundle);


                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                }).create();
                dialog.show();
            }
        });

        }
   /* @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
        dialog = new AlertDialog.Builder(this).setMessage(
                "Are you sure you want to delete " + listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getName() + " ?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println(listOfMyPalaces + "       " + palacePosition);
                                listOfMyPalaces.getPalace(palacePosition).removeRoute(routePosition,getApplicationContext());
                                // Save the state
                                listOfMyPalaces.writePalacesFile(getApplicationContext());
                                finish();
                                startActivity(new Intent(routeSelector.this, ViewPalaceList.class));
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        }).create();
        return dialog;

    }*/


    }

   /* public void init(){

    }*/

