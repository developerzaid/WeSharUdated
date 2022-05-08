package com.hazyaz.weshare.users.areaincharge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hazyaz.weshare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UpdateDP extends RecyclerView.Adapter<UpdateDP.ViewHolder> {

    ArrayList<String> personData;
    String classNames;
    Context context;

    public UpdateDP(ArrayList<String> dp, Context c) {
        this.personData = dp;
        this.context = c;
    }


    @NonNull
    @Override
    public UpdateDP.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.donater_listitem, parent, false);
        Log.d("INSIDEP", "INSIDE UODATE VIEWHOLDER");
        UpdateDP.ViewHolder vi = new UpdateDP.ViewHolder(listItem);
        return vi;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UpdateDP.ViewHolder holder, int position) {


        holder.name.setText(personData.get(0));
        holder.desc.setText(personData.get(1) + " Phone No " + personData.get(2));
        Picasso.get()
                .load(R.drawable.button_background)
                .into(holder.imageViewxx);

        holder.location.setVisibility(View.GONE);
        Log.d("INSIDEP", "BIND VIEW HOLDER");

        String person_key = personData.get(5);
        String donation_key = personData.get(6);
        String delivery_person = personData.get(7);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, AIHome.class);

//                    Donater UID
                i.putExtra("person_key", person_key);
//                    Donation UID
                i.putExtra("donation_key", donation_key);
                i.putExtra("delivery_person", delivery_person);

                Log.d("sdf45", "" + person_key + "   " + personData + "   " + delivery_person);
                AIHome a = new AIHome();
                a.updateDeliveryPerson(person_key, donation_key, delivery_person);


                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return personData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewxx;
        public TextView name, desc, location;
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
