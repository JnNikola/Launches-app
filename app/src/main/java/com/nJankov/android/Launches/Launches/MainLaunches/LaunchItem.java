package com.nJankov.android.Launches.Launches.MainLaunches;

import java.io.Serializable;


/**
 * Created by nJankov on 2/21/2020.
 */


//javna klasa za informaciite za poletuvanjeto


public class LaunchItem implements Serializable {


    private String mLaunchName;


    private long mNetLaunchDate;


    private String mMediaUrl;


    private String mTextLaunchDate;


    private String mLaunchLocation;


    private String mMissionName;


    private String mMissionDescription;


    private String mRocketImageUrl;


    private double mLaunchPadLatitude;


    private double mLaunchPadLongitude;


    private String mLaunchPadName;


    private int mLaunchStatus;


    private long mLaunchWindowOpen;


    private long mLaunchWindowClose;


    private String mMissionType;


    private String mRocketName;


    private String mRocketConfiguration;


    private String mRocketFamily;



    //konstruktor

    public LaunchItem(String launchName, long netLaunchDate, String textLaunchDate, String launchLocation,
                      String missionTitle, String missionDescription, String mediaUrl, String rocketImageUrl,
                      double padLatitude, double padLongitude, String launchPadName, int launchStatus,
                      long launchWindowOpen, long launchWindowClose, String missionType, String rocketName,
                      String rocketConfiguration, String rocketFamily){
        mLaunchName = launchName;
        mNetLaunchDate = netLaunchDate;
        mTextLaunchDate = textLaunchDate;
        mLaunchLocation = launchLocation;
        mMissionName = missionTitle;
        mMissionDescription = missionDescription;
        mMediaUrl = mediaUrl;
        mRocketImageUrl = rocketImageUrl;
        mLaunchPadLatitude = padLatitude;
        mLaunchPadLongitude = padLongitude;
        mLaunchPadName = launchPadName;
        mLaunchStatus = launchStatus;
        mLaunchWindowOpen = launchWindowOpen;
        mLaunchWindowClose = launchWindowClose;
        mMissionType = missionType;
        mRocketName = rocketName;
        mRocketConfiguration = rocketConfiguration;
        mRocketFamily = rocketFamily;
    }


    public String getmLaunchName() {
        return mLaunchName;
    }


    public long getmNetLaunchDate() {
        return mNetLaunchDate;
    }


    public String getmMediaUrl() {
        return mMediaUrl;
    }


    public String getmTextLaunchDate() {
        return mTextLaunchDate;
    }


    public String getmMissionName() {
        return mMissionName;
    }


    public String getmMissionDescription() {
        return mMissionDescription;
    }


    public String getmLaunchLocation() {
        return mLaunchLocation;
    }


    public String getmRocketImageUrl() {
        return mRocketImageUrl;
    }


    public double getmLaunchPadLatitude() {
        return mLaunchPadLatitude;
    }


    public double getmLaunchPadLongitude() {
        return mLaunchPadLongitude;
    }


    public String getmLaunchPadName() {
        return mLaunchPadName;
    }


    public int getmLaunchStatus() {
        return mLaunchStatus;
    }


    public long getmLaunchWindowOpen() {
        return mLaunchWindowOpen;
    }


    public long getmLaunchWindowClose() {
        return mLaunchWindowClose;
    }


    public String getmMissionType() {
        return mMissionType;
    }


    public String getmRocketName() {
        return mRocketName;
    }


    public String getmRocketConfiguration() {
        return mRocketConfiguration;
    }


    public String getmRocketFamily() {
        return mRocketFamily;
    }

}
