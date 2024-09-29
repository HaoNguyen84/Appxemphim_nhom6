package com.example.appxemphim_nhom6.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim_nhom6.R;
import com.example.appxemphim_nhom6.data.model.ServerData;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<ServerData> episodeList;
    private OnEpisodeClickListener onEpisodeClickListener;

    public interface OnEpisodeClickListener {
        void onEpisodeClick(String linkM3u8);
    }

    public EpisodeAdapter(List<ServerData> episodeList, OnEpisodeClickListener listener) {
        this.episodeList = episodeList;
        this.onEpisodeClickListener = listener;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        ServerData episode = episodeList.get(position);
        holder.textViewEpisodeName.setText(episode.getName());
        holder.itemView.setOnClickListener(v -> onEpisodeClickListener.onEpisodeClick(episode.getLinkM3u8()));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEpisodeName;

        EpisodeViewHolder(View itemView) {
            super(itemView);
            textViewEpisodeName = itemView.findViewById(R.id.text_view_episode_name);
        }
    }
}


