package memorypalace.palace14.classes;

public class Object_assoc {
    private String o_name;
    private String o_desc;
    private String o_imgName; //ImageName
    private float o_Xcoordinate;
    private float o_Ycoordinate;

    public Object_assoc(String name, String desc, String imgName, float x, float y){
        this.o_name=name;
        this.o_desc=desc;
        this.o_imgName =imgName;
        this.o_Xcoordinate=x;
        this.o_Ycoordinate=y;
    }

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
}