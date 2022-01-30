package com.pankajkcodes.videodownloader.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pankajkcodes.videodownloader.R;
import com.pankajkcodes.videodownloader.activities.PicturesActivity;
import com.pankajkcodes.videodownloader.activities.VideoActivity;
import com.pankajkcodes.videodownloader.models.WpModel;
import com.pankajkcodes.videodownloader.utils.Constants;

import java.util.ArrayList;

public class WpAdapter extends RecyclerView.Adapter<WpAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WpModel> filesList;

    public WpAdapter(Context context, ArrayList<WpModel> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WpModel model = filesList.get(position);
        if (model.getUri().toString().endsWith(".mp4")){
            holder.playIcon.setVisibility(View.VISIBLE);
        }else {
            holder.playIcon.setVisibility(View.INVISIBLE);
        }
        Glide.with(context).load(model.getUri())
                .into(holder.mainStatus);

        holder.mainStatus.setOnClickListener(v -> {
            if (model.getUri().toString().endsWith(".mp4")){
                final String path = model.getPath();
                String destPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        Constants.SAVE_FOLDER_NAME;
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("VIDEO_DEST_PATH",destPath);
                intent.putExtra("VIDEO_FILE",path);
                intent.putExtra("VIDEO_FILE_NAME",model.getFilename());
                intent.putExtra("VIDEO_URI",model.getUri().toString());
                context.startActivity(intent);

            }else {
                final String path = model.getPath();
                String destPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        Constants.SAVE_FOLDER_NAME;
                Intent intent = new Intent(context, PicturesActivity.class);
                intent.putExtra("IMAGE_DEST_PATH",destPath);
                intent.putExtra("IMAGE_FILE",path);
                intent.putExtra("IMAGE_FILE_NAME",model.getFilename());
                intent.putExtra("IMAGE_URI",model.getUri().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView mainStatus, playIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainStatus = itemView.findViewById(R.id.mainStatus);
            playIcon = itemView.findViewById(R.id.playIcon);

        }
    }
}