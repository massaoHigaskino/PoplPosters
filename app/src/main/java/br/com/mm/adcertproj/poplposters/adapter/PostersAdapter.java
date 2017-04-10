package br.com.mm.adcertproj.poplposters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.mm.adcertproj.poplposters.R;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.pref.MDBPreferences;

public class PostersAdapter extends RecyclerView.Adapter<PostersAdapter.PostersViewHolder> {

    // region ATTRIBUTES
    private final PostersClickListener mClickHandler;
    private MDBMovie[] mMovies;
    // endregion

    public PostersAdapter(PostersClickListener listener) {
        mClickHandler = listener;
    }

    // region PUBLIC METHODS
    public void setMovies(MDBMovie[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public PostersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.posters_grid_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId, parent, false);
        return new PostersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostersViewHolder holder, int position) {
        holder.bind(mMovies[position]);
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.length : 0;
    }
    // endregion

    // region AUXILIARY CLASSES
    public interface PostersClickListener {
        void onPostersClick(MDBMovie movie);
    }

    public class PostersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        private final ImageView posterImageView;

        public PostersViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            posterImageView = (ImageView) itemView.findViewById(R.id.iv_poster);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if(mClickHandler != null && mMovies != null && mMovies.length > adapterPosition && adapterPosition >= 0) {
                mClickHandler.onPostersClick(mMovies[adapterPosition]);
            }
        }

        public void bind(MDBMovie movie) {
            Picasso.with(context).load(MDBPreferences.buildPosterUrl(movie.getPosterPath())).into(posterImageView);
        }
    }
    // endregion
}
