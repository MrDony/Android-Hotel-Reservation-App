package com.i200513FinalProject.FinalProject1.Person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.i200513FinalProject.FinalProject1.R;

import java.util.List;

public class PersonBookingRecyclerAdapter extends RecyclerView.Adapter<PersonBookingRecyclerAdapter.PersonBookingRecyclerViewHolder> {

    private Context context;
    private List<PersonBookingRecyclerModel> bookingsList;

    public PersonBookingRecyclerAdapter(Context context, List<PersonBookingRecyclerModel> bookingsList) {
        this.context = context;
        this.bookingsList = bookingsList;
    }

    @NonNull
    @Override
    public PersonBookingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonBookingRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.person_bookings_recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonBookingRecyclerViewHolder holder, int position) {

        holder.hotelName.setText(bookingsList.get(position).getHotelName());
        holder.from.setText(bookingsList.get(position).getFrom());
        holder.to.setText(bookingsList.get(position).getTo());
        holder.status.setText(bookingsList.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public class PersonBookingRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout item;
        TextView hotelName;
        TextView from;
        TextView to;
        TextView status;
        public PersonBookingRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.Booking);
            hotelName=itemView.findViewById(R.id.TextView_HotelName);
            from=itemView.findViewById(R.id.TextView_FromDate);
            to=itemView.findViewById(R.id.TextView_ToDate);
            status=itemView.findViewById(R.id.TextView_Status);
        }
    }
}
