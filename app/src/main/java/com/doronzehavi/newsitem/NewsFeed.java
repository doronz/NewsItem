package com.doronzehavi.newsitem;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.doronzehavi.newsitem.data.NewsItemContract;
import com.doronzehavi.newsitem.sync.NewsItemSyncTask;

public class NewsFeed extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,NewsItemAdapter.NewsItemAdapterOnClickHandler {

    private static final int ID_NEWSITEM_LOADER = 29;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    /*
    * The columns of data that we are interested in displaying within our MainActivity's list of
    * weather data.
    */
    public static final String[] MAIN_NEWSITEM_PROJECTION = {
            NewsItemContract.NewsEntry.COLUMN_TITLE,
            NewsItemContract.NewsEntry.COLUMN_SUMMARY,

    };

    /*
     * We store the indices of the values in the array of Strings above to more quickly be able to
     * access the data from our query. If the order of the Strings above changes, these indices
     * must be adjusted to match the order of the Strings.
     */
    public static final int INDEX_NEWS_TITLE = 0;
    public static final int INDEX_NEWS_SUMMARY = 1;
    private NewsItemAdapter mNewsItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);


        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_newsitem);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mNewsItemAdapter = new NewsItemAdapter(this, this);
        mRecyclerView.setAdapter(mNewsItemAdapter);

        showLoading();

        getSupportLoaderManager().initLoader(ID_NEWSITEM_LOADER, null, this);
        SyncNewsTask syncTask = new SyncNewsTask();
        syncTask.execute(this);

    }

    class SyncNewsTask extends AsyncTask<Context, Void, Void>{

        @Override
        protected Void doInBackground(Context... params) {
            NewsItemSyncTask.syncNewsItems(params[0]);
            return null;
        }
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick() {
        // TODO: Make clicks do something
    }

    /**
     * Called by the {@link android.support.v4.app.LoaderManagerImpl} when a new Loader needs to be
     * created. This Activity only uses one loader, so we don't necessarily NEED to check the
     * loaderId, but this is certainly best practice.
     *
     * @param id The loader ID for which we need to create a loader
     * @param args   Any arguments supplied by the caller
     * @return A new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_NEWSITEM_LOADER:
                Uri newsItemQueryUri = NewsItemContract.NewsEntry.CONTENT_URI;
                String sortOrder = NewsItemContract.NewsEntry.COLUMN_DATE + " ASC";



                return new CursorLoader(this,
                        newsItemQueryUri,
                        MAIN_NEWSITEM_PROJECTION,
                        null,
                        null,
                        sortOrder);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mNewsItemAdapter.swapCursor(data);
        mRecyclerView.scrollToPosition(0);
        if (data.getCount() != 0) showNewsFeed();
    }

    private void showNewsFeed() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNewsItemAdapter.swapCursor(null);
    }
}
