package lavin105.restauranttracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


/*This class is responsible for populating a custom list with an imageview and two textviews. This view is then
*used to populate the main list view on the restaurntlist activity*/

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

private Context context;
int resourse;

    public RestaurantListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Restaurant> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourse=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String logo, name,phone,website,category;
        String rating;
        logo=getItem(position).getLogo();
        name=getItem(position).getName();
        rating=getItem(position).getRating();
        phone=getItem(position).getPhone();
        website=getItem(position).getWebsite();
        category=getItem(position).getCategory();
        Restaurant r = new Restaurant(name,phone,website,category,logo,rating);
        LayoutInflater inflater =LayoutInflater.from(context);
        convertView=inflater.inflate(resourse,parent,false);
        TextView theName,theRating;
        ImageView theLogo;
        theLogo=convertView.findViewById(R.id.logo);
        theName=(TextView) convertView.findViewById(R.id.name);
        theRating=(TextView) convertView.findViewById(R.id.rating);


        //this code is responsible for loading the image using the picasso library
        if(logo.equals("")){
            theLogo.setImageResource(R.mipmap.ic_launcher_round);
        }else{
            try{
                Picasso.get().load(logo).into(theLogo);

            }catch(Exception e){
                e.printStackTrace();
                theLogo.setImageResource(R.mipmap.ic_launcher_round);

            }

        }
        theName.setText(name);
        theRating.setText(rating);

        return convertView;

    }






}
