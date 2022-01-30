package com.pankajkcodes.videodownloader.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.pankajkcodes.videodownloader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;


public class FBVideoFragment extends Fragment {

    VideoView fbVideoView;
    EditText getFbLink;
    Button getFbVideo;
    Button downloadFbVideo;
    MediaController mediaController;
    private Uri uri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_video, container, false);
        fbVideoView = root.findViewById(R.id.fb_video_view);
        downloadFbVideo = root.findViewById(R.id.fb_video_download_btn);
        getFbLink = root.findViewById(R.id.fb_video_link);

        getFbVideo = root.findViewById(R.id.get_fb_video);
        getFbVideo.setOnClickListener(view -> {
            Log.d("tag", getFbLink.getText().toString());
            getFacebookData();
        });

        downloadFbVideo.setOnClickListener(view -> {
            Toast.makeText(requireContext(), "Coming Soon..", Toast.LENGTH_SHORT).show();
        });
        return root;
    }

    private void getFacebookData() {
        URL url = null;
        try {
            url = new URL(getFbLink.getText().toString());
            String host = url.getHost();
            if (host.contains("facebook.com")) {
                new CallGetFbData().execute(getFbLink.getText().toString());
                Log.d("tag", getFbLink.getText().toString());
            } else
                Log.d("tag", "url not correct");
            Toast.makeText(requireContext(), "Something Wrong..", Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    class CallGetFbData extends AsyncTask<String, Void, Document> {

        Document fbDoc;

        @Override
        protected Document doInBackground(String... strings) {

            try {
                fbDoc = Jsoup.connect(strings[0]).get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbDoc;

        }

        @Override
        protected void onPostExecute(Document document) {
//            String videoUrl = document.select("meta[property=\"og:video\"]")
//                    .last().attr("content");
            String videoUrl = getFbLink.getText().toString();
            if (!videoUrl.equals("")) {
                Log.d("tag", videoUrl);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoUrl));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                        | DownloadManager.Request.NETWORK_WIFI);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle("Download");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,
                        "fb" + System.currentTimeMillis() + ".mp4");
                DownloadManager manager = ((DownloadManager) requireActivity()
                        .getSystemService(Context.DOWNLOAD_SERVICE));
                manager.enqueue(request);
                Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                uri = Uri.parse(videoUrl);
                fbVideoView.setMediaController(mediaController);
                fbVideoView.setVideoURI(uri);
                fbVideoView.requestFocus();
                fbVideoView.start();

            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}