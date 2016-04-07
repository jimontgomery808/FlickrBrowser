package com.example.josh.flickrbrowser;

import android.os.Bundle;

public class ViewPhotoDetailsActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);
        activateToolbarWithHomeEnabled();

        // Retrieves current intent, and stores into a Photo object
        Photo photo = (Photo) getIntent().getSerializableExtra(PHOTO_TRANSFER);

    }

}
