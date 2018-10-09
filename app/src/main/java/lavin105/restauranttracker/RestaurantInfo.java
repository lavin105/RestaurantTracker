package lavin105.restauranttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/*RestaurantInfo is responsible for housing information about a particular restaurant, including its name, phone number,
* website, and rating. The rating can be modified and saved from the RestaurantInfo activity.*/


public class RestaurantInfo extends Activity {
    TextView name, number, website,category, phoneTitle, webTitle, categoryTitle;
    RatingBar rating;
    String restName, restNumber, restWeb, restCategory, restRating, restLogo;
    ImageView picture;
    float actualRating;
    Button bck;
    Restaurant r;
    String pos;
    String yesPhone, yesWeb, yesCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        //---------------------------------------------------

        name=findViewById(R.id.name);
        number=findViewById(R.id.phone);
        website=findViewById(R.id.website);
        category=findViewById(R.id.category);
        rating=findViewById(R.id.rating);
        bck=findViewById(R.id.back);
        number.setMovementMethod(LinkMovementMethod.getInstance());
        phoneTitle=findViewById(R.id.p);
        webTitle=findViewById(R.id.w);
        categoryTitle=findViewById(R.id.c);
        picture=findViewById(R.id.logo_restaurant_info);

        //---------------------------------------------------

        Intent i = getIntent();
        r = (Restaurant) i.getSerializableExtra("restaurant");
        restName=r.getName();
        restNumber=r.getPhone();
        restWeb=r.getWebsite();
        restCategory=r.getCategory();
        restRating=r.getRating();
        restLogo=r.getLogo();
        pos=i.getStringExtra("position");
        yesPhone=i.getStringExtra("viewPhone");
        yesWeb=i.getStringExtra("viewWebsite");
        yesCategory=i.getStringExtra("viewCategory");

        //displaying the image using Picasso
        if(restLogo.equals("")){
            picture.setImageResource(R.mipmap.ic_launcher_round);
        }else{
            try{
                Picasso.get().load(restLogo).into(picture);

            }catch(Exception e){
                e.printStackTrace();
                picture.setImageResource(R.mipmap.ic_launcher_round);

            }

        }


        //change the view based on the preferences selected

        if(yesPhone.equals("false")){
            phoneTitle.setVisibility(View.INVISIBLE);
            number.setVisibility(View.INVISIBLE);

        }
        if(yesWeb.equals("false")){
            webTitle.setVisibility(View.INVISIBLE);
            website.setVisibility(View.INVISIBLE);
        }
        if(yesCategory.equals("false")){
            categoryTitle.setVisibility(View.INVISIBLE);
            category.setVisibility(View.INVISIBLE);
        }

        if(restRating.equals("")){
            restRating="0.0";
        }
        if(restRating.equals("1/2★")){
            restRating="0.5";

        }
        if(restRating.equals("★")){
            restRating="1.0";

        }
        if(restRating.equals("★ 1/2★")){
            restRating="1.5";

        }
        if(restRating.equals("★★")){
            restRating="2.0";

        }
        if(restRating.equals("★★ 1/2★")){
            restRating="2.5";

        }
        if(restRating.equals("★★★")){
            restRating="3.0";

        }
        if(restRating.equals("★★★ 1/2★")){
            restRating="3.5";

        }
        if(restRating.equals("★★★★")){
            restRating="4.0";

        }
        if(restRating.equals("★★★★ 1/2★")){
            restRating="4.5";

        }
        if(restRating.equals("★★★★★")){
            restRating="5.0";

        }

        actualRating=Float.valueOf(restRating);

        name.setText(restName);
        number.setText(restNumber);
        website.setText(restWeb);
        category.setText(restCategory);
        rating.setRating(actualRating);


        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tempRating = rating.getRating();
                String newRating=Float.toString(tempRating);
                if(newRating.equals("0.0")){
                    newRating="";
                }
                if(newRating.equals("0.5")){
                    newRating="1/2★";

                }
                if(newRating.equals("1.0")){
                    newRating="★";

                }
                if(newRating.equals("1.5")){
                    newRating="★ 1/2★";

                }
                if(newRating.equals("2.0")){
                    newRating="★★";

                }
                if(newRating.equals("2.5")){
                    newRating="★★ 1/2★";

                }
                if(newRating.equals("3.0")){
                    newRating="★★★";

                }
                if(newRating.equals("3.5")){
                    newRating="★★★ 1/2★";

                }
                if(newRating.equals("4.0")){
                    newRating="★★★★";

                }
                if(newRating.equals("4.5")){
                    newRating="★★★★ 1/2★";

                }
                if(newRating.equals("5.0")){
                    newRating="★★★★★";

                }
                r.setRating(newRating);
                //passing back data to the restaurant list
                Intent z = new Intent();
                z.putExtra("key", pos);
                z.putExtra("restaurant", r);
                z.putExtra("pp",yesPhone);
                z.putExtra("ww",yesWeb);
                z.putExtra("cc", yesCategory);
                setResult(RESULT_OK,z);
                finish();
            }
        });


    }
}
