package com.hazyaz.weshare.users.admin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.hazyaz.weshare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAIAdapter extends RecyclerView.Adapter<MyAIAdapter.ViewHolder>{

    private final ArrayList<ArrayList<String >> listdata;
    String className;
    Context context;

    // RecyclerView recyclerView;
    public MyAIAdapter(ArrayList<ArrayList<String>> listdata, String clssNam, Context conte) {
        this.listdata = listdata;
        this.className = clssNam;
        this.context = conte;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.admin_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAIAdapter.ViewHolder holder, int position) {
        final ArrayList<String> myListData = listdata.get(position);

        Log.d("23djfjsdf","Inside My lsit");
        holder.name.setText(myListData.get(0));
        holder.area.setText(myListData.get(3));
        holder.phone.setText(myListData.get(2));
        holder.email.setText(myListData.get(1));


        Log.d("thisisurl",""+myListData.get(0)+myListData.get(1));

    }



    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,area,phone,email;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.AIName);
            this.area = (TextView) itemView.findViewById(R.id.AIArea);
            this.phone = (TextView) itemView.findViewById(R.id.AIPHone);
            this.email = (TextView) itemView.findViewById(R.id.AIEmail);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.admin_relative);
        }
    }
}