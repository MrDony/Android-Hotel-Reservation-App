package com.i200513FinalProject.FinalProject1.Admin;

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
import android.widget.ImageView;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.HomeView.HomePage;
import com.i200513FinalProject.FinalProject1.MainActivity;
import com.i200513FinalProject.FinalProject1.R;
import com.i200513FinalProject.FinalProject1.SignInSignUp.SignInSignUp;

import java.util.List;

public class adminView extends AppCompatActivity {

    public static RecyclerView pending;
    public static AdminRecyclerAdapter pendingAdapter;
    public static RecyclerView booked;
    public static AdminRecyclerAdapter bookedAdapter;

    public static ProgressDialog settingPending=null;
    public static ProgressDialog settingBooked=null;

    public static Context adminViewContext;

    ImageView back,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        findViews();

        refreshAdminPage(this);


        findViewById(R.id.TextView_Title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshAdminPage(adminView.adminViewContext);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.logoutUserInDB(Global.currentSessionOwner.getPerson_Username());
                setSharedPref(Global.LOCAL_PREFERENCE_DEFAULT);
                Intent i=new Intent(adminView.this, SignInSignUp.class);
                Global.currentSessionOwner=null;
                //Global._personToReturnFromDBByUsername=null;
                finish();
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public static void refreshAdminPage(Context context)
    {
        Log.d("dbug","adminView: refreshAdminPage()");
        Global.setAdminPendingBookings(context);
        settingPending=new ProgressDialog(context);
        settingPending.setTitle("Setting pending...");
        settingPending.setCancelable(false);
        settingPending.show();

        Global.setAdminBookedBookings(context);
        settingBooked=new ProgressDialog(context);
        settingBooked.setTitle("Setting Booked...");
        settingBooked.setCancelable(false);
        settingBooked.show();
    }

    public static void setPending(List<AdminRecyclerModel> pendingList, Context context)
    {
        Log.d("dbug","adminView: setPending()");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        pending.setLayoutManager(layoutManager);
        pendingAdapter=new AdminRecyclerAdapter(context,pendingList);
        pending.setAdapter(pendingAdapter);

    }
    public static void setBooked(List<AdminRecyclerModel> bookedList, Context context)
    {
        Log.d("dbug","adminView: setBooked()");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        booked.setLayoutManager(layoutManager);
        bookedAdapter=new AdminRecyclerAdapter(context,bookedList);
        booked.setAdapter(bookedAdapter);
    }

    private void findViews() {
        pending=findViewById(R.id.RecyclerView_Pending);
        booked=findViewById(R.id.RecyclerView_Booked);
        back=findViewById(R.id.ImageView_Back);
        logout=findViewById(R.id.ImageView_Logout);
        adminViewContext=this;
    }


    private String getSharedPref()
    {
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = Global.LOCAL_PREFERENCE_DEFAULT;
        String person_username = MainActivity.appPreferences.getString("person_Username", defaultValue);
        //Log.d("dbug", "Local user in signinsignup = ".concat(person_username));

        return person_username;
    }

    public void setSharedPref(String username) {

        Log.d("dbug","SignInSignUp setting pref to = ".concat(username));
        SharedPreferences.Editor editor = MainActivity.appPreferences.edit();
        editor.clear();
        editor.putString("person_Username", username);
        editor.apply();
        Log.d("dbug","SignInSignUp pref set to = ".concat(getSharedPref()));
    }
}