package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Juan on 3/29/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    private String[] mMovieData;
    private MovieAdapterOnClickListener mClickListener;

    public interface MovieAdapterOnClickListener{
        void onMovieClick(String movieId);
    }

    MovieAdapter(MovieAdapterOnClickListener listener){
        mClickListener = listener;

    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position){
        String[] movie = mMovieData[position].split("-");
        Picasso.with(viewHolder.activityContext).load(movie[2]).into(viewHolder.mMoviePoster);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(listItem, parent, false);

        return new MovieViewHolder(v, context);
    }

    @Override
    public int getItemCount(){
        if (mMovieData != null)
            return mMovieData.length;
        return 0;
    }

    public void setMovieData(String[] data){
        mMovieData = data;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public final TextView mMovieId;
        public final ImageView mMoviePoster;
        public Context activityContext;

        public MovieViewHolder(View view, Context context){
            super(view);
//            mMovieId = (TextView) view.findViewById(R.id.tv_movie_item);
            mMoviePoster = (ImageView) view.findViewById(R.id.iv_poster);
            activityContext = context;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String movieId = mMovieData[position];
            mClickListener.onMovieClick(movieId);
        }
    }
}
