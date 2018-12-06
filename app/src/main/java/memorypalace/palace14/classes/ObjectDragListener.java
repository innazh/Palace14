package memorypalace.palace14.classes;

import android.app.Dialog;
import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import memorypalace.palace14.R;

public class ObjectDragListener implements View.OnDragListener {
    private Context context;
    private Palace palace;
    private boolean globalRes;
    public View dragView;
    public String viewTag;
    public float initialX;
    public float initialY;
    public float draggedX;
    public float draggedY;

    /*The whole idea of having a result is being able to cancel dragging and dropping depending on the result.
    * If the user pressed cancel then the object is supposed to be returned to its initial spot.
    * If "save" is pressed then the system creates new Object_assoc object and adds it to the list of objects in the Palace.*/
    //boolean result;

    public ObjectDragListener(Context context, Palace palace){
        this.context=context;
        this.palace=palace;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        boolean ondragres=false;
        int action = event.getAction();
        dragView = (View) event.getLocalState();
        System.out.println("INIT_X: " + initialX);
        System.out.println("INIT_Y: " + initialY);


        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                initialX = dragView.getX(); // PLEASE REMIND ME IF JAVA IS SUPER-ANAL ABOUT USING this. BEFORE MEMBER VARIABLES.
                initialY = dragView.getY();

                // Get the tag of the view being dragged
                viewTag = dragView.getTag().toString();

                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                break;

            case DragEvent.ACTION_DROP:
                View view = (View) event.getLocalState();
                float x = event.getX();
                float y = event.getY();

                /*Here, I want to try to get mouse coordinates later (idk maybe its stupid idea and i shouldnt*/
                dragView.setX(x+dragView.getWidth());// -(view.getWidth()/2));
                dragView.setY(y);
                draggedX = (x+dragView.getWidth());
                draggedY = y;


                ondragres = callAddObjectDialog(this.context, x, y, view.getTag().toString());

            case DragEvent.ACTION_DRAG_ENDED:
                System.out.println("ACTION_DRAG_EXITED: PIZDETS");

                // Check for drag within the Palace Blueprint.
                if (!event.getResult()) {
                    // Sets the dragged object to its original place
                    dragView.setVisibility(View.VISIBLE);
                }
                // SUBJECT TO CHANGE
                dragView.setVisibility(View.VISIBLE);
                System.out.println("ACTION_DROP_FINAL_X: " + dragView.getX());
                System.out.println("ACTION_DROP_FINAL_Y: " + dragView.getY());

                break;
            default:
                break;
        }

        return true;
    }

    /*
     Function for saving object with its coordinates, name and description
      */
    public void saveObject(){
        // Sets and saves the Object (Kind of redundant tbh)
        dragView.setX(draggedX);
        dragView.setY(draggedY);
    }

    /*
    Function for resetting the object, to its starting coordinates
     */
    public void resetObject(){
        // Reset the object coordinates
        System.out.println("blah Current X: " +dragView.getX());
        System.out.println("blah Current Y: "+dragView.getY());

        dragView.setX(initialX);
        dragView.setY(initialY);
        System.out.println("blah Reset Initial X: "+initialX);
        System.out.println("blah Reset Initial Y: "+initialY);
        System.out.println("blah Dragged Object X: " +dragView.getX());
        System.out.println("blah Dragged Object Y: " +dragView.getY());

    }



    private boolean callAddObjectDialog(Context context, final float x, final float y, final String imgName)
    {
        System.out.println("OBJECT DIALOG IS CALLED");

        final EditText objName, objDesc;
        Button saveBtn, cancelBtn;
        final Dialog myDialog;
        final boolean result[] = new boolean[1];
        myDialog = new Dialog(context);

        myDialog.setContentView(R.layout.add_object_pop_up);
        myDialog.setCancelable(false);

        objName=myDialog.findViewById(R.id.objectNameInput);
        objDesc=myDialog.findViewById(R.id.objectDescInput);
        saveBtn=myDialog.findViewById(R.id.saveBtnObjInpt);
        cancelBtn=myDialog.findViewById(R.id.cancelBtnObjInpt);

        myDialog.show();


        // SAVES THE BLOODY OBJECT
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("SAVE THE OBJECT");
                // Add the object with its name, description and final x and y after dropped coordinates
                palace.addObject(new Object_assoc(objName.getText().toString(),objDesc.getText().toString(), viewTag,imgName ,draggedX, draggedY));
                saveObject();
                myDialog.cancel();
                result[0] = true;

            }
        });

        // CANCELS SAVING THE BLOODY OBJECT
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doOnFalseResult();
                System.out.println("DOESITBLOODYWORKORNOT");
                resetObject();
                myDialog.cancel();
                result[0] = false;
            }
        });

        return result[0];
    }

    public boolean getGlobalRes(){
        return globalRes;
    }
}
