package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class EditProductPhoto extends AppCompatActivity {
    Button save;
    Button addPhoto;

    //ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPhoto = (Button) findViewById(R.id.addPhotoBtn);
        save = (Button) findViewById(R.id.saveBtn);

//        addPhoto.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//           }
//       });
    }
}
