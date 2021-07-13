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
//Metod za query na launchlibrary servis  i vraka lista od launches


public final class QueryTools {


    public static final String LOG_TAG = QueryTools.class.getSimpleName();



      //Query na launchlibrary dataset i vrati listo od  LaunchItem objekt kako reprezentacija na launches.

    public static List<LaunchItem> fetchLaunchData(String requestUrl) {
        Log.e(LOG_TAG, "zema data za launch");


        URL url = createUrl(requestUrl);

        // praka http request, prima json

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem pravejki HTTP request.", e);
        }

        // vadi relevantni polinje od json i pravi lista od LaunchItem

        List<LaunchItem> launches = extractFeatureFromJson(jsonResponse);


        return launches;
    }


    //vraka nov url objekt za deden url

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "greska so pravenje URL ", e);
        }
        return url;
    }


    //pravi http baranje za dedeno url i vraka string

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

            // ako baranjeto e uspesno (response code 200), togas parsiraj go odgovorot

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "greska, response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem pri primanje json odgovor.", e);
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

    // pretvori go imputstream vo string

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


    //vrati lista od launch item od json odgovorot

    public static List<LaunchItem> extractFeatureFromJson(String launchesJson) {

        if (TextUtils.isEmpty(launchesJson)) {
            return null;
        }


        List<LaunchItem> launches = new ArrayList<>();

        // ako ima problem so formatot na json, se pravi isklu;ok za da ne krasne aplikacijata

        try {


            JSONObject baseJsonResponse = new JSONObject(launchesJson);

            // vadi JSONArray povrzana so klucot "launches" koj e reprezentacija od lista na poletuvanja


            JSONArray baseLaunchList = baseJsonResponse.getJSONArray("launches");


            // za sekoj LaunchItem vo launchArray, napravi LaunchItem objekt

            for (int i = 0; i < baseLaunchList.length(); i++) {


                JSONObject currentLaunch = baseLaunchList.getJSONObject(i);

                //ime na poletuvanjeto

                String launchName = currentLaunch.getString("name");

                //unix epoch vreme

                long netTimeStamp = currentLaunch.getLong("netstamp");

                // Time Stamp

                String textTimeStamp = currentLaunch.getString("net");

                // objekt so informacii za likacijata

                JSONObject locationObject = currentLaunch.getJSONObject("location");

                //ime na likacijata

                String locationName = locationObject.getString("name");

                String missionName = "n/a";
                String missionDescription = "n/a";
                String missionType = "n/a";

                // niza so informacii za misijata

                JSONArray missionsArray = currentLaunch.getJSONArray("missions");

                if (missionsArray.length() != 0) {
                    JSONObject firstMission = missionsArray.getJSONObject(0);

                    missionName = firstMission.getString("name");

                    missionDescription = firstMission.getString("description");


                    missionType = firstMission.getString("typeName");
                }

                // zemi JSONArray koja sodrzi media URLs
                JSONArray mediaArray = currentLaunch.getJSONArray("vidURLs");

                String firstMediaLink = null;
                if (mediaArray.length() > 0) {
                    firstMediaLink = mediaArray.getString(0);
                }

                // insormacii za voziloto
                JSONObject rocketObject = currentLaunch.getJSONObject("rocket");
                // url slika od voziloto
                String rocketImageUrl = rocketObject.getString("imageURL");
                // ime an voziloto
                String rocketName = rocketObject.getString("name");
                // konfiguracija na voziloto
                String rocketConfiguration = rocketObject.getString("configuration");
                // ime na familijata vozila
                String rocketFamilyName = rocketObject.getString("familyname");


                // izvadi latitude i longitude od launchpad
                JSONObject launchLocation = currentLaunch.getJSONObject("location");
                JSONArray padsArray = launchLocation.getJSONArray("pads");
                JSONObject firstPad = padsArray.getJSONObject(0);
                double padLatitude = firstPad.getDouble("latitude");
                double padLongitude = firstPad.getDouble("longitude");
                String padName = firstPad.getString("name");

                // status na poletuvanjeto - Int (1 zeleno, 2 crveno, 3 uspesno, 4 neuspesno)
                int launchStatus = currentLaunch.getInt("status");

                // UNIX epoch timestamp za launch window -se otvara i -se zatvara
                long launchWindowOpen = currentLaunch.getLong("wsstamp");
                long launchWindowClose = currentLaunch.getLong("westamp");



                // napravi nov LaunchItem objekt so launch-name, timestamp, lokacvija, ime na misijata i opis, i media url od json odgovorot.
                LaunchItem launch = new LaunchItem(launchName, netTimeStamp, textTimeStamp, locationName,
                        missionName, missionDescription, firstMediaLink, rocketImageUrl, padLatitude,
                        padLongitude, padName, launchStatus, launchWindowOpen, launchWindowClose, missionType,
                        rocketName, rocketConfiguration, rocketFamilyName);

                //dodadi nov LaunchItem na listata od launches
                launches.add(launch);
            }

        } catch (JSONException e) {
            //ako ima greska tuka se faka isklucokot

            Log.e("QueryUtils", "problem pri parsiranje na json ", e);
        }

        //vrati lista od launches
        return launches;
    }


}
