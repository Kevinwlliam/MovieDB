package com.uc.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Movies;
import com.uc.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_text;
    private TextView text_title;
    private TextView text_date;
    private TextView text_overview;
    private TextView text_rating;
    private TextView text_genre;
    private TextView text_popular_details;
    private String text_product_companies;
    private String movie_id = "", title_id = "", date_id, overview_id, rating_id, img_movie, popular_id, product_id;
    private ArrayList<Integer> genre_id;
    private ImageView img_movie_details;
    private MovieViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        title_id = intent.getStringExtra("title_id");
        date_id = intent.getStringExtra("date_id");
        overview_id = intent.getStringExtra("overview_id");
        rating_id = intent.getStringExtra("rating_id");
        popular_id = intent.getStringExtra("popular_id");
        genre_id = intent.getIntegerArrayListExtra("genre_id");
        img_movie = intent.getStringExtra("img_movie_details");


        lbl_text = findViewById(R.id.lbl_movie_details);
        lbl_text.setText(movie_id);
        text_title = findViewById(R.id.text_movie_details);
        text_title.setText(title_id);
        text_date = findViewById(R.id.text_release_date_details);
        text_date.setText(date_id);
        text_overview = findViewById(R.id.text_overview_details);
        text_overview.setText(overview_id);
        text_rating = findViewById(R.id.text_rating_details);
        text_rating.setText(rating_id);
        text_genre = findViewById(R.id.text_genre_id);
        text_popular_details = findViewById(R.id.text_popular_details);
        text_popular_details.setText(popular_id);
//        text_genre.setText(genre_id);
        img_movie_details = findViewById(R.id.img_movie_details);
        String img_path = Const.IMG_URL + img_movie;
        Glide.with(MovieDetailsActivity.this).load(img_path).into(img_movie_details);

        view_model = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        view_model.getMovieById(movie_id);
        view_model.getResultGetMovieById().observe(MovieDetailsActivity.this, showNonPlaying);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private Observer<Movies> showNonPlaying = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String result = "";
            for (int i = 0; i < genre_id.size(); i++){
                for (int j = 0; j < movies.getGenres().size(); j++){
                    if (genre_id.get(i) == movies.getGenres().get(j).getId()){
                        result += movies.getGenres().get(j).getName();
                    }
                }
            }
            text_genre.setText(result);
        }
    };

    private Observer<Movies> showProductCompany= new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String result = "";
            for (int i = 0; i < genre_id.size(); i++){
                for (int j = 0; j < movies.getGenres().size(); j++){
                    if (genre_id.get(i) == movies.getGenres().get(j).getId()){
                        result += movies.getGenres().get(j).getName();
                    }
                }
            }
            text_genre.setText(result);
        }
    };

}