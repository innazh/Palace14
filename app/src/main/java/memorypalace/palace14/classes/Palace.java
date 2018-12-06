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

    public void readObjectsFile(File _f){

        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream(_f));

            this.objectList = (ArrayList<Object_assoc>) in.readObject();

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("MyPalaceDetail: File is not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("MyPalaceDetail: IOE");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("MyPalaceDetail: Class not found exception");
        } finally { in.close(); } // ERROR IN THE BLOODY FILE

    }

    public void writePalacesFile(Context context){
        ObjectOutputStream objOutStream = null;
        FileOutputStream fos = null;

        try {
            //Initialize FileOutPutStream to the file "palaces.tmp" & private access internal data storage.
            fos = context.openFileOutput("objects.tmp", MODE_PRIVATE);
            //Initialize object output stream.
            objOutStream = new ObjectOutputStream(fos);
            //Writes an array list to a file
            objOutStream.writeObject(this.objectList);
        }
        catch (IOException e) {}

    }

    @Override
    public String toString() {
        return new StringBuffer("Name: ")
                .append(this.name).append("ImgName: ")
                .append(this.imageName).toString();
    }
}
