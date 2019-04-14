package com.dtec.helloatu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Category> categoryList;
    CustomItemClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RelativeLayout rlCtgBgColor;
        public ImageView ivCtgPic;
        public TextView tvCtgTitleBangla, tvCtgTitleEnglish;

        public MyViewHolder(View view) {
            super(view);

            rlCtgBgColor =(RelativeLayout) view.findViewById(R.id.rlCtgBgColor);
            ivCtgPic = (ImageView) view.findViewById(R.id.ivCtgPic);
            tvCtgTitleBangla = (TextView) view.findViewById(R.id.tvCtgTitleBangla);
            tvCtgTitleEnglish = (TextView) view.findViewById(R.id.tvCtgTitleEnglish);
            rlCtgBgColor.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
