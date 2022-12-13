package com.i200513FinalProject.FinalProject1.Hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.i200513FinalProject.FinalProject1.Booking.MakeBooking;
import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.R;
import com.squareup.picasso.Picasso;

public class HotelProfile extends AppCompatActivity {

    ImageView img;
    TextView name,address,city,phoneno,description,rating,pricing,tags;
    ImageView back,call;
    Hotel hotel;
    Button makeBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_profile);
        findviews();
        hotel=(Hotel)Global.dataSend1;
        Log.d("dbug","data got = "+hotel.printString());
        setViews();


        makeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("dbug","switching intent");
                Intent i=new Intent(HotelProfile.this,
                        com.i200513FinalProject.FinalProject1.Booking.MakeBooking.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCallToHotel();
            }
        });


    }

    private void makeCallToHotel() {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:".concat(phoneno.getText().toString())));//change the number
        startActivity(callIntent);
    }

    private void setViews() {
        String imgUrl=hotel.getHotel_Image();
        Picasso.get().load(imgUrl).into(img);
        name.setText(hotel.getHotel_Name());
        address.setText(hotel.getHotel_Address());
        city.setText(hotel.getHotel_City());
        phoneno.setText(hotel.getHotel_PhoneNo());
        description.setText(hotel.getHotel_Description());
        rating.setText("Rating: "+hotel.getHotel_Rating()+"/5");
        pricing.setText("Pricing:\n"+hotel.getHotel_Pricing());
        tags.setText(hotel.getHotel_Tags());

    }

    private void findviews() {
        img=findViewById(R.id.ImageView_HotelImage);
        name=findViewById(R.id.TextView_HotelName);
        address=findViewById(R.id.TextView_Address);
        city=findViewById(R.id.TextView_City);
        phoneno=findViewById(R.id.TextView_PhoneNo);
        description=findViewById(R.id.TextView_Description);
        rating=findViewById(R.id.TextView_Rating);
        pricing=findViewById(R.id.TextView_Pricing);
        tags=findViewById(R.id.TextView_Tags);
        makeBooking=findViewById(R.id.Button_MakeBooking);
        back=findViewById(R.id.ImageView_Back);
        call=findViewById(R.id.ImageView_Call);
    }
}