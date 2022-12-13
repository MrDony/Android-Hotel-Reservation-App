package com.i200513FinalProject.FinalProject1.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.Hotel.Hotel;
import com.i200513FinalProject.FinalProject1.NestedRecycler.VerticalRecyclerViewModel;
import com.i200513FinalProject.FinalProject1.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //List<SearchRecyclerModel> searchedList;
    TextView title;
    EditText search;
    ImageView searchButton;
    ImageView backButton;
    RecyclerView recyclerView;
    SearchRecyclerAdapter recyclerAdapter;

    public static ProgressDialog searchActivityProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViews();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!search.getText().toString().isEmpty())
                {
                    SearchActivity.searchActivityProgressDialog=new ProgressDialog(SearchActivity.this);
                    SearchActivity.searchActivityProgressDialog.setTitle("Searching...");
                    SearchActivity.searchActivityProgressDialog.setCancelable(false);
                    SearchActivity.searchActivityProgressDialog.show();

                    Global.getHotelsFromDBByCity(search.getText().toString());
                    commitSearch();
                }

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global._hotelsListToReturnByCity=null;
                finish();
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitSearch();
            }
        });



    }

    private void commitSearch()
    {
        List<Hotel> hotels=Global._hotelsListToReturnByCity;
        if(hotels!=null && hotels.size()>0)
        {
            List<SearchRecyclerModel> recyclerList=new ArrayList<>();
            for(int i=0;i<hotels.size();i++)
            {
                //String hotelID,String imgUrl, String title, String description
                recyclerList.add(new SearchRecyclerModel(
                        hotels.get(i).getHotel_ID(),
                        hotels.get(i).getHotel_Image(),
                        hotels.get(i).getHotel_Name(),
                        "City: ".concat(hotels.get(i).getHotel_City()).concat("\n").concat(
                                hotels.get(i).getHotel_Description()
                        ).concat("\n").concat(
                                "Pricing: "
                        ).concat("\n").concat(hotels.get(i).getHotel_Pricing())
                ));
            }
            setRecycler(recyclerList);

        }
        else
        {
            Toast.makeText(this,"No hotels in this city",Toast.LENGTH_SHORT);
        }
    }

    private void findViews() {
        search=findViewById(R.id.EditText_SearchQuery);
        searchButton=findViewById(R.id.ImageView_Search);
        backButton=findViewById(R.id.ImageView_Back);
        recyclerView=findViewById(R.id.RecyclerView_SearchRecycler);
        title=findViewById(R.id.TextView_Title);
    }

    private void setRecycler(List<SearchRecyclerModel> searchedItems)
    {
        //verticalRecycler = findViewById(R.id.RecyclerView_VerticalRecycler);
        //        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //        verticalRecycler.setLayoutManager(layoutManager);
        //        verticalRecyclerAdapter = new VerticalRecyclerViewAdapter(this, verticalList);
        //        verticalRecycler.setAdapter(verticalRecyclerAdapter);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter=new SearchRecyclerAdapter(this,searchedItems);
        recyclerView.setAdapter(recyclerAdapter);
    }
}