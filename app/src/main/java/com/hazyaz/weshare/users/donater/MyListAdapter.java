package com.hazyaz.weshare.users.donater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.areaincharge.AIDonationData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private final ArrayList<ArrayList<String >> listdata;
    ArrayList<String > personData;
    String classNames;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<ArrayList<String>> listdata, String clssNam, Context conte, ArrayList<String > person) {
        this.listdata = listdata;
        this.classNames = clssNam;
        this.context = conte;
        this.personData = person;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.donater_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(classNames.equals("AssignDP")){

            holder.name.setText(personData.get(0));
            holder.desc.setText(personData.get(1));
            Picasso.get()
                    .load(personData.get(3))
                    .into(holder.imageViewxx);
            holder.location.setVisibility(View.GONE);

        }
        else{
            final ArrayList<String> myListData = listdata.get(position);

            Log.d("23djfjsdf","Inside My lsit");
            holder.name.setText(myListData.get(0));
            holder.desc.setText(myListData.get(1));
            Picasso.get()
                    .load(myListData.get(3))
                    .into(holder.imageViewxx);
            holder.location.setText("Donation with: "+myListData.get(4));



            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent i = new Intent(context, AIDonationData.class);
                    i.putExtra("itemname",myListData.get(0));
                    i.putExtra("itemdesc",myListData.get(1));
                    i.putExtra("time",myListData.get(2));
                    i.putExtra("Image",myListData.get(3));
                    i.putExtra("donation_with",myListData.get(4));
                    i.putExtra("current_location",myListData.get(5));

                    i.putExtra("name",personData.get(0));
                    i.putExtra("area",personData.get(1));
                    i.putExtra("city",personData.get(2));
                    i.putExtra("state",personData.get(3));
                    i.putExtra("phone",personData.get(4));
                    i.putExtra("email",personData.get(5));
                    i.putExtra("activity",classNames);


                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);



                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewxx;
        public TextView name,desc,location;
        public CardView relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewxx = (ImageView) itemView.findViewById(R.id.imageViewwe);

            this.name = (TextView) itemView.findViewById(R.id.itemName);
            this.desc = (TextView) itemView.findViewById(R.id.itemDesc);
            this.location = (TextView) itemView.findViewById(R.id.lastLocation);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutdf);
        }
    }
}