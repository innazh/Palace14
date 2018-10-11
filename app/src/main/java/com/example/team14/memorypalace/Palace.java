package com.example.team14.memorypalace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;

public class Palace implements Serializable {

    String name;
    Bitmap img;
    String imagePath;

    Palace(String palaceName, String floorPath){

        this.name = palaceName;
        this.imagePath = floorPath;

        // Assign the image(path to Image) and then convert image to compatible format(Bitmap)
        img = BitmapFactory.decodeFile(this.imagePath);

    }

    public String getName(){
        return this.name;
    }
}
