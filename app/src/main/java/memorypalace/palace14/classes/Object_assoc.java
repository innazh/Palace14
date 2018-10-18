package memorypalace.palace14.classes;

import android.graphics.Bitmap;

public class Object_assoc {
    private String o_name;
    private String o_desc;
    private String o_symbol;
    //Bitmap o_img;

    public Object_assoc(String name, String desc, String symbol){
        this.o_name=name;
        this.o_desc=desc;
        this.o_symbol=symbol;
    }

    public String getName(){
        return this.o_name;
    }

    public String getDesc(){
        return this.o_desc;
    }

    public String getSymbol(){
        return this.o_symbol;
    }
}