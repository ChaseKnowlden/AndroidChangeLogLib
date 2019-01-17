package com.example.changeloglibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.example.changeloglibrary.Constants;
import com.example.changeloglibrary.R;
import com.example.changeloglibrary.Util;
import com.example.changeloglibrary.internal.ChangeLog;
import com.example.changeloglibrary.internal.ChangeLogRecyclerViewAdapter;
import com.example.changeloglibrary.parser.XmlParser;

public class ChangeLogRecyclerView extends RecyclerView {

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------
    protected int mRowLayoutId= Constants.mRowLayoutId;
    protected int mRowHeaderLayoutId= Constants.mRowHeaderLayoutId;
    protected int mChangeLogFileResourceId=Constants.mChangeLogFileResourceId;
    protected String mChangeLogFileResourceUrl=null;

    //--------------------------------------------------------------------------
    protected static String TAG="ChangeLogRecyclerView";

    // Adapter
    protected ChangeLogRecyclerViewAdapter mAdapter;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public ChangeLogRecyclerView(Context context) {
        this(context, null);
    }

    public ChangeLogRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeLogRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    /**
     * Initialize
     *
     * @param attrs
     * @param defStyle
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void init(AttributeSet attrs, int defStyle){

        //Init attrs
        initAttrs(attrs, defStyle);

        //Init adapter
        initAdapter();

        //Init the LayoutManager
        initLayoutManager();

    }

    /**
     * Init the Layout Manager
     */
    protected void initLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.setLayoutManager(layoutManager);
    }

    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.ChangeLogListView, defStyle, defStyle);

        try {
            //Layout for rows and header
            mRowLayoutId = a.getResourceId(R.styleable.ChangeLogListView_rowLayoutId, mRowLayoutId);
            mRowHeaderLayoutId = a.getResourceId(R.styleable.ChangeLogListView_rowHeaderLayoutId, mRowHeaderLayoutId);

            //Changelog.xml file
            mChangeLogFileResourceId = a.getResourceId(R.styleable.ChangeLogListView_changeLogFileResourceId,mChangeLogFileResourceId);

            mChangeLogFileResourceUrl = a.getString(R.styleable.ChangeLogListView_changeLogFileResourceUrl);
            //String which is used in header row for Version
            //mStringVersionHeader= a.getResourceId(R.styleable.ChangeLogListView_StringVersionHeader,mStringVersionHeader);

        } finally {
            a.recycle();
        }
    }


    /**
     * Init adapter
     */
    protected void initAdapter() {

        try{
            //Read and parse changelog.xml
            XmlParser parse;
            if (mChangeLogFileResourceUrl!=null)
                parse = new XmlParser(getContext(),mChangeLogFileResourceUrl);
            else
                parse = new XmlParser(getContext(),mChangeLogFileResourceId);
            //ChangeLog chg=parse.readChangeLogFile();
            ChangeLog chg = new ChangeLog();

            //Create adapter and set custom attrs
            mAdapter = new ChangeLogRecyclerViewAdapter(getContext(),chg.getRows());
            mAdapter.setRowLayoutId(mRowLayoutId);
            mAdapter.setRowHeaderLayoutId(mRowHeaderLayoutId);

            //Parse in a separate Thread to avoid UI block with large files
            if (mChangeLogFileResourceUrl==null || (mChangeLogFileResourceUrl!=null && Util.isConnected(getContext())))
                new ParseAsyncTask(mAdapter,parse).execute();
            else
                Toast.makeText(getContext(), R.string.changelog_internal_error_internet_connection, Toast.LENGTH_LONG).show();
            setAdapter(mAdapter);

        }catch (Exception e){
            Log.e(TAG, getResources().getString(R.string.changelog_internal_error_parsing), e);
        }

    }


    /**
     * Async Task to parse xml file in a separate thread
     *
     */
    protected class ParseAsyncTask extends AsyncTask<Void, Void, ChangeLog> {

        private ChangeLogRecyclerViewAdapter mAdapter;
        private XmlParser mParse;

        public ParseAsyncTask(ChangeLogRecyclerViewAdapter adapter, XmlParser parse){
            mAdapter=adapter;
            mParse= parse;
        }

        @Override
        protected ChangeLog doInBackground(Void... params) {

            try{
                if (mParse!=null){
                    ChangeLog chg=mParse.readChangeLogFile();
                    return chg;
                }
            }catch (Exception e){
                Log.e(TAG,getResources().getString(R.string.changelog_internal_error_parsing),e);
            }
            return null;
        }

        protected void onPostExecute(ChangeLog chg) {

            //Notify data changed
            if (chg!=null){
                mAdapter.add(chg.getRows());                            }
        }
    }


}
