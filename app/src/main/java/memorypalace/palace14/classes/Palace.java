package memorypalace.palace14.classes;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Palace implements Serializable {

    private String name;
    private String imageName;
    private ArrayList<Object_assoc> objectList;
    private ArrayList<Route> routeList;

    public Palace(String palaceName, String floorPath){

        this.name = palaceName;
        this.imageName = floorPath;
        this.objectList = new ArrayList<Object_assoc>();
        this.routeList = new ArrayList<Route>();
    }

    public void removeObject(int position){
        this.objectList.remove(position);
    }

    public void removeRoute(int position){
        this.routeList.remove(position);
    }

    //Returns the position of the object on the objectList
    public int findObject(Object_assoc obj){
        int result=-1;

        for(int i=0; i<getListLength() && result==-1;i++) {
            if(this.objectList.get(i).getName().compareTo(obj.getName())==0){
                result = i;
            }
        }

        return result;
    }

    //Setters
    public void addObject(Object_assoc object){
        this.objectList.add(object);
    }

    public void addRoute(Route path){
        this.routeList.add(path);
    }

    //Getters
    public int getListLength() { return this.objectList.size(); }

    public String getName(){
        return this.name;
    }

    public String getImageName(){
        return this.imageName;
    }

    public Object_assoc getObject(int indx) { return this.objectList.get(indx); }

    public int getObjectListSize(){
        return this.objectList.size();
    }

    // Method to return a valid Identifier for the palace(Generate a key)
    public int getValidIdentifier() {

        int [] takenIdentifiers = new int [getObjectListSize()];
        boolean invalidIdentifier = false;

        // Loop to find all Taken Identifiers
        for(int i = 0; i < getObjectListSize(); i++){
            takenIdentifiers[i] = getObject(i).get_identifier();
        }

        int primaryKey;

        // Create a valid Identifier that is unique(AKA PRIMARY KEY)
        do{

            primaryKey = (int)(Math.random() * ((1000 - 0) + 1));
            invalidIdentifier = false; // Assumes there key is unique

            // Check for identifiers that are the same
            for(int x = 0; x < getObjectListSize(); x++){
                if(primaryKey == getObject(x).get_identifier()){
                    invalidIdentifier = true;
                }
            }

        }while(invalidIdentifier); // The key is unique

        return primaryKey;

    }

    // Should return position or object??
    public Object_assoc getObjectByKey(int keyToFind){

        Object_assoc retObj = null;
        boolean found = false;

        for(int i = 0; i < getListLength() && !found; i++){

            // Object with corresponding key is found, return its index
            if(keyToFind == getObject(i).get_identifier()){
                found = true;
                retObj = getObject(i);
            }
        }
        return retObj;

    }

    //Route///////////////////////////////////////////
    public ArrayList<Object_assoc> getObjectList() {
        return this.objectList;
    }

    public Route getRoute(int indx) { return this.routeList.get(indx); }

    public int getRouteListSize(){
        return this.routeList.size();
    }

    public ArrayList<Route> getRouteList() {
        return this.routeList;
    }

    @Override
    public String toString() {
        return new StringBuffer("Name: ")
                .append(this.name).append("ImgName: ")
                .append(this.imageName).toString();
    }
}
