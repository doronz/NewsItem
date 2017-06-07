package com.doronzehavi.newsitem;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder> {


    private static final int VIEW_TYPE_NEWS = 0;

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    private final NewsItemAdapterOnClickHandler mClickHandler;

    private Cursor mCursor;

    /**
     * The interface that receives onClick messages.
     */
    public interface NewsItemAdapterOnClickHandler {
        void onClick();
    }


    public NewsItemAdapter(@NonNull Context context, NewsItemAdapterOnClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
    }



    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (like ours does) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId;

        switch(viewType){

            case VIEW_TYPE_NEWS: {
                layoutId = R.layout.news_list_item;
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);

        view.setFocusable(true);

        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String title = mCursor.getString(FeedFragment.INDEX_NEWS_TITLE);
        String summary = mCursor.getString(FeedFragment.INDEX_NEWS_SUMMARY);

        holder.titleView.setText(title);
        holder.summaryView.setText(summary);

    }

    /**
     * Swaps the cursor used by the NewsItemAdapter for its weather data. This method is called by
     * MainActivity after a load has finished, as well as when the Loader responsible for loading
     * the news data is reset. When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newCursor the new cursor to use as NewsItemAdapter's data source
     */
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView titleView;
        final TextView summaryView;

        NewsItemViewHolder(View view){
            super(view);

            titleView = (TextView) view.findViewById(R.id.news_title);
            summaryView = (TextView) view.findViewById(R.id.news_summary);

            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            //TODO: Make something happen when you click an item.
        }
    }
}
