package com.i200513FinalProject.FinalProject1.Admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.i200513FinalProject.FinalProject1.Global;
import com.i200513FinalProject.FinalProject1.R;

import java.util.List;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.AdminRecyclerViewHolder> {

    Context context;
    List<AdminRecyclerModel> itemsList;

    public AdminRecyclerAdapter(Context context, List<AdminRecyclerModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public AdminRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //new SearchRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.search_recycler_view_row,parent,false));
        return new AdminRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.admin_recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRecyclerViewHolder holder, int position) {
        holder.hotelName.setText(itemsList.get(position).getHotel_Name());
        holder.username.setText(itemsList.get(position).getPerson_Username());
        holder.from.setText(itemsList.get(position).getFromDateTime());
        holder.to.setText(itemsList.get(position).getToDateTime());
        holder.status.setText(itemsList.get(position).getStatus());
        if(holder.status.getText().equals("Pending"))
        {
            holder.pending.setText("Pending*");
        }
        if(holder.status.getText().equals("Booked"))
        {
            holder.booked.setText("Booked*");
        }
        if(holder.status.getText().equals("Cancelled"))
        {
            holder.cancelled.setText("Cancelled*");
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static final class AdminRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView hotelName,
        username,
        from,
        to,
        status;
        Button pending,booked,cancelled;
        AdminRecyclerModel booking;


        public AdminRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName=itemView.findViewById(R.id.TextView_HotelName);
            username=itemView.findViewById(R.id.TextView_PersonUsername);
            from=itemView.findViewById(R.id.TextView_FromDate);
            to=itemView.findViewById(R.id.TextView_ToDate);
            status=itemView.findViewById(R.id.TextView_Status);
            pending=itemView.findViewById(R.id.Button_Pending);
            booked=itemView.findViewById(R.id.Button_Booked);
            cancelled=itemView.findViewById(R.id.Button_Cancelled);
            pending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booking=new AdminRecyclerModel(username.getText().toString(),hotelName.getText().toString(),from.getText().toString(),to.getText().toString(),status.getText().toString());
                    Log.d("dbug","AdminRecyclerAdapter: ViewHolder pending clicked for = ".concat(hotelName.getText().toString()));
                    Global.setBookingStatusInDB(booking,"Pending",adminView.adminViewContext);
                    pending.setText("Pending*");
                    booked.setText("Booked");
                    cancelled.setText("Cancelled");
                    adminView.refreshAdminPage(adminView.adminViewContext);
                }
            });
            booked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booking=new AdminRecyclerModel(username.getText().toString(),hotelName.getText().toString(),from.getText().toString(),to.getText().toString(),status.getText().toString());

                    Log.d("dbug","AdminRecyclerAdapter: ViewHolder booked clicked for = ".concat(hotelName.getText().toString()));
                    Global.setBookingStatusInDB(booking,"Booked",adminView.adminViewContext);
                    pending.setText("Pending");
                    booked.setText("Booked*");
                    cancelled.setText("Cancelled");
                    adminView.refreshAdminPage(adminView.adminViewContext);
                }
            });
            cancelled.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booking=new AdminRecyclerModel(username.getText().toString(),hotelName.getText().toString(),from.getText().toString(),to.getText().toString(),status.getText().toString());

                    Log.d("dbug","AdminRecyclerAdapter: ViewHolder cancelled clicked for = ".concat(hotelName.getText().toString()));
                    Global.setBookingStatusInDB(booking,"Cancelled",adminView.adminViewContext);
                    pending.setText("Pending");
                    booked.setText("Booked");
                    cancelled.setText("Cancelled*");
                    adminView.refreshAdminPage(adminView.adminViewContext);
                }
            });
        }
    }
}
