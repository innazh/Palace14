package memorypalace.palace14.classes;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Object_assoc implements Serializable {
    private String o_name;
    private String o_desc;
    private String o_imgName; //ImageName
    private String view_tag;
    boolean isSelected;//Only for ObjectAdapter
    private float o_initXcoordinate;
    private float o_initYcoordinate;
    private float o_Xcoordinate;
    private float o_Ycoordinate;
    private Bitmap o_memory;
    private int o_identifier;

    public Object_assoc(String name, String desc, String imgName, String viewTag, float x, float y, float initx, float inity){
        this.o_name=name;
        this.o_desc=desc;
        this.o_imgName =imgName;
        this.view_tag=viewTag;
        this.o_Xcoordinate=x;
        this.o_Ycoordinate=y;
        this.o_initXcoordinate=initx;
        this.o_initYcoordinate=inity;
    }

    public Object_assoc(String name, String desc, String imgName, String viewTag, float x, float y){
        this.o_name=name;
        this.o_desc=desc;
        this.o_imgName =imgName;
        this.view_tag=viewTag;
        this.o_Xcoordinate=x;
        this.o_Ycoordinate=y;
    }

    //Copy constructor
    public Object_assoc(Object_assoc obj){
        this.o_name = obj.o_name;
        this.o_desc = obj.o_desc;
        this.o_imgName = obj.o_imgName;
        this.view_tag = obj.view_tag;
        this.o_Xcoordinate = obj.o_Xcoordinate;
        this.o_Ycoordinate = obj.o_Ycoordinate;
    }

    public String getView_tag(){ return this.view_tag;}
    //Getters
    public String getName(){
        return this.o_name;
    }

    public String getDesc(){
        return this.o_desc;
    }

    public String getIcon(){
        return this.o_imgName;
    }

    public float getO_Xcoordinate() {
        return o_Xcoordinate;
    }

    public float getO_Ycoordinate() {
        return o_Ycoordinate;
    }

    public float getO_initYcoordinate() {
        return o_initYcoordinate;
    }

    public float getO_initXcoordinate() {
        return o_initXcoordinate;
    }

    public Bitmap getMemory(){ return this.o_memory; }

    public boolean isSelected() {
        return isSelected;
    }

    //Setters

    public void setO_desc(String o_desc) {
        this.o_desc = o_desc;
    }

    public void setO_name(String o_name) {
        this.o_name = o_name;
    }

    public void setO_Xcoordinate(float o_Xcoordinate) {
        this.o_Xcoordinate = o_Xcoordinate;
    }

    public void setO_Ycoordinate(float o_Ycoordinate) {
        this.o_Ycoordinate = o_Ycoordinate;
    }

    public void setO_initXcoordinate(float o_initXcoordinate) {
        this.o_initXcoordinate = o_initXcoordinate;
    }

    public void setO_initYcoordinate(float o_initYcoordinate) {
        this.o_initYcoordinate = o_initYcoordinate;
    }

    public void setO_memory(Bitmap memory){
        if(memory != null) {
            this.o_memory = memory;
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setO_identifier(int key){
        o_identifier = key;
    }

    public int get_identifier(){
        return o_identifier;
    }
}