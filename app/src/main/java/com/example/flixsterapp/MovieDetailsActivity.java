package com.example.flixsterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixsterapp.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;
    String imageUrl;
    TextView tvTitle, tvOverview;
    RatingBar rbAverageVotes;
    ImageView ivDetails;

    public void setMovieAdapter(MovieAdapter movieAdapter) {
        this.movieAdapter = movieAdapter;
    }

    MovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        rbAverageVotes = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivDetails = (ImageView) findViewById(R.id.ivDetails);

        // unwrap the movie passed via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        imageUrl = getIntent().getStringExtra("image_url");
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        float voteAverage = movie.getVoteAverage().floatValue();
        rbAverageVotes.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        // load using glide
        Glide.with(this)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 25,0 ))
                .error(R.drawable.flicks_movie_placeholder)
                .into(ivDetails);


    }
}
