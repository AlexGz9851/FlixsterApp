package com.example.flixsterapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flixsterapp.models.Config;
import com.example.flixsterapp.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    //list of movies
    ArrayList<Movie> movies;
    // config needed for image urls
    Config config;
    // Context for rendering
    Context context;


    //initialize with list
    public MovieAdapter(ArrayList<Movie> movies){
        this.movies =movies;
    }

    // creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // get the context and create the inflater
         context = viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        // create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, viewGroup, false);
        // return viewHolder
        return new ViewHolder(movieView);
    }

    // binds a inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // get the movie data at the specified position
        Movie movie = movies.get(i);
        // populate the view with the movie data
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());


        // determine cell orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        //build url for poster image or backdrop image, depending on the orientation
        String imageUrl = isPortrait ?
                config.getImageUrl(config.getPosterSize(), movie.getPosterPath())
                : config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());

        // get the correct placeholder and imageview for the current orientation
        int placeholderId = isPortrait ?
                R.drawable.flicks_movie_placeholder
                : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ?
                viewHolder.ivPosterImage
                : viewHolder.ivPosterImage ;

        // load using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25,0 ))
                .placeholder(placeholderId)
                .error(R.drawable.flicks_movie_placeholder)
                .into(imageView);

    }
    // returns the total number of items from the list.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //create the view holder as a static inner class
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackdropImage;

        public ViewHolder (View itemView){
            super(itemView);
            // lookup view objects by id
            itemView.setOnClickListener(this);
            ivPosterImage =(ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Movie Clicked", Toast.LENGTH_LONG);
            // get position from adapter
            int position = getAdapterPosition();
            // validating position
            if(position != RecyclerView.NO_POSITION){
                // get seleted movie
                Movie movieSelected = movies.get(position);
                // create new intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movieSelected));
                String url = config.getImageUrl(config.getBackdropSize(), movieSelected.getBackdropPath());
                intent.putExtra("image_url", url);
                // show the activity
                context.startActivity(intent);

            }
        }
    }

}
