package memorypalace.palace14;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
import memorypalace.palace14.classes.Object_assoc;
import memorypalace.palace14.classes.PalaceList;

public class ViewRoute extends AppCompatActivity {

    //XML
    private RelativeLayout relativeLayoutContainer;
    private RelativeLayout parentView;
    //Other Variables
    private PalaceList listOfMyPalaces;
    private int palacePosition;
    private int routePosition;
    private int x_cord, y_cord, x, y;
    private int screenCenter, windowWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        init();
    }

    public void init(){
        Intent intent = getIntent();
        //Get data from the received intent:
        Bundle bundle = intent.getExtras();
        listOfMyPalaces =  (PalaceList) bundle.getSerializable("palaceList");
        palacePosition = bundle.getInt("palacePosition");
        routePosition = bundle.getInt("routePosition");

        parentView = findViewById(R.id.viewRouteObjectsCardView);

        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenCenter = windowWidth / 2;

        //Now, we need to iterate through all of our objects and create the layourInflators for them.
        ArrayList<Object_assoc> data = listOfMyPalaces.getPalace(palacePosition).getRoute(routePosition).getObjectList();
        for (int i=0; i<data.size(); i++) {
            LayoutInflater inflate = (LayoutInflater) ViewRoute.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View containerView = inflate.inflate(R.layout.view_route_custom_object_layout, null);
            ImageView objectIcon = containerView.findViewById(R.id.objectIcon);
            RelativeLayout relativeLayoutContainer = containerView.findViewById(R.id.relative_container);


            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            containerView.setLayoutParams(layoutParams);

            containerView.setTag(i);
            //Select the right icon
            switch(data.get(i).getIcon()){
                case "barstool":
                    objectIcon.setImageResource(R.drawable.barstool);
                    break;
                case "bookcase":
                    objectIcon.setImageResource(R.drawable.bookcase);
                    break;
                case "diningset":
                    objectIcon.setImageResource(R.drawable.diningset);
                    break;
                case "stool":
                    objectIcon.setImageResource(R.drawable.stool);
                    break;
            }
            LayoutParams layoutTvParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


            TextView tvName = containerView.findViewById(R.id.tvName);
            TextView tvDesc = containerView.findViewById(R.id.tvDescription);

            tvName.setText(data.get(i).getName());
            tvDesc.setText(data.get(i).getDesc());

            // Touch listener on the image layout to swipe image right or left.
            relativeLayoutContainer.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {

                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    containerView.setX(0);
                    containerView.setY(0);

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            x = (int) event.getX();
                            y = (int) event.getY();


                            Log.v("On touch", x + " " + y);
                            break;
                        case MotionEvent.ACTION_MOVE:

                            x_cord = (int) event.getRawX();
                            // smoother animation.
                            y_cord = (int) event.getRawY();

                            containerView.setX(x_cord - x);
                            containerView.setY(y_cord - y);


                            if (x_cord >= screenCenter) {
                                containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));

                            } else {
                                // rotate image while moving
                                containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                            }

                            break;
                        case MotionEvent.ACTION_UP:

                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();

                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            parentView.removeView(containerView);

                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            parentView.addView(containerView);

        }
    }

    //Back button.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ViewRoute.this, MyPalaceDetail.class);

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
