package memorypalace.palace14.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
    private String name;
    private ArrayList<Object_assoc> objectList;

    //Look into it, maybe will have to add another parameter
    public Route(String name){

        this.name = name;
        this.objectList = new ArrayList<Object_assoc>();
    }

    public void setName(String name){
        this.name = name;
    }

    public void addObject(Object_assoc obj){
        this.objectList.add(obj);
    }

    public Object_assoc getObject(int n){
        return this.objectList.get(n);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Object_assoc> getObjectList() {
        return this.objectList;
    }
}
