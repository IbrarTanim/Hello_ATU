package com.dtec.helloatu.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtec.helloatu.R;
import com.dtec.helloatu.activities.FragmentBaseActivity;
import com.dtec.helloatu.activities.MainActivity;
import com.dtec.helloatu.adapter.InfoEditAdapter;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.listener.CustomItemClickListener;
import com.dtec.helloatu.manager.DatabaseManager;
import com.dtec.helloatu.utilities.GridSpacingItemDecoration;

import java.util.List;


public class EditInfoFragment extends Fragment {
    FragmentBaseActivity activity;
    DatabaseManager databaseManager;
    RecyclerView recycler_view_info_edit;
    InfoEditAdapter infoEditAdapter;
    private List<Crime> crimesList;

    public EditInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);
        activity = (FragmentBaseActivity) getActivity();
        databaseManager = new DatabaseManager(activity);
        crimesList = databaseManager.listCrime();

        recycler_view_info_edit = view.findViewById(R.id.recycler_view_info_edit);

        infoEditAdapter = new InfoEditAdapter(activity, activity, crimesList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
        recycler_view_info_edit.setLayoutManager(mLayoutManager);
        recycler_view_info_edit.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, activity), true));
        recycler_view_info_edit.setItemAnimator(new DefaultItemAnimator());

        recycler_view_info_edit.setAdapter(infoEditAdapter);

        // prepareCategorys();
        return view;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
