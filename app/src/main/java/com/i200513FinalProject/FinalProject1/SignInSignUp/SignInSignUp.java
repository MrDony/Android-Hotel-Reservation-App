package com.i200513FinalProject.FinalProject1.SignInSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.HomeView.HomePage;
import com.i200513FinalProject.FinalProject1.MainActivity;
import com.i200513FinalProject.FinalProject1.Person.Person;
import com.i200513FinalProject.FinalProject1.R;

public class SignInSignUp extends AppCompatActivity {

    EditText siUsername,siPassword;
    Button siButton;
    TextView siWarning,siTitle;
    Boolean trySignIn=false;

    EditText suEmail,suUsername,suPassword,suCnic;
    Button suButton;
    TextView suWarning,suTitle;
    Boolean trySignUp=false;

    ImageView back;

    public static ProgressDialog progressDialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);
        findViews();
        setSharedPref(Global.LOCAL_PREFERENCE_DEFAULT);


        siButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPressed();
            }
        });

        suButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPressed();
            }
        });

        suTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trySignUp)
                {
                    Person p=Global._personToReturnFromDBByUsername;

                    if(p!=null)
                        Log.d("dbug","Person got from db signUp = ".concat(p.printString()));
                    else
                        Log.d("dbug","person not found");
                    if(p==null)
                    {

                        Person add=new Person(Global.LOCAL_APP_TOKEN,
                                suEmail.getText().toString(),
                                suUsername.getText().toString(),
                                "imgUrl",
                                suPassword.getText().toString(),
                                suCnic.getText().toString());

                        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = MainActivity.appPreferences.edit();
                        editor.clear();
                        editor.putString("person_Username", suUsername.getText().toString());
                        editor.apply();

                        Log.d("dbug","setting pref signup = ".concat(getSharedPref()));
                        Global.currentSessionOwner=add;

                        Global.addPersonToDB(add);


                        Intent i=new Intent(SignInSignUp.this, HomePage.class);
                        finish();
                        startActivity(i);

                    }
                    else
                    {
                        trySignUp=false;
                        suWarning.setText("Username already exists!");
                    }
                }
            }
        });

        siTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trySignIn)
                {
                    Person p=Global._personToReturnFromDBByUsernamePassword;
                    Log.d("dbug","Person username = ".concat(p.getPerson_Username()));
                    if(p.getPerson_Username().equals("admin"))
                    {
                        SharedPreferences.Editor editor = MainActivity.appPreferences.edit();
                        editor.clear();
                        editor.putString("person_Username", p.getPerson_Username());
                        editor.apply();
                        Log.d("dbug","setting pref signin =".concat(getSharedPref()));

                        Global.currentSessionOwner=p;

                        Intent i=new Intent(SignInSignUp.this,com.i200513FinalProject.FinalProject1.Admin.adminView.class);
                        finish();
                        startActivity(i);
                    }
                    else if(p!=null)
                    {
                        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = MainActivity.appPreferences.edit();
                        editor.clear();
                        editor.putString("person_Username", p.getPerson_Username());
                        editor.apply();
                        Log.d("dbug","setting pref signin =".concat(getSharedPref()));

                        Global.currentSessionOwner=p;

                        Intent i=new Intent(SignInSignUp.this, HomePage.class);
                        finish();
                        startActivity(i);
                    }
                    else
                    {
                        trySignIn=false;
                        siWarning.setText("Username or password is incorrect");
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void signUpPressed()
    {
        String email,username,password,cnic;
        email=suEmail.getText().toString();
        username=suUsername.getText().toString();
        password=suPassword.getText().toString();
        cnic=suCnic.getText().toString();

        if(email.isEmpty())
        {
            suWarning.setText("Please enter email");
        }
        else if(username.isEmpty())
        {
            suWarning.setText("Please enter username");
        }
        else if(password.isEmpty())
        {
            suWarning.setText("Please enter password");
        }
        else if(cnic.isEmpty())
        {
            suWarning.setText("Please enter cnic");
        }
        else
        {
            Global._personToReturnFromDBByUsername=null;
            progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Accessing database...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Global.getPersonFromDB(username);
            trySignUp=true;
        }
    }

    private void signInPressed()
    {
        String username,password;
        username=siUsername.getText().toString();
        password=siPassword.getText().toString();

        if(username.isEmpty())
        {
            siWarning.setText("Username field empty");
        }
        else if(password.isEmpty())
        {
            siWarning.setText("Password field empty");
        }
        else
        {
            progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Accessing database...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Global.getPersonFromDBByUsernamePassword(username,password);
            trySignIn=true;
        }
    }

    private void findViews()
    {
        siUsername=findViewById(R.id.EditText_SignInUsername);
        siPassword=findViewById(R.id.EditText_SignInPassword);
        siButton=findViewById(R.id.Button_SignInButton);
        siWarning=findViewById(R.id.TextView_SignInWarning);
        siTitle=findViewById(R.id.TextView_Title);

        suEmail=findViewById(R.id.EditText_SignUpEmail);
        suUsername=findViewById(R.id.EditText_SignUpUsername);
        suPassword=findViewById(R.id.EditText_SignUpPassword);
        suCnic=findViewById(R.id.EditText_SignUpCnic);
        suButton=findViewById(R.id.Button_SignUpButton);
        suWarning=findViewById(R.id.TextView_SignUpWarning);
        suTitle=findViewById(R.id.TextView_Title2);

        back=findViewById(R.id.ImageView_Back);
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