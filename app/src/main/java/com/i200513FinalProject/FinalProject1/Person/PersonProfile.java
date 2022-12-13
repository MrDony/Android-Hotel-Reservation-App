package com.i200513FinalProject.FinalProject1.Person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.i200513FinalProject.FinalProject1.Booking.Booking;
import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.R;

import java.util.ArrayList;
import java.util.List;

public class PersonProfile extends AppCompatActivity {

    ImageView profilePic;
    TextView username,email,cnic,password;

    ImageView back;

    RecyclerView bookings;
    PersonBookingRecyclerAdapter recyclerAdapter;
    List<Booking> personBookings;

    public static ProgressDialog personProfileProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);
        findViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Person p= Global.currentSessionOwner;

        username.setText(p.getPerson_Username());
        email.setText(p.getPerson_Email());
        cnic.setText(p.getPerson_Cnic());
        password.setText(p.getPerson_Password());

        PersonProfile.personProfileProgressDialog=new ProgressDialog(PersonProfile.this);
        PersonProfile.personProfileProgressDialog.setTitle("Loading Bookings...");
        PersonProfile.personProfileProgressDialog.setCancelable(false);
        PersonProfile.personProfileProgressDialog.show();

        Global.getBookingsOfUser(p.getPerson_Username());


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personBookings=Global._bookingsListToReturnByUsername;
                if(personBookings.size()>0)//personBookings!=null &&
                {
                    List<PersonBookingRecyclerModel> recyclerModelList=new ArrayList<>();
                    for(int i =0;i<personBookings.size();i++)
                    {
                        recyclerModelList.add(new PersonBookingRecyclerModel(
                                personBookings.get(i).getBooking_HotelName(),
                                personBookings.get(i).getBooking_StartDateTime(),
                                personBookings.get(i).getBooking_EndDateTime(),
                                personBookings.get(i).getBooking_Status()));
                    }
                    setRecycler(recyclerModelList);
                }
                else
                {
                    Toast.makeText(PersonProfile.this,"No bookings",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findViews() {
        profilePic=findViewById(R.id.ImageView_PersonImage);
        username=findViewById(R.id.TextView_PersonUsername);
        email=findViewById(R.id.TextView_EmailAddress);
        cnic=findViewById(R.id.TextView_cnic);
        bookings=findViewById(R.id.RecyclerView_BookingsRecycler);
        back=findViewById(R.id.ImageView_Back);
        password=findViewById(R.id.TextView_Password);

    }

    private void setRecycler(List<PersonBookingRecyclerModel> bookingsList)
    {
        for(int i=0;i<bookingsList.size();i++)
        {
            Log.d("dbug",bookingsList.get(i).getHotelName());
        }
        //RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        //        recyclerView.setLayoutManager(layoutManager);
        //        recyclerAdapter=new SearchRecyclerAdapter(this,searchedItems);
        //        recyclerView.setAdapter(recyclerAdapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(PersonProfile.this);
        bookings.setLayoutManager(layoutManager);
        recyclerAdapter=new PersonBookingRecyclerAdapter(PersonProfile.this, bookingsList);
        bookings.setAdapter(recyclerAdapter);
    }
}