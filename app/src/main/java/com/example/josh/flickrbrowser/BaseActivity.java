package com.example.josh.flickrbrowser;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Josh on 4/2/2016.
 */
public class BaseActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    public final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    protected Toolbar activateToolbar()
    {
        if(mToolbar == null)
        {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar != null)
            {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled()
    {
        activateToolbar();
        if(mToolbar != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return mToolbar;
    }
}