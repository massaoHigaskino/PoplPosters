package br.com.mm.adcertproj.poplposters.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PostersAdapter {

    public interface PostersClickListener {
        void onPostersClick();
    }
    public class PostersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public PostersViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
