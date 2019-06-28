package com.example.flixsterapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    // the base url for loading images
    String imageBaseUrl;
    //Poster images size to use when fetching images part of the url
    String posterSize;

    public String getBackdropSize() {
        return backdropSize;
    }

    // the backdrop size to use when fetching images
    String backdropSize;

    public Config(JSONObject js) throws JSONException {
        JSONObject images = js.getJSONObject("images");
        // get image base url
        imageBaseUrl = images.getString("secure_base_url");
        // get the poster size
        JSONArray posterSizeOption = images.getJSONArray("poster_sizes");
        // index 3 --> w342
        posterSize= posterSizeOption.optString(3, "w342");
        // parse the backdrop sizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        // index 1 --> w780
        backdropSize = backdropSizeOptions.optString(1, "w780");
    }
    //helper method 4 creating urls
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s",imageBaseUrl,size,path );//concatenate all 3
    }
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
