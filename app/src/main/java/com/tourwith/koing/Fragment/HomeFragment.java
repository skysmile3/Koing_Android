package com.tourwith.koing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tourwith.koing.Activity.HomeFilterActivity;
import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Activity.TourCreationActivity;
import com.tourwith.koing.Firebase.FirebaseTour;
import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.R;
import com.tourwith.koing.ViewPager.ViewPagerClickListener;
import com.tourwith.koing.ViewPager.ViewPagerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class HomeFragment extends Fragment implements ViewPagerClickListener {

    List<RecyclerItem> items = new ArrayList<>();
    ViewPager imageViewPager;
    Context context;
    ArrayList<String> imageArr;
    int baseItemViewHeight = 900;
    MainActivity activity;
    ViewPagerClickListener listener;
    FirebaseTour firebaseTour;
    ImageButton filterButton;
    TextView filterAreaText;

    public HomeFragment() {
    }

    public HomeFragment(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ////by hb
        ImageButton tourCreateButton = (ImageButton) view.findViewById(R.id.home_add_bt);
        tourCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TourCreationActivity.class);
                intent.putExtra("uid", activity.uid);
                startActivityForResult(intent, 0);
            }
        });

        ///by hb
        filterAreaText = (TextView) view.findViewById(R.id.filter_area_text);
        filterButton = (ImageButton) view.findViewById(R.id.home_filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HomeFilterActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        context = getContext();

        imageViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        imageViewPager.setPageMargin((int) getResources().getDimension(R.dimen.home_card_page_margin));
        imageViewPager.setClipToPadding(false);
        imageViewPager.setPadding((int) getResources().getDimension(R.dimen.home_card_padding_left), 0, (int) getResources().getDimension(R.dimen.home_card_padding_right), 0);

        //imageViewPager.setAdapter(new ViewPagerAdapter(getActivity().getLayoutInflater(),getContext(),this, 5));

        listener = this;

        firebaseTour = new FirebaseTour(imageViewPager, getActivity(), getContext(), listener);


        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //imageViewPager.setPageTransformer(true, new MagnifyingAnimPageTransInterface(200, baseItemViewHeight));


        return view;
    }

    public FirebaseTour getFirebaseTour() {
        return firebaseTour;
    }

    @Override
    public void onCardClicked(ViewPagerHolder holder, int position) {
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==1001){

            filterAreaText.setText(data.getStringExtra("area"));
            Tour tourForFiltering = new Tour();
            tourForFiltering.setNationality(data.getStringExtra("nationality"));
            tourForFiltering.setLang1(data.getStringExtra("language1"));
            tourForFiltering.setLang2(data.getStringExtra("language2"));
            tourForFiltering.setArea(data.getStringExtra("area"));
            tourForFiltering.setTour_type(data.getStringExtra("type"));
            tourForFiltering.setStart_timestamp(data.getLongExtra("period1", 0));
            tourForFiltering.setEnd_timestamp(data.getLongExtra("period2", 9509455240734l));



            firebaseTour.filter(tourForFiltering);

        }

    }
}
