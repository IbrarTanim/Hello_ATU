package com.dtec.helloatu.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.dtec.helloatu.R;
import com.dtec.helloatu.adapter.CategoryAdapter;
import com.dtec.helloatu.dao.Crime;
import com.dtec.helloatu.listener.CustomItemClickListener;
import com.dtec.helloatu.manager.DatabaseManager;
import com.dtec.helloatu.pojo.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    MainActivity activity;
    RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categoryList;
    TextView tvMarque, tvTerrorismInfo;
    //Long currentCrime;
    //DatabaseManager databaseManager;
    //Crime crimId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        //databaseManager = new DatabaseManager(activity);
        //currentCrime = getIntent().getLongExtra("currentCrime", 0);
        //crimId = databaseManager.getCrimeById(currentCrime);

        tvMarque = (TextView) findViewById(R.id.tvMarque);
        tvTerrorismInfo = (TextView) findViewById(R.id.tvTerrorismInfo);
        tvMarque.setSelected(true);
        tvTerrorismInfo.setText(Html.fromHtml(getString(R.string.terrorism_info)));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(this, categoryList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                /*Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();*/



               /* final Intent intent;
                switch (position()){
                    case 0:
                        intent =  new Intent(context, FirstActivity.class);
                        break;

                    case 1:
                        intent =  new Intent(context, SecondActivity.class);
                        break;

                    default:
                        intent =  new Intent(context, DefaultActivity.class);
                        break;
                }
                context.startActivity(intent);*/


            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCategorys();


    }

    private void prepareCategorys() {

        int[] categoryPic = new int[]{
                R.drawable.ic_terrorist,
                R.drawable.ic_serinze,
                R.drawable.ic_bomb,
                R.drawable.ic_cyber_crime,
                R.drawable.ic_wanted,
                R.drawable.ic_info_cell,
        };


        int[] categoryColor = new int[]{

                ContextCompat.getColor(this, R.color.app_color),
                ContextCompat.getColor(this, R.color.cyber_crime_color),
                ContextCompat.getColor(this, R.color.bomb_crime_color),
                ContextCompat.getColor(this, R.color.organaizational_crime_color),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
        };

        Category category = new Category(categoryPic[0], categoryColor[0], getString(R.string.terrorism_bn), getString(R.string.terrorism_en));
        categoryList.add(category);

        category = new Category(categoryPic[1], categoryColor[1], getString(R.string.cyber_crime_bn), getString(R.string.cyber_crime_en));
        categoryList.add(category);

        category = new Category(categoryPic[2], categoryColor[2], getString(R.string.bomb_bn), getString(R.string.bomb_en));
        categoryList.add(category);

        category = new Category(categoryPic[3], categoryColor[3], getString(R.string.organaizational_crime_bn), getString(R.string.organaizational_crime_en));
        categoryList.add(category);

        category = new Category(categoryPic[4], categoryColor[4], getString(R.string.wanted_bn), getString(R.string.wanted_en));
        categoryList.add(category);

        category = new Category(categoryPic[5], categoryColor[5], getString(R.string.information_bn), getString(R.string.information_en));
        categoryList.add(category);
        adapter.notifyDataSetChanged();


    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {

        finish();

    }

}
