package com.i200513FinalProject.FinalProject1;

import com.i200513FinalProject.FinalProject1.Admin.AdminRecyclerModel;
import com.i200513FinalProject.FinalProject1.Admin.adminView;
import com.i200513FinalProject.FinalProject1.Booking.Booking;
import com.i200513FinalProject.FinalProject1.Booking.MakeBooking;
import com.i200513FinalProject.FinalProject1.Person.Person;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.i200513FinalProject.FinalProject1.HomeView.HomePage;
import com.i200513FinalProject.FinalProject1.Hotel.Hotel;
import com.i200513FinalProject.FinalProject1.Person.PersonProfile;
import com.i200513FinalProject.FinalProject1.Search.SearchActivity;
import com.i200513FinalProject.FinalProject1.SignInSignUp.SignInSignUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Global {
    public static final String FIREBASE_HOTELS_COLLECTION = "Hotels";
    public static final String FIREBASE_USERS_COLLECTION = "Users";
    public static final String FIREBASE_BOOKINGS_COLLECTION = "Bookings";
    public static final String LOCAL_PREFERENCE_DEFAULT="_na";
    public static String LOCAL_APP_TOKEN=null;
    public static FirebaseFirestore db;
    public static Object dataSend1;
    public static Object dataSend2;
    public static Object dataSend3;
    public static Person currentSessionOwner;
    public static List<Hotel> _hotelsListToReturn;
    public static List<Hotel> _hotelsListToReturnByCity;
    public static List<Booking> _bookingsListToReturnByUsername=null;
    public static Person _personToReturnFromDBByUsername=null;
    public static Person _personToReturnFromDBByUsernamePassword=null;
    public static boolean lock = false;


    public static void addHotelsToDB(List<Hotel> hotels) {
        for (int i = 0; i < hotels.size(); i++) {
            Hotel iHotel = hotels.get(i);
            Map<String, Object> hotelToPut = new HashMap<>();
            //String hotel_ID, String hotel_Name, String hotel_Address,
            // String hotel_City, String hotel_PhoneNo, String hotel_Description,
            // String hotel_Rating, String hotel_Pricing, String hotel_Tags,
            // String hotel_Image
            hotelToPut.put("hotel_ID", iHotel.getHotel_ID());
            hotelToPut.put("hotel_Name", iHotel.getHotel_Name());
            hotelToPut.put("hotel_Address", iHotel.getHotel_Address());
            hotelToPut.put("hotel_City", iHotel.getHotel_City());
            hotelToPut.put("hotel_PhoneNo", iHotel.getHotel_PhoneNo());
            hotelToPut.put("hotel_Description", iHotel.getHotel_Description());
            hotelToPut.put("hotel_Rating", iHotel.getHotel_Rating());
            hotelToPut.put("hotel_Pricing", iHotel.getHotel_Pricing());
            hotelToPut.put("hotel_Tags", iHotel.getHotel_Tags());
            hotelToPut.put("hotel_Image", iHotel.getHotel_Image());

            Global.db.collection(Global.FIREBASE_HOTELS_COLLECTION).add(hotelToPut).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    //Log.d("dbug ,iHotel.getHotel_ID() + "(id) hotel added to firebase");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Log.d("dbug ,iHotel.getHotel_ID() + "(id) hotel could not be added to firebase");
                }
            });
        }

    }

    public static void addBookingToDB(Booking booking)
    {
        Map<String,Object> bookingToPut= new HashMap<>();
        //String booking_PersonID, booking_PersonUsername;
        //    String booking_HotelID, booking_HotelName;
        //    String booking_StartDateTime, booking_EndDateTime;
        //    String booking_Status;
        bookingToPut.put("booking_PersonID",booking.getBooking_PersonID());
        bookingToPut.put("booking_PersonUsername",booking.getBooking_PersonUsername());
        bookingToPut.put("booking_HotelID",booking.getBooking_HotelID());
        bookingToPut.put("booking_HotelName",booking.getBooking_HotelName());
        bookingToPut.put("booking_StartDateTime",booking.getBooking_StartDateTime());
        bookingToPut.put("booking_EndDateTime",booking.getBooking_EndDateTime());
        bookingToPut.put("booking_Status",booking.getBooking_Status());

        Global.db.collection(Global.FIREBASE_BOOKINGS_COLLECTION).add(bookingToPut).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //Log.d("dbug",Added");
                //Toast.makeText(MakeBooking,"Booking added",Toast.LENGTH_SHORT).show();
                //MakeBooking.makeBooking.setText("Booking made");
            }
        });

    }

    public static List<Booking> getBookingsOfUser(String username)
    {
        List<Booking> bookings=new ArrayList<>();
        Global.db.collection(FIREBASE_BOOKINGS_COLLECTION).whereEqualTo("booking_PersonUsername",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        bookings.add(new Booking(
                                document.getString("booking_PersonID"),
                                document.getString("booking_PersonUsername"),
                                document.getString("booking_HotelID"),
                                document.getString("booking_HotelName"),
                                document.getString("booking_StartDateTime"),
                                document.getString("booking_EndDateTime"),
                                document.getString("booking_Status")
                        ));
                    }
                    if(PersonProfile.personProfileProgressDialog.isShowing())
                        PersonProfile.personProfileProgressDialog.dismiss();
                }
            }
        });
        Global._bookingsListToReturnByUsername=bookings;
        return bookings;
    }

    public static List<Hotel> getHotelsFromDB() {
        _hotelsListToReturn = new ArrayList<>();
        _getHotelsFromDB();
        return _hotelsListToReturn;
    }

    public static void _getHotelsFromDB() {
        //Log.d("dbug",GLobal _getHotelsFromDB");
        Global.db.collection(FIREBASE_HOTELS_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //String hotel_ID, String hotel_Name, String hotel_Address,
                        // String hotel_City, String hotel_PhoneNo, String hotel_Description,
                        // String hotel_Rating, String hotel_Pricing, String hotel_Tags,
                        // String hotel_Image
                        //Log.d("dbug ,document.getString("hotel_ID") + " hotel is present");
                        Global._hotelsListToReturn.add(new Hotel(
                                document.getString("hotel_ID"),
                                document.getString("hotel_Name"),
                                document.getString("hotel_Address"),
                                document.getString("hotel_City"),
                                document.getString("hotel_PhoneNo"),
                                document.getString("hotel_Description"),
                                document.getString("hotel_Rating"),
                                document.getString("hotel_Pricing"),
                                document.getString("hotel_Tags"),
                                document.getString("hotel_Image")));
                        ////Log.d("dbug",Global 1: "+Global._hotelsListToReturn.get(Global._hotelsListToReturn.size()-1).printString());
                    }
                    for (int i = 0; i < Global._hotelsListToReturn.size(); i++) {
                        //Log.d("dbug ,"Global 2: (" + Global._hotelsListToReturn.size() + ")" + Global._hotelsListToReturn.get(i).printString());

                    }


                } else {
                    Log.w("dbug", "Error getting documents.", task.getException());

                }
            }

        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (int i = 0; i < Global._hotelsListToReturn.size(); i++) {
                    //Log.d("dbug ,"Global 3: (" + Global._hotelsListToReturn.size() + ")" + Global._hotelsListToReturn.get(i).printString());
                    Global.lock = false;
                }
                HomePage.progressDialog.dismiss();
            }
        });

    }

    public static float stringToFloat(String s) {
        return Float.parseFloat(s);
    }

    public static Hotel getHotelFromID(String id)
    {
        for(int i=0;i<Global._hotelsListToReturn.size();i++)
        {
            if(Global._hotelsListToReturn.get(i).getHotel_ID().equals(id))
            {
                return Global._hotelsListToReturn.get(i);
            }
        }
        return null;
    }

    public static List<Hotel> getHotelsFromDBByCity(String city)
    {
        List<Hotel> hotels=new ArrayList<>();
        Global.db.collection(Global.FIREBASE_HOTELS_COLLECTION).whereEqualTo("hotel_City",city).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        //Log.d("dbug",got".concat(document.getString("hotel_City")));
                        hotels.add(new Hotel(
                            document.getString("hotel_ID"),
                            document.getString("hotel_Name"),
                            document.getString("hotel_Address"),
                            document.getString("hotel_City"),
                            document.getString("hotel_PhoneNo"),
                            document.getString("hotel_Description"),
                            document.getString("hotel_Rating"),
                            document.getString("hotel_Pricing"),
                            document.getString("hotel_Tags"),
                            document.getString("hotel_Image")));
                    }
                    if(SearchActivity.searchActivityProgressDialog.isShowing())
                        SearchActivity.searchActivityProgressDialog.dismiss();
                }
            }
        });
        Global._hotelsListToReturnByCity=hotels;
        return hotels;
    }

    public static Person getPersonFromDB(String username)
    {
        Global._personToReturnFromDBByUsername=null;
        Global.db.collection(FIREBASE_USERS_COLLECTION).whereEqualTo("person_Username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete())
                {
                    //String person_ID,person_Email,person_Username;
                    //    String person_ImgUrl;
                    //    String person_Password;
                    //    String person_Cnic;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d("dbug",getPersonFromDB 1");
                        Global._personToReturnFromDBByUsername=new Person(document.getString("person_ID"),
                                document.getString("person_Email"),
                                document.getString("person_Username"),
                                document.getString("person_ImgUrl"),
                                document.getString("person_Password"),
                                document.getString("person_Cnic"));
                        //Log.d("dbug",personFound");
                    }
                    if(MainActivity.mainActivityProgressDialog!=null && MainActivity.mainActivityProgressDialog.isShowing())
                        MainActivity.mainActivityProgressDialog.dismiss();
                    if(SignInSignUp.progressDialog!=null && SignInSignUp.progressDialog.isShowing())
                        SignInSignUp.progressDialog.dismiss();

                }
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d("dbug",getPersonFromDB 2");
                        Global._personToReturnFromDBByUsername=new Person(document.getString("person_ID"),
                                document.getString("person_Email"),
                                document.getString("person_Username"),
                                document.getString("person_ImgUrl"),
                                document.getString("person_Password"),
                                document.getString("person_Cnic"));
                        //Log.d("dbug",personFound");
                    }
                    if(MainActivity.mainActivityProgressDialog!=null && MainActivity.mainActivityProgressDialog.isShowing())
                        MainActivity.mainActivityProgressDialog.dismiss();
                    if(SignInSignUp.progressDialog!=null && SignInSignUp.progressDialog.isShowing())
                        SignInSignUp.progressDialog.dismiss();
                }
            }
        });
        return _personToReturnFromDBByUsername;
    }

    public static void getPersonFromDBByUsernamePassword(String username,String password)
    {
        Global._personToReturnFromDBByUsernamePassword=null;
        Global.db.collection(FIREBASE_USERS_COLLECTION).whereEqualTo("person_Username",username)
                .whereEqualTo("person_Password",password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d("dbug",getPersonFromDB 1");
                    Global._personToReturnFromDBByUsernamePassword=new Person(document.getString("person_ID"),
                            document.getString("person_Email"),
                            document.getString("person_Username"),
                            document.getString("person_ImgUrl"),
                            document.getString("person_Password"),
                            document.getString("person_Cnic"));
                    //Log.d("dbug",personFound");
                }
                if(MainActivity.mainActivityProgressDialog!=null && MainActivity.mainActivityProgressDialog.isShowing())
                    MainActivity.mainActivityProgressDialog.dismiss();
                if(SignInSignUp.progressDialog!=null && SignInSignUp.progressDialog.isShowing())
                    SignInSignUp.progressDialog.dismiss();
            }
        });
    }
    public static void addPersonToDB(Person person)
    {
        //Log.d("dbug",adding person to db = ".concat(person.printString()));
        if(getPersonFromDB(person.getPerson_Username())!=null)return;
        Map<String, Object> personToPut = new HashMap<>();
        //String person_ID, String person_Email, String person_Username,
        // String person_ImgUrl, String person_Password, String person_Cnic
        personToPut.put("person_ID",person.getPerson_ID());
        personToPut.put("person_Email",person.getPerson_Email());
        personToPut.put("person_Username",person.getPerson_Username());
        personToPut.put("person_ImgUrl",person.getPerson_ImgUrl());
        personToPut.put("person_Password",person.getPerson_Password());
        personToPut.put("person_Cnic",person.getPerson_Cnic());
        Global.db.collection(FIREBASE_USERS_COLLECTION).
                add(personToPut).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //Log.d("dbug ,person.getPerson_Username() + "(username) person added to firebase");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.d("dbug ,person.getPerson_Username() + "(username) person could not be added to firebase");
            }
        });
    }

    public static List<String> splitDate(String date)
    {
       List<String> splitedDate=new ArrayList<>();
       String portion;
       //YYYY/MM/DD
        //0123/56/89
       String[] array=date.split("-");
       splitedDate.add(array[0]);
       splitedDate.add(array[1]);
       splitedDate.add(array[2]);

       return splitedDate;
    }

    public static int sti(String s)
    {
        return Integer.parseInt(s);
    }


    public static void setCurrentSessionOwner(String username)
    {
        Global.db.collection(FIREBASE_USERS_COLLECTION).whereEqualTo("person_Username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete())
                {
                    //String person_ID,person_Email,person_Username;
                    //    String person_ImgUrl;
                    //    String person_Password;
                    //    String person_Cnic;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d("dbug",getPersonFromDB 1");
                        Global.currentSessionOwner=new Person(document.getString("person_ID"),
                                document.getString("person_Email"),
                                document.getString("person_Username"),
                                document.getString("person_ImgUrl"),
                                document.getString("person_Password"),
                                document.getString("person_Cnic"));
                        //Log.d("dbug",personFound");
                    }
                }
            }
        });

    }

    public static void logoutUserInDB(String username)
    {
        Global.db.collection(FIREBASE_USERS_COLLECTION).whereEqualTo("person_Username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        String id=document.getId();
                        //Log.d("dbug",Logout user from db id of document = ".concat(id));
                        Global.db.collection(FIREBASE_USERS_COLLECTION).document(id).update("person_ID","1").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Log.d("dbug",Logged out in database");
                            }
                        });
                    }
                }
            }
        });
    }

    public static void signinUserInDB(String username)
    {
        Global.db.collection(FIREBASE_USERS_COLLECTION).whereEqualTo("person_Username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        String id=document.getId();
                        //Log.d("dbug",sign in user from db id of document = ".concat(id));
                        Global.db.collection(FIREBASE_USERS_COLLECTION).document(id).update("person_ID",Global.LOCAL_APP_TOKEN).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Log.d("dbug",signed in in database");
                            }
                        });
                    }
                }
            }
        });
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////ADMIN/////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void setAdminPendingBookings(Context p_context)
    {
        Global.db.collection(FIREBASE_BOOKINGS_COLLECTION).whereEqualTo("booking_Status","Pending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete())
                {
                    List<AdminRecyclerModel> pendingList=new ArrayList<>();
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        pendingList.add(new AdminRecyclerModel(
                                document.getString("booking_PersonUsername"),
                                document.getString("booking_HotelName"),
                                document.getString("booking_StartDateTime"),
                                document.getString("booking_EndDateTime"),
                                document.getString("booking_Status")
                        ));
                    }
                    Log.d("dbug","Global: setAdminPendingBookings adminView.setPending()");
                    com.i200513FinalProject.FinalProject1.Admin.adminView.setPending(pendingList,p_context);
                    if(adminView.settingPending!=null && adminView.settingPending.isShowing())
                        adminView.settingPending.dismiss();
                }

            }
        });
    }

    public static void setAdminBookedBookings(Context p_context)
    {
        Global.db.collection(FIREBASE_BOOKINGS_COLLECTION).whereEqualTo("booking_Status","Booked").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete())
                {
                    List<AdminRecyclerModel> bookedList=new ArrayList<>();
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        bookedList.add(new AdminRecyclerModel(
                                document.getString("booking_PersonUsername"),
                                document.getString("booking_HotelName"),
                                document.getString("booking_StartDateTime"),
                                document.getString("booking_EndDateTime"),
                                document.getString("booking_Status")
                        ));
                    }
                    Log.d("dbug","Global: setAdminBookedBookings adminView.setBooked()");
                    com.i200513FinalProject.FinalProject1.Admin.adminView.setBooked(bookedList,p_context);
                    if(adminView.settingBooked!=null && adminView.settingBooked.isShowing())
                        adminView.settingBooked.dismiss();
                }

            }
        });
    }

    public static void setBookingStatusInDB(AdminRecyclerModel booking,String p_status, Context context)
    {
        Log.d("dbug","Global: setBookingStatusInDB of booking = ".concat(booking.printString()).concat(" to").concat(p_status));
        Log.d("dbug","Global: setBookingStatusInDB context.packagename = ".concat(context.getPackageName()));
        //bookingToPut.put("booking_PersonID",booking.getBooking_PersonID());
        //        bookingToPut.put("booking_PersonUsername",booking.getBooking_PersonUsername());
        //        bookingToPut.put("booking_HotelID",booking.getBooking_HotelID());
        //        bookingToPut.put("booking_HotelName",booking.getBooking_HotelName());
        //        bookingToPut.put("booking_StartDateTime",booking.getBooking_StartDateTime());
        //        bookingToPut.put("booking_EndDateTime",booking.getBooking_EndDateTime());
        //        bookingToPut.put("booking_Status",booking.getBooking_Status());
        Global.db.collection(FIREBASE_BOOKINGS_COLLECTION)
                .whereEqualTo("booking_PersonUsername",booking.getPerson_Username())
                .whereEqualTo("booking_HotelName",booking.getHotel_Name())
                .whereEqualTo("booking_StartDateTime",booking.getFromDateTime())
                .whereEqualTo("booking_EndDateTime",booking.getToDateTime())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete())
                        {
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                String docID=document.getId();
                                Log.d("dbug","Global: setBookingStatusInDB documentId = ".concat(docID));
                                Global.db.collection(FIREBASE_BOOKINGS_COLLECTION).document(docID).update("booking_Status",p_status)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context,"Changed to ".concat(p_status),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dbug","Global: setBookingStatusInDB failed");
                    }
                });
    }


}
