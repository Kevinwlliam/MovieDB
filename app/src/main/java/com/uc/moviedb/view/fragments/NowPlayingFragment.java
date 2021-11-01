package com.uc.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.uc.moviedb.R;
import com.uc.moviedb.adapter.NowPlayingAdapter;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.viewmodel.MovieViewModel;
import com.uc.moviedb.helper.itemClickSupport;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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

    private RecyclerView rv_now_playing;
    private MovieViewModel view_model;
    Boolean isLoading = false;
    int page = 1;
    NowPlayingAdapter adapter;
    private ProgressBar loadingbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        loadingbar = view.findViewById(R.id.progressbar_now_playing_fragment);
        loadingbar.setVisibility(View.INVISIBLE);

        rv_now_playing = view.findViewById(R.id.rv_now_playing_fragment);
        rv_now_playing.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NowPlayingAdapter(getActivity());
        rv_now_playing.setAdapter(adapter);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getNowPlaying(page);
        view_model.getResultNowPlaying().observe(getActivity(), showNonPlaying);

        rv_now_playing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (isLoading) {
                    if (layoutManager.findLastVisibleItemPosition() >= (20 * (page - 1) + 1)) {
                        isLoading = false;
                        loading(isLoading);
                    }
                }
                if (!isLoading && (layoutManager.findFirstVisibleItemPosition() >= (20 * page) - 10 && layoutManager.findFirstVisibleItemPosition() <= 20 * page)) {
                    isLoading = true;
                    loading(isLoading);
                    page += 1;
                    view_model.getNowPlaying(page);
                    view_model.getResultNowPlaying().observe(getActivity(), showNowPlayingNextPage);
                }
            }
            });

        return view;
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            loadingbar.setVisibility(View.VISIBLE);
        } else {
            loadingbar.setVisibility(View.INVISIBLE);
        }
    }

    private Observer<NowPlaying> showNonPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            adapter.setListNowPlaying(nowPlaying.getResults());
            rv_now_playing.setAdapter(adapter);

            itemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+nowPlaying.getResults().get(position).getId());
                    if (Navigation.findNavController(v).getCurrentDestination().getId() == R.id.nowPlayingFragment){
                    Navigation.findNavController(v).navigate((R.id.action_nowPlayingFragment_to_movieDetailsFragment2), (bundle));
                    }
                }
            });

        }
    };

    private Observer<NowPlaying> showNowPlayingNextPage = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            adapter.addListNowPlaying(nowPlaying.getResults());
            adapter.notifyItemInserted((20*page));
            int currentSize = 20*page;
            int nextSize = currentSize+20;
            while (currentSize < nextSize) {
                currentSize += 20;
            }
            adapter.notifyDataSetChanged();
        }
    };
}