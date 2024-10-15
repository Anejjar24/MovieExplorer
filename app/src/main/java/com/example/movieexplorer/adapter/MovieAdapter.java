package com.example.movieexplorer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieexplorer.DetailsActivity;
import com.example.movieexplorer.R;
import com.example.movieexplorer.beans.Movie;
import com.example.movieexplorer.service.MovieService;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>  implements Filterable {
    private List<Movie> movies;
    private Context context;
    private static final String TAG = "MovieAdapter";

    private List<Movie> moviesFilter;
    private NewFilter mfilter;

    public MovieAdapter(Context context,List<Movie> movies ) {
        this.movies = movies;
        this.context = context;
        moviesFilter = new ArrayList<>();
        moviesFilter.addAll(movies);
        mfilter = new NewFilter(this);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        final MovieViewHolder holder=new MovieViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.edit_star_item, null,
                        false);
                final ImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar2);
                final TextView idss = popup.findViewById(R.id.idss);
                final TextView name = popup.findViewById(R.id.name);

                Bitmap bitmap =
                        ((BitmapDrawable)((ImageView)v.findViewById(R.id.image)).getDrawable()).getBitmap();
                img.setImageBitmap(bitmap);
                bar.setRating(((RatingBar)v.findViewById(R.id.ratingBar)).getRating());
                idss.setText(((TextView)v.findViewById(R.id.ids)).getText().toString());
                name.setText(((TextView)v.findViewById(R.id.name)).getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Rate Movie : ")
                        .setMessage("Give a rating between 1 and 5 :")
                        .setView(popup)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(idss.getText().toString());
                                Movie movie = MovieService.getInstance().findById(ids);
                                movie.setStar(s);
                                MovieService.getInstance().update(movie);
                                notifyItemChanged(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", null).setNeutralButton("See Details", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Naviguez vers une autre activité pour voir les détails du film
                                int movieId = Integer.parseInt(idss.getText().toString());
                                //System.out.println("message");
                                Movie movie = MovieService.getInstance().findById(movieId);
                                Intent intent = new Intent(context, DetailsActivity.class);
                                intent.putExtra("movie_id", movieId); // Passez l'ID du film
                                intent.putExtra("director", movie.getDirector());
                                intent.putExtra("description", movie.getDescription());

                                intent.putExtra("name", movie.getName());
                                intent.putExtra("genre", movie.getGenre());
                                intent.putExtra("actor", movie.getPrincipalActor());
                                intent.putExtra("stars", movie.getStar());
                                intent.putExtra("image", movie.getImg());
                                //star

                                context.startActivity(intent);
                            }
                        })
                        .create();
                dialog.show();
            }
        });

        return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Log.d(TAG, "onBindView call ! " + position);
        Glide.with(context)
               .asBitmap()
                .load(moviesFilter.get(position).getImg())
                .apply(new RequestOptions().override(100, 100))
                .into(holder.image);
//        Glide.with(context)
//                .load(R.drawable.ic_launcher_background) // Assurez-vous que sample_image est une image existante dans drawable
//                .into(holder.image);
        holder.name.setText(moviesFilter.get(position).getName());
        holder.stars.setRating(moviesFilter.get(position).getStar());
        holder.idss.setText(moviesFilter.get(position).getId()+"");


    }

    @Override
    public int getItemCount() {
        return moviesFilter.size();
    }
    @Override
    public Filter getFilter() {
        return mfilter;
    }


    public class MovieViewHolder extends  RecyclerView.ViewHolder{
         TextView name;
         ImageView image;
         RatingBar stars;
         ConstraintLayout parent;
        TextView idss;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            idss = itemView.findViewById(R.id.ids);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.image);
            stars=itemView.findViewById(R.id.ratingBar);
            parent=itemView.findViewById(R.id.parent);
        }
    }
    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;
        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            moviesFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                moviesFilter.addAll(movies);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Movie p : movies) {
                    if (p.getName().toLowerCase().startsWith(filterPattern)) {
                        moviesFilter.add(p);
                    }
                }
            }
            results.values = moviesFilter;
            results.count = moviesFilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesFilter = (List<Movie>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
