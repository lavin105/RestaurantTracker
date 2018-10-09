package lavin105.restauranttracker;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/*RestaurantList is responsible for displaying a list of restaurants as well as the options menu
* that allows a user to either add a restaurant, load existing restuarants from a text file, clear all restaurants from the
* list, and to navigate to the preferences page.
* This class acts as the central hub of the application and is the starting location for the app. A custom
* list adapter is used in order to display multiple columns in the listview.*/



public class RestaurantList extends AppCompatActivity {
    ListView restaurantList;
    ArrayList<Restaurant> restaurantArrayList,sortedList;
    Spinner menu;
    ArrayAdapter<String> spinnerAdapter;
    RestaurantListAdapter restaurantAdapter;
    int REQUEST_CODE_ADD_RESTAURANT=1;
    int REQUEST_CODE_RESTAURANT_INFO=2;
    int REQUEST_CODE_PREFRENCES=3;
    String name,phone,website,category,logo,rating;
    Restaurant restaurant;
    Toolbar bar;
    String enablePhone="true";
    String enableWebsite="true";
    String enableCategory="true";
    Button sortAZ, sortRating;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restuarant_list);
        //------------------------------------------------------
        restaurantList=findViewById(R.id.restaurant_list);
        menu=findViewById(R.id.menu);
        bar=findViewById(R.id.bar);
        sortAZ=findViewById(R.id.sortAtoZ);
        sortRating=findViewById(R.id.sortRating);

        //------------------------------------------------------
        setSupportActionBar(bar);
        restaurantArrayList= new ArrayList<Restaurant>();
        restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
        restaurantList.setAdapter(restaurantAdapter);
        spinnerAdapter=new ArrayAdapter<String>(RestaurantList.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.menu));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(spinnerAdapter);


        AlertDialog.Builder alert= new AlertDialog.Builder(RestaurantList.this);
        alert.setTitle("★★★★★FIVE STAR RESTAURANT★★★★★");
        alert.setMessage("Would you like to see this restaurant now?");


        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restaurantList.performItemClick(restaurantList.getChildAt(restaurantArrayList.size()-1), restaurantArrayList.size()-1, restaurantList.getItemIdAtPosition(restaurantArrayList.size()-1));
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog theAlert=alert.create();

        //BROADCAST RECEIVER
        IntentFilter filter = new IntentFilter();
        filter.addAction("lavin105.restauranttracker");
         BroadcastReceiver receiver = new BroadcastReceiver() {
            //if 5 stars create a clickable message
            @Override
            public void onReceive(Context context, Intent intent) {
                String value =  intent.getExtras().getString("rating");
                if(value.equals("5.0")){
                   theAlert.show();
                }
            }
        };
        registerReceiver(receiver, filter);



        sortAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedList= new ArrayList<Restaurant>(restaurantArrayList);

                Collections.sort(sortedList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant o1, Restaurant o2) {
                        return o1.name.compareTo(o2.name);
                    }
                });
                restaurantArrayList=sortedList;
                restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();

                if (restaurantArrayList.size()==0){
                    Toast.makeText(RestaurantList.this, "Nothing to Sort",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RestaurantList.this, "Sorted Alphabetically",Toast.LENGTH_SHORT).show();

                }
            }
        });

        //sorting restaurants by rating
        sortRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedList= new ArrayList<Restaurant>(restaurantArrayList);

                Collections.sort(sortedList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant o1, Restaurant o2) {
                        if (o1.rating.equals("★★★★★")){
                            o1.rating="5.0";
                        }
                        if (o1.rating.equals("★★★★ 1/2★")){
                            o1.rating="4.5";
                        } if (o1.rating.equals("★★★★")){
                            o1.rating="4.0";
                        } if (o1.rating.equals("★★★ 1/2★")){
                            o1.rating="3.5";
                        } if (o1.rating.equals("★★★")){
                            o1.rating="3.0";
                        } if (o1.rating.equals("★★ 1/2★")){
                            o1.rating="2.5";
                        } if (o1.rating.equals("★★")){
                            o1.rating="2.0";
                        } if (o1.rating.equals("★ 1/2★")){
                            o1.rating="1.5";
                        } if (o1.rating.equals("★")){
                            o1.rating="1.0";
                        } if (o1.rating.equals("1/2★")){
                            o1.rating="0.5";
                        } if (o1.rating.equals("★")){
                            o1.rating="0.0";
                        }
                        if (o2.rating.equals("★★★★★")){
                            o2.rating="5.0";
                        }
                        if (o2.rating.equals("★★★★ 1/2★")){
                            o2.rating="4.5";
                        } if (o2.rating.equals("★★★★")){
                            o2.rating="4.0";
                        } if (o2.rating.equals("★★★ 1/2★")){
                            o2.rating="3.5";
                        } if (o2.rating.equals("★★★")){
                            o2.rating="3.0";
                        } if (o2.rating.equals("★★ 1/2★")){
                            o2.rating="2.5";
                        } if (o2.rating.equals("★★")){
                            o2.rating="2.0";
                        } if (o2.rating.equals("★ 1/2★")){
                            o2.rating="1.5";
                        } if (o2.rating.equals("★")){
                            o2.rating="1.0";
                        } if (o2.rating.equals("1/2★")){
                            o2.rating="0.5";
                        } if (o2.rating.equals("★")){
                            o2.rating="0.0";
                        }
                        int comparison=Float.valueOf(o2.rating).compareTo(Float.valueOf(o1.rating));

                        if(o1.rating.equals("0.0")){
                            o1.rating="";
                        }
                        if(o1.rating.equals("0.5")){
                            o1.rating="1/2★";

                        }
                        if(o1.rating.equals("1.0")){
                            o1.rating="★";

                        }
                        if(o1.rating.equals("1.5")){
                            o1.rating="★ 1/2★";

                        }
                        if(o1.rating.equals("2.0")){
                            o1.rating="★★";

                        }
                        if(o1.rating.equals("2.5")){
                            o1.rating="★★ 1/2★";

                        }
                        if(o1.rating.equals("3.0")){
                            o1.rating="★★★";

                        }
                        if(o1.rating.equals("3.5")){
                            o1.rating="★★★ 1/2★";

                        }
                        if(o1.rating.equals("4.0")){
                            o1.rating="★★★★";

                        }
                        if(o1.rating.equals("4.5")){
                            o1.rating="★★★★ 1/2★";

                        }
                        if(o1.rating.equals("5.0")){
                            o1.rating="★★★★★";

                        }
                        if(o2.rating.equals("0.0")){
                            o2.rating="";
                        }
                        if(o2.rating.equals("0.5")){
                            o2.rating="1/2★";

                        }
                        if(o2.rating.equals("1.0")){
                            o2.rating="★";

                        }
                        if(o2.rating.equals("1.5")){
                            o2.rating="★ 1/2★";

                        }
                        if(o2.rating.equals("2.0")){
                            o2.rating="★★";

                        }
                        if(o2.rating.equals("2.5")){
                            o2.rating="★★ 1/2★";

                        }
                        if(o2.rating.equals("3.0")){
                            o2.rating="★★★";

                        }
                        if(o2.rating.equals("3.5")){
                            o2.rating="★★★ 1/2★";

                        }
                        if(o2.rating.equals("4.0")){
                            o2.rating="★★★★";

                        }
                        if(o2.rating.equals("4.5")){
                            o2.rating="★★★★ 1/2★";

                        }
                        if(o2.rating.equals("5.0")){
                            o2.rating="★★★★★";

                        }


                        return comparison;
                    }
                });
                restaurantArrayList=sortedList;
                restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();
                if (restaurantArrayList.size()==0){
                    Toast.makeText(RestaurantList.this, "Nothing to Sort",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RestaurantList.this, "Sorted by Rating",Toast.LENGTH_SHORT).show();

                }
            }
        });


        //extra menu
        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //menu
                }
                if (position==1){
                    //add
                    Intent i = new Intent(RestaurantList.this, AddRestaurant.class);
                    startActivityForResult(i,REQUEST_CODE_ADD_RESTAURANT);

                }
                if (position==2){
                    restaurantArrayList.clear();
                    restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                    restaurantList.setAdapter(restaurantAdapter);
                    restaurantAdapter.notifyDataSetChanged();
                    menu.setSelection(0);

                }
                if (position==3){
                    //load
                           InputStream inputStream = getResources().openRawResource(R.raw.restaurants);
                           BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));

                           try{
                               String eachline = bufferedReader.readLine();
                               while (eachline != null) {
                                   // `the words in the file are separated by space`, so to get each words
                                   String[] restaurants = eachline.split(",");
                                   if(restaurants[5].equals("0.0")){
                                       restaurants[5]="";
                                   }
                                   if(restaurants[5].equals("0.5")){
                                       restaurants[5]="1/2★";

                                   }
                                   if(restaurants[5].equals("1.0")){
                                       restaurants[5]="★";

                                   }
                                   if(restaurants[5].equals("1.5")){
                                       restaurants[5]="★ 1/2★";

                                   }
                                   if(restaurants[5].equals("2.0")){
                                       restaurants[5]="★★";

                                   }
                                   if(restaurants[5].equals("2.5")){
                                       restaurants[5]="★★ 1/2★";

                                   }
                                   if(restaurants[5].equals("3.0")){
                                       restaurants[5]="★★★";

                                   }
                                   if(restaurants[5].equals("3.5")){
                                       restaurants[5]="★★★ 1/2★";

                                   }
                                   if(restaurants[5].equals("4.0")){
                                       restaurants[5]="★★★★";

                                   }
                                   if(restaurants[5].equals("4.5")){
                                       restaurants[5]="★★★★ 1/2★";

                                   }
                                   if(restaurants[5].equals("5.0")){
                                       restaurants[5]="★★★★★";

                                   }
                                   Restaurant r = new Restaurant(restaurants[0],restaurants[1],restaurants[2],restaurants[3],restaurants[4],restaurants[5]);
                                   restaurantArrayList.add(r);
                                   eachline=bufferedReader.readLine();

                               }
                           }catch (IOException e){
                               e.printStackTrace();
                           }

                        restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                        restaurantList.setAdapter(restaurantAdapter);
                        restaurantAdapter.notifyDataSetChanged();
                        menu.setSelection(0);



                }
                if (position==4){
                    //prefrernces
                    Intent prefrences = new Intent(RestaurantList.this,RestaurantPrefrences.class);
                    prefrences.putExtra("enableP",enablePhone);
                    prefrences.putExtra("enableW", enableWebsite);
                    prefrences.putExtra("enableC",enableCategory);
                    startActivityForResult(prefrences,REQUEST_CODE_PREFRENCES);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //send you to restaurantinfo by passing a restaurant to be viewed
        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r=(Restaurant) restaurantList.getItemAtPosition(position);
                Intent restaurantInfo= new Intent(RestaurantList.this,RestaurantInfo.class);
                restaurantInfo.putExtra("restaurant",r);
                String thePosition=Integer.toString(position);
                restaurantInfo.putExtra("position",thePosition);
                restaurantInfo.putExtra("viewPhone",enablePhone);
                restaurantInfo.putExtra("viewWebsite", enableWebsite);
                restaurantInfo.putExtra("viewCategory",enableCategory);
                startActivityForResult(restaurantInfo,REQUEST_CODE_RESTAURANT_INFO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD_RESTAURANT){
            if (resultCode==RESULT_OK){

                name=data.getStringExtra("name");
                phone=data.getStringExtra("number");
                website=data.getStringExtra("website");
                category=data.getStringExtra("category");
                rating=data.getStringExtra("rating");
                logo=data.getStringExtra("url");
                System.out.println(logo);

                if(rating.equals("0.0")){
                    rating="";
                }
                if(rating.equals("0.5")){
                    rating="1/2★";

                }
                if(rating.equals("1.0")){
                    rating="★";

                }
                if(rating.equals("1.5")){
                    rating="★ 1/2★";

                }
                if(rating.equals("2.0")){
                    rating="★★";

                }
                if(rating.equals("2.5")){
                    rating="★★ 1/2★";

                }
                if(rating.equals("3.0")){
                    rating="★★★";

                }
                if(rating.equals("3.5")){
                    rating="★★★ 1/2★";

                }
                if(rating.equals("4.0")){
                    rating="★★★★";

                }
                if(rating.equals("4.5")){
                    rating="★★★★ 1/2★";

                }
                if(rating.equals("5.0")){
                    rating="★★★★★";

                }

                restaurant=new Restaurant(name,phone,website,category,logo,rating);
                restaurantArrayList.add(restaurant);
                restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();
                menu.setSelection(0);



            }
            if (resultCode==RESULT_CANCELED){
                menu.setSelection(0);
            }
        }
        if (requestCode==REQUEST_CODE_RESTAURANT_INFO){
            if (resultCode==RESULT_OK){

                restaurant=(Restaurant) data.getSerializableExtra("restaurant");
                String key=data.getStringExtra("key");
                int changeKey=Integer.parseInt(key);
                restaurantArrayList.set(changeKey,restaurant);
                restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();
                menu.setSelection(0);
                enablePhone=data.getStringExtra("pp");
                enableCategory=data.getStringExtra("cc");
                enableWebsite=data.getStringExtra("ww");


            }
        }
        if (requestCode==REQUEST_CODE_PREFRENCES){
            if (resultCode==RESULT_OK){
                enablePhone=data.getStringExtra("enablePhone");
                enableWebsite=data.getStringExtra("enableWebsite");
                enableCategory=data.getStringExtra("enableCategory");
                menu.setSelection(0);


            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_menu:
                Intent i = new Intent(RestaurantList.this, AddRestaurant.class);
                startActivityForResult(i,REQUEST_CODE_ADD_RESTAURANT);
                break;
            case R.id.clear_menu:
                restaurantArrayList.clear();
                restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();
                menu.setSelection(0);
                break;
            case R.id.load_menu:
                //do loading
                InputStream inputStream = getResources().openRawResource(R.raw.restaurants);
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));

                try{
                    String eachline = bufferedReader.readLine();
                    while (eachline != null) {
                        // `the words in the file are separated by space`, so to get each words
                        String[] restaurants = eachline.split(",");
                        if(restaurants[5].equals("0.0")){
                            restaurants[5]="";
                        }
                        if(restaurants[5].equals("0.5")){
                            restaurants[5]="1/2★";

                        }
                        if(restaurants[5].equals("1.0")){
                            restaurants[5]="★";

                        }
                        if(restaurants[5].equals("1.5")){
                            restaurants[5]="★ 1/2★";

                        }
                        if(restaurants[5].equals("2.0")){
                            restaurants[5]="★★";

                        }
                        if(restaurants[5].equals("2.5")){
                            restaurants[5]="★★ 1/2★";

                        }
                        if(restaurants[5].equals("3.0")){
                            restaurants[5]="★★★";

                        }
                        if(restaurants[5].equals("3.5")){
                            restaurants[5]="★★★ 1/2★";

                        }
                        if(restaurants[5].equals("4.0")){
                            restaurants[5]="★★★★";

                        }
                        if(restaurants[5].equals("4.5")){
                            restaurants[5]="★★★★ 1/2★";

                        }
                        if(restaurants[5].equals("5.0")){
                            restaurants[5]="★★★★★";

                        }
                        Restaurant r = new Restaurant(restaurants[0],restaurants[1],restaurants[2],restaurants[3],restaurants[4],restaurants[5]);
                        restaurantArrayList.add(r);
                        eachline=bufferedReader.readLine();

                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                restaurantAdapter= new RestaurantListAdapter(RestaurantList.this, R.layout.custom_list,restaurantArrayList);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();
                menu.setSelection(0);
                break;
            case R.id.prefrences_menu:
                Intent prefrences = new Intent(RestaurantList.this,RestaurantPrefrences.class);
                prefrences.putExtra("enableP",enablePhone);
                prefrences.putExtra("enableW", enableWebsite);
                prefrences.putExtra("enableC",enableCategory);
                startActivityForResult(prefrences,REQUEST_CODE_PREFRENCES);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}
