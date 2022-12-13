package com.i200513FinalProject.FinalProject1.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.Hotel.Hotel;
import com.i200513FinalProject.FinalProject1.Person.Person;
import com.i200513FinalProject.FinalProject1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MakeBooking extends AppCompatActivity {

    EditText dayFrom,monthFrom,yearFrom,hourFrom,minsFrom;
    EditText dayTo,monthTo,yearTo,hourTo,minsTo;
    public static Button makeBooking;
    ImageView backButton;

    Person currentUser;
    Hotel hotelToBookAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        hotelToBookAt= (Hotel)Global.dataSend1;
        currentUser=Global.currentSessionOwner;
        findViews();

        makeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBooking();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Global.dataSend1=null;
                finish();
            }
        });


    }

    private void createBooking()
    {
        String ty,tm,td,th,tmin;
        ty=yearTo.getText().toString();
        tm=monthTo.getText().toString();
        td=dayTo.getText().toString();
        th=hourTo.getText().toString();
        tmin=minsTo.getText().toString();


        String fy,fm,fd,fh,fmin;
        fy=yearFrom.getText().toString();
        fm=monthFrom.getText().toString();
        fd=dayFrom.getText().toString();
        fh=hourFrom.getText().toString();
        fmin=minsFrom.getText().toString();


        //Log.d("dbug",fy);
        //Log.d("dbug",ty);

        if(fy.isEmpty()||fm.isEmpty()||fd.isEmpty()||fh.isEmpty()||fmin.isEmpty())
            Toast.makeText(this,"Please fill \"From\" field",Toast.LENGTH_SHORT).show();
        else if(ty.isEmpty()||tm.isEmpty()||td.isEmpty()||th.isEmpty()||tmin.isEmpty())
            Toast.makeText(this,"Please fill \"To\" field",Toast.LENGTH_SHORT).show();
        else//check logic
        {
            String from="";
            String to="";
            from = from.concat(
                    fd).concat("/").concat(
                            fm.concat("/").concat(
                                    fy.concat(" ").concat(
                                        fh.concat(":").concat(
                                                fmin
                                    )
                            )
                    )
            );
            to = to.concat(
                    td).concat("/").concat(
                    tm.concat("/").concat(
                            ty.concat(" ").concat(
                                    th.concat(":").concat(
                                            tmin
                                    )
                            )
                    )
            );
            Log.d("dbug",from);
            Log.d("dbug",to);
            Toast.makeText(this,from.concat(to),Toast.LENGTH_SHORT).show();
            Booking b=new Booking(Global.currentSessionOwner.getPerson_ID(),Global.currentSessionOwner.getPerson_Username(),
                    hotelToBookAt.getHotel_ID(),hotelToBookAt.getHotel_Name(),
                    from, to,
                    "Pending"
            );
            commitBooking(b);
        }

    }


    private void makeABooking() {
        String todayDay,todayMonth,todayYear,todayHour,todayMin;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());

        Date currentDate = new Date();

        // Creating simple date formatter to 24 hours
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
        // getting the time in 24 hours format
        String timeIn24Hours = formatter.format(currentDate);

        Log.d("dbug",formatted);

        List<String> todayDate=new ArrayList<>(Global.splitDate(formatted));
        for(int i=0;i<todayDate.size();i++) Log.d("dbug",todayDate.get(i));
        todayYear=todayDate.get(0);
        todayMonth=todayDate.get(1);
        todayDay=todayDate.get(2);

        String [] array=timeIn24Hours.split(":");

        todayHour=array[0];
        todayMin=array[1];

        String ty,tm,td,th,tmin;
        ty=yearTo.getText().toString();
        tm=monthTo.getText().toString();
        td=dayTo.getText().toString();
        th=hourTo.getText().toString();
        tmin=minsTo.getText().toString();
        String completeToDateTime="";
        completeToDateTime.concat(td);
        completeToDateTime.concat("/");
        completeToDateTime.concat(tm);
        completeToDateTime.concat("/");
        completeToDateTime.concat(ty);
        completeToDateTime.concat(" ");
        completeToDateTime.concat(th);
        completeToDateTime.concat(":");
        completeToDateTime.concat(tmin);

        String fy,fm,fd,fh,fmin;
        fy=yearFrom.getText().toString();
        fm=monthFrom.getText().toString();
        fd=dayFrom.getText().toString();
        fh=hourFrom.getText().toString();
        fmin=minsFrom.getText().toString();
        String completeFromDateTime="";
        completeFromDateTime.concat(td);
        completeFromDateTime.concat("/");
        completeFromDateTime.concat(tm);
        completeFromDateTime.concat("/");
        completeFromDateTime.concat(ty);
        completeFromDateTime.concat(" ");
        completeFromDateTime.concat(th);
        completeFromDateTime.concat(":");
        completeFromDateTime.concat(tmin);

        Log.d("dbug","generated to date : ".concat(completeToDateTime));
        Log.d("dbug","generated from date: ".concat(completeFromDateTime));

        if(fy.isEmpty()||fm.isEmpty()||fd.isEmpty()||fh.isEmpty()||fmin.isEmpty())
            Toast.makeText(this,"Please fill \"From\" field",Toast.LENGTH_SHORT).show();
        else if(ty.isEmpty()||tm.isEmpty()||td.isEmpty()||th.isEmpty()||tmin.isEmpty())
            Toast.makeText(this,"Please fill \"To\" field",Toast.LENGTH_SHORT).show();
        else//check logic
        {
            int from=Global.sti(fy)+Global.sti(fm)+Global.sti(fd)+Global.sti(fh)+Global.sti(fmin);
            int to=Global.sti(ty)+Global.sti(tm)+Global.sti(td)+Global.sti(th)+Global.sti(tmin);
            int today=Global.sti(todayYear)+Global.sti(todayMonth)+Global.sti(todayDay)+Global.sti(todayHour)+Global.sti(todayMin);

            if(from>today)
            {
                if(to>from)
                {
                    Booking b=new Booking(currentUser.getPerson_ID(),currentUser.getPerson_Username(),
                            hotelToBookAt.getHotel_ID(),hotelToBookAt.getHotel_Name(),
                            completeFromDateTime,completeToDateTime,"pending");
                    commitBooking(b);
                }
                else
                {
                    Toast.makeText(this,"\"To\" should be after \"From\"",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this,"\"From\" should be after \"Today's date\"",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void commitBooking(Booking b)
    {
        Global.addBookingToDB(b);

    }

    private void findViews() {
        dayFrom=findViewById(R.id.Date_dayFrom);
        monthFrom=findViewById(R.id.Date_MonthFrom);
        yearFrom=findViewById(R.id.Date_YearFrom);
        hourFrom=findViewById(R.id.Time_HourFrom);
        minsFrom=findViewById(R.id.Time_MinFrom);

        dayTo=findViewById(R.id.Date_dayTo);
        monthTo=findViewById(R.id.Date_MonthTo);
        yearTo=findViewById(R.id.Date_YearTo);
        hourTo=findViewById(R.id.Time_HourTo);
        minsTo=findViewById(R.id.Time_MinTo);

        backButton=findViewById(R.id.ImageView_Back);
        makeBooking=findViewById(R.id.Button_book);
    }
}