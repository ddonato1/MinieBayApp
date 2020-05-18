/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * Product Info Activity, this activity will display the product that the user selected. Showing
 * its information and a button for adding a bid. Where the bid button will let the user add a bid
 * on the product if he/she wants
 **/
package com.example.miniebayapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductInfo extends AppCompatActivity {
    private String hostAddress;

    TextView namep, descp, pricep, deptp, categp, ownerp;
    ImageView img;
    String name;
    String desc;
    String price;
    String dept;
    String categ;
    String owner;
    String productid;
    Button bidbtn;

    View convertView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Define the web server's IP address
        hostAddress="192.168.0.11:8088";

        bidbtn = (Button) findViewById(R.id.addbidButton);
        ownerp = (TextView) findViewById(R.id.itemOwner);
        namep = (TextView) findViewById(R.id.itemName);
        descp = (TextView) findViewById(R.id.itemDescription);
        pricep = (TextView) findViewById(R.id.itemPrice);
        deptp = (TextView) findViewById(R.id.itemDepartment);
        categp = (TextView) findViewById(R.id.itemCategory);
        img = (ImageView) findViewById(R.id.imageView4);

        Intent i = this.getIntent();
        name = i.getStringExtra("Name");
        desc = i.getStringExtra("Description");
        price = i.getStringExtra("Price");
        dept = i.getStringExtra("deptid");
        categ = i.getStringExtra("cateid");
        owner = i.getStringExtra("Owner");

        productid = i.getStringExtra("prodid");
        namep.setText(name);

        //img.setImageResource(p);
//        img.setImageAlpha(Integer.parseInt(imageURL));
        descp.setText(desc);
        pricep.setText(price);
        deptp.setText(dept);
        categp.setText(categ);
        ownerp.setText(owner);

        //listv = findViewById(R.id.itemLists);


        bidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = price;
                String o = owner;
                String pid = productid;
                Intent in = new Intent(ProductInfo.this, BidsOffersPage.class);
                in.putExtra("Price", p);
                in.putExtra("Owner", o);
                in.putExtra("prodid", pid);
                startActivity(in);
            }
        });
    }

//        /***
//         *  This method downloads a image from a web server using an URL
//         * @param url: Image URL
//         * @return  d: android.graphics.drawable.Drawable;
//         * */
//        public Drawable LoadImageFromWebOperations(String url) {
//            try {
//                //Request the image to the web server
//                InputStream is = (InputStream) new URL(url).getContent();
//
//                //Generates an android.graphics.drawable.Drawable object
//                Drawable d = Drawable.createFromStream(is, "src name");
//
//                return d; }
//            catch (Exception e) {
//                return null;
//            }
//        }
}
