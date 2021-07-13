package com.nJankov.android.Launches.Launches.NetworkTools;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.nJankov.android.Launches.Launches.MainLaunches.LaunchItem;

import java.util.List;

/**
 *
 * Created by nJankov on 2/23/2020.
 */

public class LaunchAsyncTaskLoader extends AsyncTaskLoader<List<LaunchItem>> {


    private static final String LOG_TAG = LaunchAsyncTaskLoader.class.getName();


    private String mUrl;

    public LaunchAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG, "Loader pocnuva");
        forceLoad();
    }

    @Override
    public List<LaunchItem> loadInBackground() {
        Log.e(LOG_TAG, "Loader pocnuva pozadinski loading");
        if (mUrl == null) {
            return null;
        }

        List<LaunchItem> launches;
        // napravi network baranje, parsiraj go odgovorot, izvleci lista od launches.
        if (mUrl.contains("next")) {
            launches = QueryTools.fetchLaunchData(mUrl);
        } else {
            launches = QueryToolsPrevious.fetchLaunchData(mUrl);
        }
        return launches;
    }
}