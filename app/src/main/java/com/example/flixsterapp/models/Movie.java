package com.example.flixsterapp.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {

    // values from API
    public String title;
    public String overview;
    public String posterPath; // just the path
    public Double voteAverage;

    public String getBackdropPath() {
        return backdropPath;
    }

    private String backdropPath;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    // initialize from JSON data
    public Movie (JSONObject object ) throws JSONException{
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath= object.getString("poster_path");
        backdropPath =object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");

    }
    public Movie(){} // required for parceler class
}
