package com.dtec.helloatu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dtec.helloatu.R;
import com.dtec.helloatu.activities.FragmentBaseActivity;
import com.dtec.helloatu.activities.InformationActivity;
import com.dtec.helloatu.activities.WantedActivity;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.listener.CustomItemClickListener;
import com.dtec.helloatu.pojo.Category;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class InfoEditAdapter extends RecyclerView.Adapter<InfoEditAdapter.MyViewHolder> {

    private Context mContext;
    FragmentBaseActivity activity;
    private List<Crime> crimesList;
    //CustomItemClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout rl_info_edit;
        public ImageView ivCtgPic_info_edit;
        public TextView tvBangla_info_edit, tvEnglish_info_edit;
        private final Context context;


        public MyViewHolder(View view) {
            super(view);

            context = itemView.getContext();
            rl_info_edit = (RelativeLayout) view.findViewById(R.id.rl_info_edit);
            ivCtgPic_info_edit = (ImageView) view.findViewById(R.id.ivCtgPic_info_edit);
            tvBangla_info_edit = (TextView) view.findViewById(R.id.tvBangla_info_edit);
            tvEnglish_info_edit = (TextView) view.findViewById(R.id.tvEnglish_info_edit);
            //rl_info_edit.setOnClickListener(this);

        }

        /*@Override
        public void onClick(View v) {

           *//* if (clickListener != null) {
                clickListener.onItemClick(itemView, this.getAdapterPosition());
            }*//*

            final Intent intent;
            switch (getAdapterPosition()) {
                case 4:
                    intent = new Intent(context, WantedActivity.class);
                    intent.putExtra("positionWanted", getAdapterPosition());

                    break;

                case 5:
                    intent = new Intent(context, InformationActivity.class);
                    intent.putExtra("positionInfo", getAdapterPosition());

                    break;

                default:
                   *//* intent = new Intent(context, FormActivity.class);
                    intent.putExtra("positionForm", getAdapterPosition());*//*

                    intent = new Intent(context, FragmentBaseActivity.class);
                    intent.putExtra("positionFragmentBaseActivity", getAdapterPosition());
                    break;
            }
            context.startActivity(intent);
            ((Activity) context).finish();

        }*/

    }


    public InfoEditAdapter(Context mContext, FragmentBaseActivity activity, List<Crime> crimesList ) {
        this.mContext = mContext;
        this.activity = activity;
        this.crimesList = crimesList;
        //this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_info_edit, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView);

       /* itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(itemView, myViewHolder.getPosition());
            }
        });*/

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Crime crime = crimesList.get(position);
        //Glide.with(mContext).load(category.getCtgPic()).into(holder.ivCtgPic);
        if (crimesList.get(position).getCrimPosition() == 0 ) {
            holder.tvBangla_info_edit.setText(crimesList.get(position).getCrimeInfo());
            holder.tvEnglish_info_edit.setText(crimesList.get(position).getInformerName());
            holder.rl_info_edit.setBackgroundResource(R.color.app_color);
            holder.ivCtgPic_info_edit.setImageResource(R.drawable.ic_terrorist);

        } else  if ( crimesList.get(position).getCrimPosition() ==1 ) {
            holder.tvBangla_info_edit.setText(crimesList.get(position).getCrimeInfo());
            holder.tvEnglish_info_edit.setText(crimesList.get(position).getInformerName());
            holder.rl_info_edit.setBackgroundResource(R.color.cyber_crime_color);
            holder.ivCtgPic_info_edit.setImageResource(R.drawable.ic_serinze);

        } else  if ( crimesList.get(position).getCrimPosition() == 2 ) {
            holder.tvBangla_info_edit.setText(crimesList.get(position).getCrimeInfo());
            holder.tvEnglish_info_edit.setText(crimesList.get(position).getInformerName());
            holder.rl_info_edit.setBackgroundResource(R.color.bomb_crime_color);
            holder.ivCtgPic_info_edit.setImageResource(R.drawable.ic_bomb);

        } else  if ( crimesList.get(position).getCrimPosition() == 3 ) {
            holder.tvBangla_info_edit.setText(crimesList.get(position).getCrimeInfo());
            holder.tvEnglish_info_edit.setText(crimesList.get(position).getInformerName());
            holder.rl_info_edit.setBackgroundResource(R.color.organaizational_crime_color);
            holder.ivCtgPic_info_edit.setImageResource(R.drawable.ic_cyber_crime);

        }


    }

    @Override
    public int getItemCount() {
        return crimesList.size();
    }
}
