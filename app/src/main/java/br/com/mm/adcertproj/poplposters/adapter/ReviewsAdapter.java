package br.com.mm.adcertproj.poplposters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.mm.adcertproj.poplposters.R;
import br.com.mm.adcertproj.poplposters.model.MDBReview;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewsHolder> {

    // region ATTRIBUTES
    private MDBReview[] mReviews;
    // endregion

    // region PUBLIC METHODS
    public void setReviews(MDBReview[] reviews) {
        this.mReviews = reviews;
        notifyDataSetChanged();
    }
    @Override
    public ReviewsViewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.reviews_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId, parent, false);
        return new ReviewsViewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewsHolder holder, int position) {
        holder.bind(mReviews[position]);
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.length : 0;
    }
    // endregion

    // region AUXILIARY CLASSES
    public class ReviewsViewsHolder extends RecyclerView.ViewHolder {

        private final TextView reviewTextView;
        private final TextView authorTextView;

        public ReviewsViewsHolder(View itemView) {
            super(itemView);
            reviewTextView = (TextView) itemView.findViewById(R.id.tv_review);
            authorTextView = (TextView) itemView.findViewById(R.id.tv_author);
        }

        public void bind(MDBReview review) {
            reviewTextView.setText(review.getContent());
            authorTextView.setText(review.getAuthor());
        }
    }
    // endregion
}
