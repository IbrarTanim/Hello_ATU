package com.dtec.helloatu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.dtec.helloatu.listener.CustomItemClickListener;
import com.dtec.helloatu.pojo.Category;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> categoryList;
    CustomItemClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RelativeLayout rlCtgBgColor;
        public ImageView ivCtgPic;
        public TextView tvCtgTitleBangla, tvCtgTitleEnglish;
        private final Context context;

        public MyViewHolder(View view) {
            super(view);

            context = itemView.getContext();
            rlCtgBgColor = (RelativeLayout) view.findViewById(R.id.rlCtgBgColor);
            ivCtgPic = (ImageView) view.findViewById(R.id.ivCtgPic);
            tvCtgTitleBangla = (TextView) view.findViewById(R.id.tvCtgTitleBangla);
            tvCtgTitleEnglish = (TextView) view.findViewById(R.id.tvCtgTitleEnglish);
            rlCtgBgColor.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

           /* if (clickListener != null) {
                clickListener.onItemClick(itemView, this.getAdapterPosition());
            }*/


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
                   /* intent = new Intent(context, FormActivity.class);
                    intent.putExtra("positionForm", getAdapterPosition());*/

                    intent = new Intent(context, FragmentBaseActivity.class);
                    intent.putExtra("positionFragmentBaseActivity", getAdapterPosition());
                    break;
            }
            context.startActivity(intent);
            ((Activity) context).finish();

        }
    }


    public CategoryAdapter(Context mContext, List<Category> categoryList, CustomItemClickListener clickListener) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
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
        Category category = categoryList.get(position);

        holder.tvCtgTitleBangla.setText(category.getCtgTitleBangla());
        holder.tvCtgTitleEnglish.setText(category.getCtgTitleEnglish());
        holder.rlCtgBgColor.setBackgroundColor(category.getCtgBgColor());
        Glide.with(mContext).load(category.getCtgPic()).into(holder.ivCtgPic);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
