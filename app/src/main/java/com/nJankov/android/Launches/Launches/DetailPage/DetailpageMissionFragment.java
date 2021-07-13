package com.nJankov.android.Launches.Launches.DetailPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nJankov.android.Launches.Launches.MainLaunches.LaunchItem;
import com.nJankov.android.Launches.R;

/**
 * Created by nJankov on 2/26/2020.
 */

//poveke detalji za misijata




public class DetailpageMissionFragment extends Fragment {

    LaunchItem mCurrentLaunch;

    public DetailpageMissionFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detailpage_mission, container, false);

        mCurrentLaunch = (LaunchItem) getArguments().getSerializable("thisLaunch");


        // ime na misijata
        String missionName = mCurrentLaunch.getmMissionName();
        TextView missionNameTextView = (TextView) rootView.findViewById(R.id.mission_name);
        missionNameTextView.setText(missionName);


        // tip na misija
        String missionType = mCurrentLaunch.getmMissionType();
        TextView missionTypeTextView = (TextView) rootView.findViewById(R.id.mission_type);
        missionTypeTextView.setText(missionType);


        // opis na misija
        String missionDescription = mCurrentLaunch.getmMissionDescription();
        TextView missionDescriptionTextView = (TextView) rootView.findViewById(R.id.mission_description);
        missionDescriptionTextView.setText(missionDescription);


        // ime na voziloto
        String rocketName = mCurrentLaunch.getmRocketName();
        TextView rocketNameTextView = (TextView) rootView.findViewById(R.id.rocketNameTextView);
        rocketNameTextView.setText(rocketName);


        // konfiguracija na voziloto
        String rocketConfiguration = getResources().getString(R.string.vehicleConfiguration);
        String rocketConfigVal = mCurrentLaunch.getmRocketConfiguration();

        if (rocketConfigVal.equals("")){
            rocketConfiguration += " n/a";
        } else {
            rocketConfiguration += " " + rocketConfigVal;
        }

        TextView rocketConfigTextView = (TextView) rootView.findViewById(R.id.rocketConfigTextView);
        rocketConfigTextView.setText(rocketConfiguration);


        // familija na vozilata
        String vehicleFamily = getResources().getString(R.string.vehicleFamily);
        String rocketFamilyVal = mCurrentLaunch.getmRocketFamily();

        if (rocketFamilyVal.equals("")){
            vehicleFamily += " n/a";
        } else {
            vehicleFamily += " " + rocketFamilyVal;
        }

        TextView rocketFamilyTextView = (TextView) rootView.findViewById(R.id.rocketFamilyTextView);
        rocketFamilyTextView.setText(vehicleFamily);



        return rootView;
    }

    public static Fragment newInstance(LaunchItem mCurrentLaunch) {

        DetailpageMissionFragment myDetailMissionFragment = new DetailpageMissionFragment();

        Bundle launch = new Bundle();
        launch.putSerializable("thisLaunch", mCurrentLaunch);
        myDetailMissionFragment.setArguments(launch);

        return myDetailMissionFragment;
    }
}
