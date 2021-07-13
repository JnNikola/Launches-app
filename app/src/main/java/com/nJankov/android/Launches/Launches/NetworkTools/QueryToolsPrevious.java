package com.nJankov.android.Launches.Launches.NetworkTools;

import android.text.TextUtils;
import android.util.Log;

import com.nJankov.android.Launches.Launches.MainLaunches.LaunchItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nJankov on 2/24/2020.
 */
public class QueryToolsPrevious {


    public static final String LOG_TAG = QueryToolsPrevious.class.getSimpleName();


    public QueryToolsPrevious(){}


    public static List<LaunchItem> fetchLaunchData(String requestUrl) {
        Log.e(LOG_TAG, "zema data za launch ");


        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem pri pravenje http request.", e);
        }


        return extractFeatureFromJson(jsonResponse);
    }



    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "greska pri pravenje URL ", e);
        }
        return url;
    }



    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "greska, response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem pri zemanje launch JSON rezultat.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }



    public static List<LaunchItem> extractFeatureFromJson(String launchesJson) {

        if (TextUtils.isEmpty(launchesJson)) {
            return null;
        }

        List<LaunchItem> launches = new ArrayList<>();

        try {


            JSONObject baseJsonResponse = new JSONObject(launchesJson);

            JSONArray baseLaunchList = baseJsonResponse.getJSONArray("launches");



            for (int i = baseLaunchList.length() - 1; i >= 0; i--) {


                JSONObject currentLaunch = baseLaunchList.getJSONObject(i);

                String launchName = currentLaunch.getString("name");

                long netTimeStamp = currentLaunch.getLong("netstamp");

                String textTimeStamp = currentLaunch.getString("net");

                JSONObject locationObject = currentLaunch.getJSONObject("location");

                String locationName = locationObject.getString("name");

                JSONArray missionsArray = currentLaunch.getJSONArray("missions");

                String missionName = "n/a";
                String missionDescription = "n/a";
                String missionType = "n/a";

                if (missionsArray.length() != 0) {

                    JSONObject firstMission = missionsArray.getJSONObject(0);

                    missionName = firstMission.getString("name");

                    missionDescription = firstMission.getString("description");

                    missionType = firstMission.getString("typeName");
                }



                JSONArray mediaArray = currentLaunch.getJSONArray("vidURLs");

                String firstMediaLink = null;
                if (mediaArray.length() > 0) {
                    firstMediaLink = mediaArray.getString(0);
                }


                JSONObject rocketObject = currentLaunch.getJSONObject("rocket");

                String rocketImageUrl = rocketObject.getString("imageURL");

                String rocketName = rocketObject.getString("name");

                String rocketConfiguration = rocketObject.getString("configuration");

                String rocketFamilyName = rocketObject.getString("familyname");



                JSONObject launchLocation = currentLaunch.getJSONObject("location");
                JSONArray padsArray = launchLocation.getJSONArray("pads");
                JSONObject firstPad = padsArray.getJSONObject(0);
                double padLatitude = firstPad.getDouble("latitude");
                double padLongitude = firstPad.getDouble("longitude");
                String padName = firstPad.getString("name");


                int launchStatus = currentLaunch.getInt("status");


                long launchWindowOpen = currentLaunch.getLong("wsstamp");
                long launchWindowClose = currentLaunch.getLong("westamp");




                LaunchItem launch = new LaunchItem(launchName, netTimeStamp, textTimeStamp, locationName,
                        missionName, missionDescription, firstMediaLink, rocketImageUrl, padLatitude,
                        padLongitude, padName, launchStatus, launchWindowOpen, launchWindowClose, missionType,
                        rocketName, rocketConfiguration, rocketFamilyName);


                launches.add(launch);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "problem pri parsiranje na json ", e);
        }


        return launches;
    }

}
