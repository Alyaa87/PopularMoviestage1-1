package com.example.android.popularmoviestage1;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MovieData> data = Collections.emptyList();
    MovieData current;
    int currentPos = 0;
    private Context context;
    private LayoutInflater inflater;

    // create constructor to initialize context and data sent from MainActivity
    public MovieAdapter(Context context, List<MovieData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_details, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        MovieData current = data.get(position);


        // load image into imageview using picasso
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png" + current.poster_image);

    }
    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView posterImage;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.name_movie);
            posterImage= (ImageView) itemView.findViewById(R.id.image_movie);
        }

    }

}
