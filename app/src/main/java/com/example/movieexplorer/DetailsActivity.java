package com.example.movieexplorer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsActivity extends AppCompatActivity {
    private TextView name;
    private TextView genre;
    private TextView director;
    private RatingBar ratingBar;
    private TextView principalActor;
    private TextView description;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        name=findViewById(R.id.name);
        genre=findViewById(R.id.genre);
        director=findViewById(R.id.director);
        principalActor=findViewById(R.id.actor);
        ratingBar=findViewById(R.id.ratingBarDetails);
        image=findViewById(R.id.imageDetails);
        description=findViewById(R.id.description);

        String movieName = getIntent().getStringExtra("name");
        float stars = getIntent().getFloatExtra("stars",0);
        String movieGenre = getIntent().getStringExtra("genre");
        String movieDirctor = getIntent().getStringExtra("director");
        String movieActor = getIntent().getStringExtra("actor");
        String movieImage = getIntent().getStringExtra("image");
        String movieDescription = getIntent().getStringExtra("description");
        description.setText(movieDescription);
        name.setText(movieName);
        genre.setText(movieGenre);
        director.setText(movieDirctor);
        principalActor.setText(movieActor);
        ratingBar.setRating(stars);

        // image
        Glide.with(this)
                .asBitmap()
                .load(movieImage)
                .apply(new RequestOptions().override(100, 100))
                .into(image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Récupérer les paddings actuels définis dans le fichier XML
            int currentLeftPadding = v.getPaddingLeft();
            int currentTopPadding = v.getPaddingTop();
            int currentRightPadding = v.getPaddingRight();
            int currentBottomPadding = v.getPaddingBottom();

            // Ajouter les insets du système aux paddings actuels
            v.setPadding(
                    currentLeftPadding + systemBars.left,
                    currentTopPadding + systemBars.top,
                    currentRightPadding + systemBars.right,
                    currentBottomPadding + systemBars.bottom
            );
            return insets;
        });
    }
}