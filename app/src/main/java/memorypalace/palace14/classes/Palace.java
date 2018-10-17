package memorypalace.palace14.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

public class Palace implements Serializable {

    String name;
    //Bitmap img;
    String imagePath; //Did you mean img name?


    public Palace(String palaceName, String floorPath){

        this.name = palaceName;
        this.imagePath = floorPath;

        // Assign the image(path to Image) and then convert image to compatible format(Bitmap)
        //img = BitmapFactory.decodeFile(this.imagePath);

    }

    public String getName(){
        return this.name;
    }

    //Image Name/Path (?)
    public String getImagePath(){
        return this.imagePath;
    }

    @Override
    public String toString() {
        return new StringBuffer("Name: ")
                .append(this.name).append("ImgName: ")
                .append(this.imagePath).toString();
    }
}
