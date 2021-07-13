package com.nJankov.android.Launches.Launches.MainLaunches;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nJankov.android.Launches.Launches.DetailPage.LaunchDetailActivity;
import com.nJankov.android.Launches.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nJankov on 2/22/2020.
 */

//array adapter za inflacija na list layot so launch items

public class LaunchArrayAdapter extends ArrayAdapter<LaunchItem> {


    Activity mContext;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param adapter A List of Words objects to display in a list
     */
    public LaunchArrayAdapter(Activity context, List<LaunchItem> adapter) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, adapter);
        mContext = context;
    }




    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }


    private String formatTime(Date timeObject){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E, LLL dd, h:mm a");
        return dateFormatter.format(timeObject);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //proveri dari se koristi istiot view, inaku inflacija na view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.launch_list_item, parent, false);
        }


        final LaunchItem currentLaunchItem = getItem(position);


        TextView locationTextView =(TextView) listItemView.findViewById(R.id.launch_location);
        locationTextView.setText(currentLaunchItem.getmLaunchLocation());


        TextView launchTitleView = (TextView) listItemView.findViewById(R.id.full_launch_title);
        launchTitleView.setText(currentLaunchItem.getmLaunchName());


        TextView launchTextDate = (TextView) listItemView.findViewById(R.id.launch_text_time);
        launchTextDate.setText(currentLaunchItem.getmTextLaunchDate());


        TextView missionTitle = (TextView) listItemView.findViewById(R.id.lone_mission_title);
        missionTitle.setText(currentLaunchItem.getmMissionName());


        TextView missionDescView = (TextView) listItemView.findViewById(R.id.lone_mission_description);
        missionDescView.setText(currentLaunchItem.getmMissionDescription());



        //implementacija na vteme i datum


        String timeToDisplay = "n/a";
        long currentDate = currentLaunchItem.getmNetLaunchDate();
        if (currentDate != 0){

            currentDate *= 1000;
            Date dateObject = new Date(currentDate);

            timeToDisplay = formatTime(dateObject);
        }


        Button watchLiveButton = (Button) listItemView.findViewById(R.id.watch_live_button);
        final String liveVideoUrl = currentLaunchItem.getmMediaUrl();

        if(liveVideoUrl != null){
            watchLiveButton.setVisibility(View.VISIBLE);
            watchLiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri liveMediaUri = Uri.parse(liveVideoUrl);

                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, liveMediaUri);


                    v.getContext().startActivity(websiteIntent);
                }
            });
        } else {
            watchLiveButton.setVisibility(View.GONE);
        }

        Button moreInfoButton = (Button) listItemView.findViewById(R.id.more_info_button);

        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent(getContext().getApplicationContext(), LaunchDetailActivity.class);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                infoIntent.putExtra("currentLaunchItem", currentLaunchItem);
                getContext().getApplicationContext().startActivity(infoIntent);
            }
        });


        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);

        timeTextView.setText(timeToDisplay);


        ImageView currentImageView = (ImageView) listItemView.findViewById(R.id.map_picture_view);



        final double latitude = currentLaunchItem.getmLaunchPadLatitude();
        final double longitude = currentLaunchItem.getmLaunchPadLongitude();



        Picasso.with(listItemView.getContext()).load(currentLaunchItem.getmRocketImageUrl()).into(currentImageView);

        FloatingActionButton mapsButton = (FloatingActionButton) listItemView.findViewById(R.id.fab);
        final String launchPadName = currentLaunchItem.getmLaunchPadName();


        mapsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "("+ launchPadName +")"));

                intent.setPackage("com.google.android.apps.maps");


                getContext().startActivity(intent);
            }
        });



        return listItemView;
    }


}
