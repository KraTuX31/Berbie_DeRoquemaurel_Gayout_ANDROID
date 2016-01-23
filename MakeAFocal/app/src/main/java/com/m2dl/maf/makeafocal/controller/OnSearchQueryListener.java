package com.m2dl.maf.makeafocal.controller;

import android.support.v7.widget.SearchView;

import java.util.Timer;
import java.util.TimerTask;


public class OnSearchQueryListener implements SearchView.OnQueryTextListener {
    /** Timer to check when execute query. */
    private Timer timer;

    /** Text inputted in tool bar. */
    private String query;

    /**
     * Construct a Manager of event inputted in search bar.
     */
    public OnSearchQueryListener() {
        timer = new Timer();
        query = "";
    }

    @Override
    public boolean onQueryTextSubmit(final String inputQuery) {
        filterQuery(inputQuery);
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String inputQuery) {
        filterQuery(inputQuery);
        return false;
    }

    /**
     * Request treatment according to the text inputted <i>inputQuery</i>;
     * @param inputQuery Inputted query.
     */
    private void filterQuery(String inputQuery) {
        if (!inputQuery.isEmpty()) {
            stopTimer();
            query = inputQuery;
            startTimer();
        }
    }

    /**
     * Start timer.
     * For each character inputted, timer is stopped then re-start.
     * Execution of the treatment <i>executeQuery</i> is only call when user
     * stop to input text (after 500 ms with entering character).
     */
    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executeQuery();
            }
        }, 500);
    }

    /**
     * Stop timer.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Execute query.
     */
    public void executeQuery() {
        // TODO
        // Here execute query with filter bar

    }


}
