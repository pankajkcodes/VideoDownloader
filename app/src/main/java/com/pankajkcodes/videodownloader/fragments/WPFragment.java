package com.pankajkcodes.videodownloader.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pankajkcodes.videodownloader.R;
import com.pankajkcodes.videodownloader.adapters.WpAdapter;
import com.pankajkcodes.videodownloader.models.WpModel;
import com.pankajkcodes.videodownloader.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class WPFragment extends Fragment {


    private WpAdapter adapter;

    private File[] files;

    private final ArrayList<WpModel> filesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wp, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            try {
                setUpLayout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 1000);
        });
        return root;
    }

    private void setUpLayout() {

        filesList.clear();

        recyclerView.setHasFixedSize(true);

        adapter = new WpAdapter(requireContext(), getData());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                (3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<WpModel> getData() {

        WpModel model;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                Constants.FOLDER_NAME + "Media/.Statuses";
        File targetDir = new File(targetPath);
        files = targetDir.listFiles();
        for (File file : files) {
            model = new WpModel();
            model.setUri(Uri.fromFile(file));
            model.setFilename(file.getName());
            model.setPath(file.getAbsolutePath());
            if (!model.getUri().toString().endsWith(".nomedia")) {
                filesList.add(model);
            }
        }

        return filesList;
    }


}