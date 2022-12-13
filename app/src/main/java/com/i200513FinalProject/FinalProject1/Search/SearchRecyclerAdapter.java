package com.i200513FinalProject.FinalProject1.Search;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.Hotel.Hotel;
import com.i200513FinalProject.FinalProject1.NestedRecycler.HorizontalRecyclerViewModel;
import com.i200513FinalProject.FinalProject1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchRecyclerViewHolder> {

    private Context context;
    private List<SearchRecyclerModel> itemsList;

    public SearchRecyclerAdapter(Context context, List<SearchRecyclerModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.search_recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, int position) {
//
        String imgUrl=itemsList.get(position).getImgUrl();
        Picasso.get().load(imgUrl).into(holder.img);
        holder.id.setText(itemsList.get(position).getHotelID());
        holder.title.setText(itemsList.get(position).getTitle());
        holder.desc.setText(itemsList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public static final class SearchRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ConstraintLayout item;
        TextView id;
        TextView title;
        TextView desc;
        ImageView img;

        public SearchRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.ItemView_Item);
            id=itemView.findViewById(R.id.TextView_HiddenID);
            title=itemView.findViewById(R.id.TextView_Title);
            desc=itemView.findViewById(R.id.TextView_Description);
            desc.setMovementMethod(new ScrollingMovementMethod());
            img=itemView.findViewById(R.id.ImageView_Image);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("dbug",id.getText().toString()+" id hotel pressed");
                    Global.dataSend1=new Hotel(Global.getHotelFromID(id.getText().toString()));
                    Intent i=new Intent(itemView.getContext(),com.i200513FinalProject.FinalProject1.Hotel.HotelProfile.class);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
