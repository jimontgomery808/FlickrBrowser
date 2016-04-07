package com.example.josh.flickrbrowser;

import java.io.Serializable;

/**
 * Created by Josh on 3/28/2016.
 */
public class Photo implements Serializable
{
    // version number of photo class. If updates are made, version is updated
    // Standard when using Serializable interface
    private static final long serialVersionUID = 1L;

    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String mTitle, String mAuthor, String mAuthorId, String mLink, String mTags, String mImage)
    {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImage = mImage;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getAuthor()
    {
        return mAuthor;
    }

    public String getAuthorId()
    {
        return mAuthorId;
    }

    public String getLink()
    {
        return mLink;
    }

    public String getTags()
    {
        return mTags;
    }

    public String getImage()
    {
        return mImage;
    }

    @Override
    public String toString()
    {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
