package com.i200513FinalProject.FinalProject1.HomeView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.Hotel.Hotel;
import com.i200513FinalProject.FinalProject1.MainActivity;
import com.i200513FinalProject.FinalProject1.NestedRecycler.HorizontalRecyclerViewModel;
import com.i200513FinalProject.FinalProject1.NestedRecycler.VerticalRecyclerViewAdapter;
import com.i200513FinalProject.FinalProject1.NestedRecycler.VerticalRecyclerViewModel;
import com.i200513FinalProject.FinalProject1.R;
import com.i200513FinalProject.FinalProject1.SignInSignUp.SignInSignUp;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    RecyclerView verticalRecycler;
    VerticalRecyclerViewAdapter verticalRecyclerAdapter;
    TextView title;
    Button booking;
    ImageView logout;

    //bottom bar
    ImageView back;
    ImageView search;
    ImageView person;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        Global.lock = true;
        HomePage.progressDialog = new ProgressDialog(HomePage.this);
        HomePage.progressDialog.setTitle("Loading hotels...");
        HomePage.progressDialog.setCancelable(false);
        HomePage.progressDialog.show();
        getHotels();
        //dummy();
        //while(HomePage.progressDialog.isShowing());
        setHotels();


        //addHotels();
        title = findViewById(R.id.TextView_Title);
        search=findViewById(R.id.ImageView_Search);
        logout=findViewById(R.id.ImageView_Logout);
        back=findViewById(R.id.ImageView_Back);
        person=findViewById(R.id.ImageView_PersonProfile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.logoutUserInDB(Global.currentSessionOwner.getPerson_Username());
                setSharedPref(Global.LOCAL_PREFERENCE_DEFAULT);
                Intent i=new Intent(HomePage.this, SignInSignUp.class);
                Global.currentSessionOwner=null;
                //Global._personToReturnFromDBByUsername=null;
                finish();
                startActivity(i);
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHotels();
                //Global.currentSessionOwner = Global._personToReturnFromDBByUsername;
                Log.d("dbug", "current User = " + Global.currentSessionOwner.getPerson_Username());
                Global.signinUserInDB(Global.currentSessionOwner.getPerson_Username());
                setSharedPref(Global.currentSessionOwner.getPerson_Username());
                getSharedPref();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, com.i200513FinalProject.FinalProject1.Search.SearchActivity.class);
                startActivity(i);
            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, com.i200513FinalProject.FinalProject1.Person.PersonProfile.class);
                startActivity(i);

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        //setSharedPref(Global.currentSessionOwner.getPerson_Username());
    }

    private void setSharedPref(String username) {

        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MainActivity.appPreferences.edit();
        editor.clear();
        editor.putString("person_Username", username);
        editor.apply();


    }

    private String getSharedPref()
    {
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = Global.LOCAL_PREFERENCE_DEFAULT;
        String person_username = MainActivity.appPreferences.getString("person_Username", defaultValue);
        Log.d("dbug", "Local user in Home = ".concat(person_username));

        return person_username;
    }


    private void setHotels() {
        Log.d("dbug", "setHotels: setting");
        List<Hotel> hotelsToDisplay = (Global._hotelsListToReturn);
        for (int i = 0; i < hotelsToDisplay.size(); i++)
            Log.d("dbug", "HomePage: " + hotelsToDisplay.get(i).printString());
        if (hotelsToDisplay.size() > 0) {
            List<HorizontalRecyclerViewModel> AllHotelsList = new ArrayList<>();
            List<HorizontalRecyclerViewModel> HotelsAbove4Rating = new ArrayList<>();

            for (int i = 0; i < hotelsToDisplay.size(); i++) {
                Log.d("dbug", hotelsToDisplay.get(i).printString());
                if (Global.stringToFloat(hotelsToDisplay.get(i).getHotel_Rating()) >= 4.0) {
                    HotelsAbove4Rating.add(new HorizontalRecyclerViewModel(hotelsToDisplay.get(i).getHotel_ID(),
                            hotelsToDisplay.get(i).getHotel_Image(),
                            hotelsToDisplay.get(i).getHotel_Name(),
                            "City: " + hotelsToDisplay.get(i).getHotel_City() + "\n" +
                                    hotelsToDisplay.get(i).getHotel_Description() + "\n" +
                                    "Pricing: " + hotelsToDisplay.get(i).getHotel_Pricing()));
                }
                AllHotelsList.add(new HorizontalRecyclerViewModel(hotelsToDisplay.get(i).getHotel_ID(),
                        hotelsToDisplay.get(i).getHotel_Image(),
                        hotelsToDisplay.get(i).getHotel_Name(),
                        "City: " + hotelsToDisplay.get(i).getHotel_City() + "\n" +
                                hotelsToDisplay.get(i).getHotel_Description() + "\n" +
                                "Pricing: " + hotelsToDisplay.get(i).getHotel_Pricing()));
            }

            List<VerticalRecyclerViewModel> categories = new ArrayList<>();
            categories.add(new VerticalRecyclerViewModel("The Best!", "Hotels above 4.0 rating", HotelsAbove4Rating));
            categories.add(new VerticalRecyclerViewModel("All Hotels", "All the hotels!", AllHotelsList));
            setVerticalRecycler(categories);
        }

    }

    private void getHotels() {
        Global.lock = true;
        Global.getHotelsFromDB();
    }

    private void setVerticalRecycler(List<VerticalRecyclerViewModel> verticalList) {
        verticalRecycler = findViewById(R.id.RecyclerView_VerticalRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        verticalRecycler.setLayoutManager(layoutManager);
        verticalRecyclerAdapter = new VerticalRecyclerViewAdapter(this, verticalList);
        verticalRecycler.setAdapter(verticalRecyclerAdapter);

    }

    private void addHotels() {
        Log.d("dbug", "adding hotels to firebase");
        List<Hotel> hotelsList = new ArrayList<>();
        //String hotel_ID, String hotel_Name, String hotel_Address,
        // String hotel_City, String hotel_PhoneNo, String hotel_Description,
        // String hotel_Rating, String hotel_Pricing, String hotel_Tags,
        // String hotel_img

        hotelsList.add(new Hotel("1", "Bismillah Hotel", "Lahore one road, Basement",
                "Lahore", "+9202210202", "Best Hotel in Town!",
                "4.5", "1 bed bedroom = 2000PKR Per night", "#Cheap #Lahore",
                "https://cdn-icons-png.flaticon.com/512/1475/1475996.png"));
        hotelsList.add(new Hotel("2", "Lovely Hotel", "F7 Markaz, Plaza 5",
                "Islamabad", "+929991292", "View of the margalla!",
                "4.2", "1 bed bedroom = 2500PKR Per night", "#Luxury #Islamabad",
                "https://cdn-icons-png.flaticon.com/512/1475/1475996.png"));
        hotelsList.add(new Hotel("3", "Mukhtar Hotel", "Gujranwala GT road, Big plaza",
                "Gujranwala", "+9206549223", "Gujran da sakoon-gah!",
                "2.1", "1 bed bedroom = 1000PKR Per night", "#Cheap #Gujranwala",
                "https://cdn-icons-png.flaticon.com/512/1475/1475996.png"));
        hotelsList.add(new Hotel("4", "PC Hotel", "Rawalpindi mall road, Plot 2",
                "Rawalpindi", "+922992910", "international, yes!",
                "4.0", "1 bed bedroom = 4000PKR Per night", "#Luxury #Rawalpindi",
                "https://cdn-icons-png.flaticon.com/512/1475/1475996.png"));
        hotelsList.add(new Hotel("5", "Bismillah Hotel", "Karachi Saddar, Plot 6",
                "Karachi", "+9202210202", "Best Hotel in Town!",
                "4.2", "1 bed bedroom = 2000PKR Per night", "#Cheap #Karachi",
                "https://cdn-icons-png.flaticon.com/512/1475/1475996.png"));

        Global.addHotelsToDB(hotelsList);
    }

    private void dummy() {
        List<HorizontalRecyclerViewModel> items = new ArrayList<>();
        items.add(new HorizontalRecyclerViewModel("1", "https://cdn-icons-png.flaticon.com/512/1475/1475996.png", "hotel 1", "hotel one"));
        items.add(new HorizontalRecyclerViewModel("2", "https://cdn-icons-png.flaticon.com/512/1946/1946788.png", "hotel 2", "hotel two"));
        items.add(new HorizontalRecyclerViewModel("3", "https://cdn-icons-png.flaticon.com/512/3009/3009489.png", "hotel 3", "hotel three"));


        List<VerticalRecyclerViewModel> categories = new ArrayList<>();
        categories.add(new VerticalRecyclerViewModel("Lahore", "lahore 1", items));
        categories.add(new VerticalRecyclerViewModel("Islamabad", "The dead city here!", items));
        categories.add(new VerticalRecyclerViewModel("Karachi", "Business hub!", items));

        setVerticalRecycler(categories);
    }


}