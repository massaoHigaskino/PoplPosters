package br.com.mm.adcertproj.poplposters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.mm.adcertproj.poplposters.R;
import br.com.mm.adcertproj.poplposters.model.MDBVideo;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    // region ATTRIBUTES
    private final VideosClickListener mClickListener;
    private MDBVideo[] mVideos;
    // endregion

    public VideosAdapter(VideosClickListener listener) {
        this.mClickListener = listener;
    }

    // region PUBLIC METHODS
    public void setVideos(MDBVideo[] videos) {
        this.mVideos = videos;
        notifyDataSetChanged();
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.videos_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, int position) {
        holder.bind(mVideos[position]);
    }

    @Override
    public int getItemCount() {
        return mVideos != null ? mVideos.length : 0;
    }
    // endregion

    // region AUXILIARY CLASSES
    public interface VideosClickListener {
        void onVideosClick(MDBVideo video);
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView trailerNameTextView;

        public VideosViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            trailerNameTextView = (TextView) itemView.findViewById(R.id.tv_trailer_name);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if(mClickListener != null && mVideos != null && mVideos.length > adapterPosition && adapterPosition >= 0) {
                mClickListener.onVideosClick(mVideos[adapterPosition]);
            }
        }

        public void bind(MDBVideo video) {
            trailerNameTextView.setText(video.getName());
        }
    }
    // endregion
}
