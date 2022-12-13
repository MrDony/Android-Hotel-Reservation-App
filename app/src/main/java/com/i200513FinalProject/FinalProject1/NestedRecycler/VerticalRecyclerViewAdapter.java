package com.i200513FinalProject.FinalProject1.NestedRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.i200513FinalProject.FinalProject1.R;

import java.util.List;

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalViewHolder> {

    private Context context;
    private List<VerticalRecyclerViewModel> itemsList;

    public VerticalRecyclerViewAdapter(Context context, List<VerticalRecyclerViewModel> list)
    {
        this.context=context;
        this.itemsList=list;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VerticalViewHolder(LayoutInflater.from(context).inflate(R.layout.vertical_recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        holder.title.setText(itemsList.get(position).getTitle());
        holder.description.setText(itemsList.get(position).getDescription());
        setHorizontalItemRecycler(holder.horizontalList,itemsList.get(position).getHorizontalList());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public static final class VerticalViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        RecyclerView horizontalList;
        public VerticalViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title=itemView.findViewById(R.id.TextView_Title);
            description=itemView.findViewById(R.id.TextView_Description);
            horizontalList=itemView.findViewById(R.id.RecyclerView_HorizontalRecycler);

        }


    }

    private void setHorizontalItemRecycler(RecyclerView recyclerView, List<HorizontalRecyclerViewModel> recyclerItemsList)
    {
        HorizontalRecyclerViewAdapter itemRecyclerAdapter = new HorizontalRecyclerViewAdapter(context,recyclerItemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));

        recyclerView.setAdapter(itemRecyclerAdapter);

    }
}
