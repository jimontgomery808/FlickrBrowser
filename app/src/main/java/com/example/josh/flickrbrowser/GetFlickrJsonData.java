package com.example.josh.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josh on 3/28/2016.
 */
public class GetFlickrJsonData extends GetRawData
{
    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll)
    {
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        mPhotos = new ArrayList<Photo>();
    }

    public void execute()
    {
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll)
    {
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";



        //builds URL
        // buildUpon function adds on to base URL
        mDestinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM,searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll? "ALL": "ANY")
                .appendQueryParameter(FORMAT_PARAM,"json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM,"1")
                .build();

        // If returns null, error occurs with URL (typo, etc)
        return mDestinationUri != null;
    }

    public List<Photo> getPhotos()
    {
        return mPhotos;
    }

    /***************************************************************************
     Method: processResult
     returns: void
     ---------------------------------------------------------------------------
     Creates a photo object, and adds to List<Photo> mPhotos
     ***************************************************************************/
    public void processResult()
    {
        // Error result if raw file is not able to be downloaded
        if(getmDownloadStatus() != DownloadStatus.OK)
        {
            Log.e(LOG_TAG, "Error downloading raw file");

            return;
        }

        // Strings: names of categories in JSON file to be used
        final String FLICKR_ITEMS     = "items";
        final String FLICKR_TITLE     = "title";
        final String FLICKR_MEDIA     = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR    = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK      = "link";
        final String FLICKR_TAGS      = "tags";

        try
        {
            // Creates a JSONObject to store each Json item
            JSONObject jsonData = new JSONObject(getmData());
            // Creates an array of JSON items (items include "title", "media"...etc)
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);

            // Loop through each component of the array
            for(int i = 0; i < itemsArray.length(); i++)
            {
                // Accesses the [i]th item of items array
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);

                // Retrives the title, author, authorId, link, and tags
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                //String link = jsonPhoto.getString(FLICKR_LINK);

                String tags = jsonPhoto.getString(FLICKR_TAGS);

                //Creates JSONObject that contains media array
                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                // Retrieves media URL from the media array
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);
                // Formats link correctly
                String link = photoUrl.replaceFirst("_m.","_b.");

                // Creates a Photo object
                Photo photoObject = new Photo(title, author, authorId, link,tags,photoUrl);
                // Adds photoObject to mPhotos List
                this.mPhotos.add(photoObject);
            }

            for(Photo singlePhoto: mPhotos)
            {
                Log.v(LOG_TAG, singlePhoto.toString());
            }
        }
        catch(JSONException jsone)
        {
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data");
        }
    }
    public class DownloadJsonData extends DownloadRawData
    {

        protected void onPostExecute(String webData)
        {
            super.onPostExecute(webData);
            processResult();

        }

        protected String doInBackground(String... params)
        {
            String[] par = { mDestinationUri.toString() };
            return super.doInBackground(par);
        }

    }


}
