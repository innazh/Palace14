package memorypalace.palace14.classes;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

public class ObjectTouchListener implements View.OnTouchListener {

    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            ClipData data = ClipData.newPlainText("", "");

            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);

            return true;

        } else {

            return false;
        }
    }
}
