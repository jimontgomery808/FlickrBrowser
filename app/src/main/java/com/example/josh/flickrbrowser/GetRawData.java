package com.example.josh.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// Different download statuses
enum DownloadStatus{IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}


/**
 * GetRawData:
 * This class gets raw data from a URL
 */

public class GetRawData
{
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    /**************************************************************************
      Constructor
     **************************************************************************/
    public GetRawData(String mRawUrl)
    {
        this.mRawUrl = mRawUrl;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    /***************************************************************************
     Method: rest()
     returns: void
     --------------------------------------------------------------------------
     Resets mDownloadStatus, mRawUrl, and mData
     ***************************************************************************/
    public void reset()
    {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl         = null;
        this.mData           = null;
    }

    /***************************************************************************
     Method: getmData
     returns: String
     ---------------------------------------------------------------------------
     Getter method for mData
     ***************************************************************************/
    public String getmData()
    {
        return mData;
    }

    /***************************************************************************
     Method: getmDownloadStatus
     returns: DownloadStatus (enum)
     ---------------------------------------------------------------------------
     Getter method for mDownloadStatus
     ***************************************************************************/
    public DownloadStatus getmDownloadStatus()
    {
        return mDownloadStatus;
    }

    public void setmRawUrl(String mRawUrl)
    {
        this.mRawUrl = mRawUrl;
    }

    /***************************************************************************
     Method: execute
     returns: voide
     ---------------------------------------------------------------------------
     Executes DownloadData object
     ***************************************************************************/
    public void execute()
    {
        this.mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    /*
      DownloadRawData:
      Class to download raw data from URL
     */
    public class DownloadRawData extends AsyncTask<String, Void, String>
    {
        protected void onPostExecute(String webData)
        {
            mData = webData;
            Log.v(LOG_TAG, "Data returned was: " + mData);
            if(mData == null)
            {
                if(mRawUrl == null)
                {
                    mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
                }
                else
                {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }
            //Success
            else
            {
                mDownloadStatus = DownloadStatus.OK;
            }
        }

        // Autogenerate this
        @Override
        protected String doInBackground(String... params)
        {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if(params == null)
            {
                return null;
            }

            try
            {
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if(inputStream == null)
                {
                    return null;
                }

                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null)
                {
                    buffer.append(line + "\n");
                }
                return buffer.toString();

            }
            catch (IOException e)
            {
                Log.e(LOG_TAG,"Error",e);
                return null;
            }
            finally
            {
                if(urlConnection != null)
                {
                    urlConnection.disconnect();
                }

                if(reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (final IOException e)
                    {
                        Log.e(LOG_TAG, "Error closing stream", e);

                    }
                }
            }
        }
    }
}
