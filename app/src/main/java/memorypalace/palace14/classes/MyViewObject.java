package memorypalace.palace14.classes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyViewObject extends View {

    public MyViewObject(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewObject(View v) {
        super(v.getContext());
    }

    @Override
    public void setX(float x) {
        super.setTranslationX(x);
    }

    @Override
    public void setY(float y) {
        super.setTranslationY(y);
    }
}
