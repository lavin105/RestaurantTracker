package lavin105.restauranttracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;


/*AddRestaurant activity is responsible for adding a restaurant with certain attributes. I created an extra field for logo
* which takes a url in order to grab an image from the internet using the Picasso library. Clear button returns all edittexts to
* their original state. Cancel takes you back to the main restaurnt list activity*/

public class AddRestaurant extends Activity {

    EditText name,phone,website, link;
    Button cancel, add, toChrome, clearFields;
    RatingBar rating;
    Spinner restaurantTypes;
    ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant);

        //----------------------------------------------------------
        //Java & XML binding
        restaurantTypes=findViewById(R.id.add_restaurant_type_spinner);
        name=findViewById(R.id.add_name_edittext);
        phone=findViewById(R.id.add_phone_edittext);
        website=findViewById(R.id.add_website_edittext);
        rating=findViewById(R.id.rating);
        add=findViewById(R.id.add_restaurant);
        cancel=findViewById(R.id.cancel_add_restaurant);
        link=findViewById(R.id.url);
        toChrome=findViewById(R.id.launchChrome);
        clearFields=findViewById(R.id.clear);

        //----------------------------------------------------------

        spinnerAdapter=new ArrayAdapter<String>(AddRestaurant.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.restaurant_types));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantTypes.setAdapter(spinnerAdapter);

        //button that returns you to the reataurant list
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED);
                finish();
            }
        });
        //button that clears all edittexts
        clearFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                phone.setText("");
                website.setText("");
                rating.setRating(0);
                restaurantTypes.setSelection(0);
                link.setText("");
            }
        });

        //button that launches a web browser
        toChrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBrowser= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(toBrowser);
            }
        });

        //add button to return to the restaurant list with data
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurantName=name.getText().toString();
                String restaurantNumber=phone.getText().toString();
                String restaurantWeb=website.getText().toString();
                String restaurantCategory=restaurantTypes.getSelectedItem().toString();
                float restaurantRating=rating.getRating();
                String theRating=Float.toString(restaurantRating);
                String theUrl=link.getText().toString();

                if (restaurantName.equals("")||restaurantNumber.equals("")||restaurantCategory==null||restaurantWeb.equals("")||theRating==null){
                    Toast.makeText(AddRestaurant.this,"Fields Missing",Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent("lavin105.restauranttracker");
                    i.putExtra("name", restaurantName);
                    i.putExtra("number",restaurantNumber);
                    i.putExtra("website",restaurantWeb);
                    i.putExtra("category", restaurantCategory);
                    i.putExtra("rating", theRating);
                    i.putExtra("url",theUrl);
                    setResult(RESULT_OK,i);
                    if(restaurantRating==5.0){
                        sendBroadcast(i);
                    }
                    finish();
                }




            }
        });

    }

}
