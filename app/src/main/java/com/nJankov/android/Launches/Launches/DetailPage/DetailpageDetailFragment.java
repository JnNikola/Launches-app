package com.nJankov.android.Launches.Launches.DetailPage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nJankov.android.Launches.Launches.MainLaunches.LaunchItem;
import com.nJankov.android.Launches.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nJankov on 2/25/2020.
 */

//fragment za pokazuvanje na dopolnitelni info za poletuvanjeto

public class DetailpageDetailFragment extends Fragment {

    LaunchItem currentLaunch;

    public DetailpageDetailFragment(){

    }

    /**
     * Helper method to convert UNIX epoch time to date
     * @param dateObject of the launch
     * @return a formatted date
     */
    private String formatNetLaunchDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,  MMMM dd,  HH:mm", Locale.ENGLISH);
        return dateFormat.format(dateObject);
    }

    private String formatWindowTime(long launchWindowTime){

        if (launchWindowTime == 0) {
            return "n/a";
        } else {

            launchWindowTime *= 1000;
            Date dateObject = new Date(launchWindowTime);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            return dateFormat.format(dateObject);

        }
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detailpage_detailtab, container, false);

       currentLaunch = (LaunchItem) getArguments().getSerializable("thisLaunch");


       // status za lansiranje
       int launchStatus = currentLaunch.getmLaunchStatus();
       TextView launchStatusTextView = (TextView) rootView.findViewById(R.id.launch_status);

       switch(launchStatus){
           case 1:
               launchStatusTextView.setText(R.string.launch_is_go);
               launchStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.successful));
               break;
           case 2:
               launchStatusTextView.setText(R.string.launch_is_no_go);
               launchStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.unsuccessful));
               break;
           case 3:
               launchStatusTextView.setText(R.string.launch_success);
               launchStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.successful));
               break;
           case 4:
               launchStatusTextView.setText(R.string.launch_failed);
               launchStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.unsuccessful));
               break;
       }




       // Map Image View

       final double latitude = currentLaunch.getmLaunchPadLatitude();
       final double longitude = currentLaunch.getmLaunchPadLongitude();

       // otvori GMaps pri kliknuvanje FAB

       FloatingActionButton mapsButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
       final String launchPadName = currentLaunch.getmLaunchPadName();

       mapsButton.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){

               Intent intent = new Intent(Intent.ACTION_VIEW,
                       Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "("+ launchPadName +")"));
               // napravi explicit intent
               intent.setPackage("com.google.android.apps.maps");


               getContext().startActivity(intent);
           }
       });




       // Get Date Object for projected launch date

       long netLaunchLong = currentLaunch.getmNetLaunchDate();
       String timeToDisplay = "n/a";
       if (netLaunchLong != 0){

           netLaunchLong *= 1000;
           Date dateObject = new Date(netLaunchLong);

           timeToDisplay = formatNetLaunchDate(dateObject);
       }

       TextView detailLaunchDate = (TextView) rootView.findViewById(R.id.full_launch_date);
       detailLaunchDate.setText(timeToDisplay);




       // zemi date objekt za prozorec za poletuvanje

       long launchWindowStart = currentLaunch.getmLaunchWindowOpen();
       long launchWindowClose = currentLaunch.getmLaunchWindowClose();

       TextView detailLaunchWindowOpen = (TextView) rootView.findViewById(R.id.detail_window_open);
       TextView detailLaunchWindowClose = (TextView) rootView.findViewById(R.id.detail_window_close);

       detailLaunchWindowOpen.setText("Window Start: " + formatWindowTime(launchWindowStart));
       detailLaunchWindowClose.setText("Window Close: " + formatWindowTime(launchWindowClose));





       // Watch Live kopce

       Button watchLiveButton = (Button) rootView.findViewById(R.id.watch_live_button);
       final String liveVideoUrl = currentLaunch.getmMediaUrl();

       if(liveVideoUrl != null){
           watchLiveButton.setVisibility(View.VISIBLE);
           watchLiveButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Uri liveMediaUri = Uri.parse(liveVideoUrl);
                   // nov intent za view na  earthquake URI
                   Intent websiteIntent = new Intent(Intent.ACTION_VIEW, liveMediaUri);

                   v.getContext().startActivity(websiteIntent);
               }
           });
       } else {
           watchLiveButton.setVisibility(View.GONE);
       }


       return rootView;
    }


    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment newInstance(LaunchItem mCurrentLaunch) {
        DetailpageDetailFragment myDetailFragment = new DetailpageDetailFragment();

        Bundle launch = new Bundle();
        launch.putSerializable("thisLaunch", mCurrentLaunch);
        myDetailFragment.setArguments(launch);

        return myDetailFragment;
    }

}
