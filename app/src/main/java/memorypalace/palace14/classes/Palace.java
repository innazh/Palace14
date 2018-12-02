package memorypalace.palace14.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Palace implements Serializable {

    String name;
    String imageName;
    ArrayList<Object_assoc> objectList;

    public Palace(String palaceName, String floorPath){

        this.name = palaceName;
        this.imageName = floorPath;
        this.objectList = new ArrayList<Object_assoc>();
    }

    //Setters
    public void addObject(Object_assoc object){
        objectList.add(object);
    }

    //Getters
    public String getName(){
        return this.name;
    }

    public String getImageName(){
        return this.imageName;
    }

    public Object_assoc getObject(int indx) { return this.objectList.get(indx); }

    @Override
    public String toString() {
        return new StringBuffer("Name: ")
                .append(this.name).append("ImgName: ")
                .append(this.imageName).toString();
    }
}
