package com.i200513FinalProject.FinalProject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.i200513FinalProject.FinalProject1.Admin.adminView;
import com.i200513FinalProject.FinalProject1.HomeView.HomePage;
import com.i200513FinalProject.FinalProject1.Person.Person;
import com.i200513FinalProject.FinalProject1.SignInSignUp.SignInSignUp;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dbug";
    public static SharedPreferences appPreferences;
    Button press;

    public static ProgressDialog mainActivityProgressDialog=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("dbug","xxxxxx");


        MainActivity.appPreferences = getPreferences(Context.MODE_PRIVATE);
        Global.db = FirebaseFirestore.getInstance();

        //openAdminPage();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful())
                        {
                            Global.LOCAL_APP_TOKEN=task.getResult();
                            Log.d("dbug","Token: ".concat(Global.LOCAL_APP_TOKEN));
                        }
                    }
                });

        press=findViewById(R.id.Button_openApp);
        MainActivity.mainActivityProgressDialog=new ProgressDialog(this);
        MainActivity.mainActivityProgressDialog.setTitle("Loading App...");
        MainActivity.mainActivityProgressDialog.setCancelable(false);
        MainActivity.mainActivityProgressDialog.show();


        //setSharedPref(Global.LOCAL_PREFERENCE_DEFAULT);
        String _localUsername=checkSharedPref();
        Log.d("dbug","Local: ".concat(_localUsername));
        //


        Person p = new Person("1", "ammarbaloch2015@gmail.com", "MrDony",
                "imageURl", "password", "3740518339337");
        if(!_localUsername.equals(Global.LOCAL_PREFERENCE_DEFAULT))
            Global.currentSessionOwner = Global.getPersonFromDB(_localUsername);

        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String localUsername=checkSharedPref();
                Log.d("dbug","Local user after press = "+localUsername);
                if(localUsername.equals("admin"))
                {
                    Log.d("dbug","Main: local user is admin after if");
                    Global.setCurrentSessionOwner(localUsername);
                    openAdminPage();
                }
                else if(!localUsername.equals(Global.LOCAL_PREFERENCE_DEFAULT)) {
                    Global.setCurrentSessionOwner(localUsername);
                    openHomePage();
                }

                else
                {
                    openSignInSignUp();
                }
            }
        });
        //openHomePage();

        //testDB();

    }

    private void openAdminPage() {
        Intent i=new Intent(MainActivity.this, adminView.class);
        finish();
        startActivity(i);
    }

    private void openSignInSignUp() {
        Intent i=new Intent(MainActivity.this, SignInSignUp.class);
        finish();
        startActivity(i);
    }


    public void setSharedPref(String username) {


        SharedPreferences.Editor editor = MainActivity.appPreferences.edit();
        editor.putString("person_Username", username);
        editor.apply();


    }

    private String checkSharedPref() {

        String defaultValue = Global.LOCAL_PREFERENCE_DEFAULT;
        String person_username = MainActivity.appPreferences.getString("person_Username", defaultValue);
        Log.d("dbug", "Local user = ".concat(person_username));
        if (person_username.equals(Global.LOCAL_PREFERENCE_DEFAULT))
        {
            MainActivity.mainActivityProgressDialog.dismiss();
           return person_username;
        }
        //Global.currentSessionOwner=Global.getPersonFromDB(person_username);
        return person_username;
    }




    private void openHomePage() {

        //for (int i = 0; i < 100000; i++) for (int j = 0; j < 1000; j++) ;
        Intent i = new Intent(this, com.i200513FinalProject.FinalProject1.HomeView.HomePage.class);
        finish();
        startActivity(i);
    }

    private void testDB() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        Global.db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        press = findViewById(R.id.Button_openApp);
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }
}