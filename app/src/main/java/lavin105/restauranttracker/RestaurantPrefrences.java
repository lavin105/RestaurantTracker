package lavin105.restauranttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


/*Restaurantprefrences is responsible for allowing the user to choose how they view a restaurant on the restaurantinfo activity.
* They can choose to view the phone,website, or category. All fields being their life as checked.*/

public class RestaurantPrefrences extends Activity {

    CheckBox phone,website,category;
    Button savePrefrences;
    boolean viewPhone, viewWebsite, viewCategory;
    String p,w,c;
    String c1,c2,c3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_prefrences);
        //----------------------------------------------------

        phone=findViewById(R.id.view_phone);
        website=findViewById(R.id.view_website);
        category=findViewById(R.id.view_category);
        savePrefrences=findViewById(R.id.save_prefrences);

        //----------------------------------------------------
        Intent checked= getIntent();
        c1=checked.getStringExtra("enableP");
        c2=checked.getStringExtra("enableW");
        c3=checked.getStringExtra("enableC");

        if(c1.equals("true")){
            phone.setChecked(true);
        }
        if(c2.equals("true")){
            website.setChecked(true);
        }
        if(c3.equals("true")){
            category.setChecked(true);
        }
        savePrefrences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPhone=phone.isChecked();
                viewWebsite=website.isChecked();
                viewCategory=category.isChecked();
                p=String.valueOf(viewPhone);
                w=String.valueOf(viewWebsite);
                c=String.valueOf(viewCategory);





                Intent i = new Intent();
                i.putExtra("enablePhone",p);
                i.putExtra("enableWebsite",w);
                i.putExtra("enableCategory", c);
                setResult(RESULT_OK, i);
                finish();

            }
        });
    }
}
