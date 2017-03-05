package fr.Launcher.ImHere.searcher;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import fr.Launcher.ImHere.KissApplication;
import fr.Launcher.ImHere.MainActivity;
import fr.Launcher.ImHere.pojo.Pojo;

/**
 * Retrieve pojos from history
 */
public class HistorySearcher extends Searcher {
    private static final int MAX_RECORDS = 25;
    private SharedPreferences prefs;

    public HistorySearcher(MainActivity activity) {
        super(activity);
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    @Override
    protected List<Pojo> doInBackground(Void... voids) {
        // Ask for records
        boolean smartHistory = !prefs.getString("history-mode", "recency").equals("recency");
        boolean excludeFavorites = prefs.getBoolean("exclude-favorites", false);

        //Gather favorites
        ArrayList<Pojo> favoritesPojo = new ArrayList<Pojo>(0);
        if(excludeFavorites){
            favoritesPojo = KissApplication.getDataHandler(activity).getFavorites(activity.tryToRetrieve);
        }

        return KissApplication.getDataHandler(activity).getHistory(activity, MAX_RECORDS, smartHistory, favoritesPojo);
    }
}
