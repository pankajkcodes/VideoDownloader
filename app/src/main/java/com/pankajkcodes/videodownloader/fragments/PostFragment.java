package com.pankajkcodes.videodownloader.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pankajkcodes.videodownloader.R;
import com.pankajkcodes.videodownloader.models.GraphQlModel;

import org.apache.commons.lang3.StringUtils;

public class PostFragment extends Fragment {

    String URL = "NULL";
    ImageView imageView;
    EditText getReelLink;
    Button getPost;
    Button downloadPost;
    String reelUrl = "1";
    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_post, container, false);
        imageView = root.findViewById(R.id.post_view);
        downloadPost = root.findViewById(R.id.post_download_btn);
        getReelLink = root.findViewById(R.id.post_link);

        getPost = root.findViewById(R.id.get_post);
        getPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL = getReelLink.getText().toString().trim();
                if (getReelLink.equals("NULL")) {
                    Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

                } else {
                    String result = StringUtils.substringBefore(URL, "/?");
                    URL = result + "/?__a=1";
                    processData();
                }
            }


        });
        downloadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reelUrl.equals("1")) {
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                            | DownloadManager.Request.NETWORK_WIFI);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setTitle("Download");
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,
                            "insta" + System.currentTimeMillis()+".jpg");
                    DownloadManager manager = ((DownloadManager) requireActivity()
                            .getSystemService(Context.DOWNLOAD_SERVICE));
                    manager.enqueue(request);
                    Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return root;


    }

    private void processData() {
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                GraphQlModel graphQlModel = gson.fromJson(response, GraphQlModel.class);
                reelUrl = graphQlModel.getGraphql().getShortcode_media().getDisplay_url();
                uri = Uri.parse(reelUrl);
                Glide.with(requireContext()).load(uri)
                        .into(imageView);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}