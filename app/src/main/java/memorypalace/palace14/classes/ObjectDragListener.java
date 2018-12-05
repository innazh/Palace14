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
    public float initialX;
    public float initialY;

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
        initialX = dragView.getX(); // PLEASE REMIND ME IF JAVA IS SUPER-ANAL ABOUT USING this. BEFORE MEMBER VARIABLES.
        initialY = dragView.getY();
        System.out.println("INIT_X: " + initialX);
        System.out.println("INIT_Y: " + initialY);


        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
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

                ondragres = callAddObjectDialog(this.context, x, y, view.getTag().toString());

//                /*Still a bit buggy.
//                  The problem is: I don't know how to properly return the result from the onClick function
//                  because it runs 'asynchroniously'(?) and right now returns the result to me only after my dragging and dropping is completely over
//                  so that I can't reset anything based on the result*/

                /*
                    Correction YOU CANNOT RETURN THE RESULT FROM ONCLICk
                    So therefore, that means we need to code this in such a way that we do not use the
                    return value of the onclick.
                 */


//                System.out.println("RESULT BEFORE IF: = " + result);
//                if(result) {
//                    System.out.println("RESULT TRUE");
//                }
//                else{
//                    System.out.println("RESULT FALSE");
//                    dragView.setX(initialX);
//                    dragView.setY(initialY);
//                }
//                //////////////////////////////////////////////////////////////////////////////////////////////////
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

    private boolean callAddObjectDialog(Context context, final float x, final float y, final String imgName)
    {
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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                palace.addObject(new Object_assoc(objName.getText().toString(),objDesc.getText().toString(),imgName ,x, y));
                //doOnTrueResult();
                myDialog.cancel();
                result[0] = true;

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doOnFalseResult();
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
