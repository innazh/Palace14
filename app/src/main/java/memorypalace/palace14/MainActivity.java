package memorypalace.palace14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button choosePalace, buildPalace, palaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choosePalace = findViewById(R.id.choosePalace);
        buildPalace = findViewById(R.id.buildPalace);
        palaceList = findViewById(R.id.palaceList);

        choosePalace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChoosePalace.class);
                startActivity(intent);
            }
        });

        buildPalace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuildPalace.class);
                startActivity(intent);
            }
        });

        palaceList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPalaceList.class);
                startActivity(intent);
            }
        });
    }

}