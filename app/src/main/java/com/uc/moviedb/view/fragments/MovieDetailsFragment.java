package com.uc.moviedb.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uc.moviedb.LoadingD;
import com.uc.moviedb.R;
import com.uc.moviedb.adapter.ProductCompanyAdapter;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.helper.itemClickSupport;
import com.uc.moviedb.model.Movies;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.view.activities.MovieDetailsActivity;
import com.uc.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView text_title, text_date, text_overview, text_rating, text_genre, text_popular_details, text_product_company, text_tagline;
    private String movie_id = "", title_id = "", date_id, overview_id, rating_id, img_movie, popular_id;
    private ArrayList<Integer> genre_id;
    private ImageView img_movie_details, img_backdrop_movie_details_fragment, img_product_company;
    private MovieViewModel view_model;
    private RecyclerView rv_product_company;
    private LoadingD loadingD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        String movieId = getArguments().getString("movieId");

        text_title = view.findViewById(R.id.text_movie_details_fragment);
        text_date = view.findViewById(R.id.text_release_date_details_fragment);
        text_overview = view.findViewById(R.id.text_overview_details_fragment);
        text_rating = view.findViewById(R.id.text_rating_details_fragment);
        text_genre = view.findViewById(R.id.text_genre_id_fragment);
        text_popular_details = view.findViewById(R.id.text_popular_details_fragment);
        text_product_company = view.findViewById(R.id.text_product_company);
        text_tagline = view.findViewById(R.id.text_tagline);
        img_product_company = view.findViewById(R.id.img_product_company);
        img_movie_details = view.findViewById(R.id.img_movie_details_fragment);
        img_backdrop_movie_details_fragment = view.findViewById(R.id.img_backdrop_movie_details_fragment);
        rv_product_company = view.findViewById(R.id.rv_product_company);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getMovieById(movieId);
        view_model.getResultGetMovieById().observe(getActivity(), showMovieDetails);
        view_model.getResultGetMovieById().observe(getActivity(), showProductCompany);

        loadingD = new LoadingD(getActivity());
        loadingD.startLoadingDialog();

        return view;
    }

    private Observer<Movies> showMovieDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            text_title.setText(movies.getTitle());
            text_date.setText("Date : "+movies.getRelease_date());
            text_overview.setText(movies.getOverview());
            text_tagline.setText(movies.getTagline());
            text_rating.setText("Rating : "+movies.getVote_average()+" ("+movies.getVote_count()+")");

            String genre = "Genre : ";
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size()-1) {
                    genre += movies.getGenres().get(i).getName();
                }
                else {
                    genre += movies.getGenres().get(i).getName() + ", ";
                }
            }
            text_genre.setText(genre);

            text_popular_details.setText("Popularity : "+movies.getPopularity());
            Glide.with(MovieDetailsFragment.this).load(Const.IMG_URL + movies.getPoster_path().toString()).into(img_movie_details);
            Glide.with(MovieDetailsFragment.this).load(Const.IMG_URL + movies.getBackdrop_path().toString()).into(img_backdrop_movie_details_fragment);
            loadingD.stopProgress();
        }
    };

    private Observer<Movies> showProductCompany = new Observer<Movies>() {



        @Override
        public void onChanged(Movies movies) {

            List<Movies.ProductionCompanies> productionCompaniesList = movies.getProduction_companies();

            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

            ProductCompanyAdapter adapter = new ProductCompanyAdapter(getActivity());
            adapter.setListProductCompany(movies.getProduction_companies());
            rv_product_company.setAdapter(adapter);
            rv_product_company.setLayoutManager(layoutManager);

            itemClickSupport.addTo(rv_product_company).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    String name = productionCompaniesList.get(position).getName();
                    Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                }
            });
//            String img_path = Const.IMG_URL + img_product_company;
//            Glide.with(MovieDetailsFragment.this).load(img_path).into(img_product_company);
//            text_product_company.setText(""+movies.getProduction_companies());
        }

    };

}