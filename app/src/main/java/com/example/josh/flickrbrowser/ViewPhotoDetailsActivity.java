package com.example.josh.flickrbrowser;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        //Populates photo_details layout with photo title
        TextView photoTitle = (TextView) findViewById(R.id.photo_title);
        photoTitle.setText("Title: " + photo.getTitle());

        //Populates photo_details layout with photo tags
        TextView photoTags = (TextView) findViewById(R.id.photo_tags);
        photoTags.setText("Tags: " + photo.getTags());

        //Populates photo_details layout with photo author
        TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
        photoAuthor.setText(photo.getAuthor());

        //Populates photo_details layout with photo image
        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getLink())
                .placeholder(R.drawable.placeholder)
                .into(photoImage);

    }

}
