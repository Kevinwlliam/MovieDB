package com.uc.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.uc.moviedb.R;
import com.uc.moviedb.adapter.NowPlayingAdapter;
import com.uc.moviedb.adapter.UpComingAdapter;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.viewmodel.MovieViewModel;
import com.uc.moviedb.helper.itemClickSupport;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpComingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpComingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpComingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
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

    private RecyclerView rv_up_coming;
    private MovieViewModel view_model;
    Boolean isLoading = false;
    int page = 1;
    UpComingAdapter adapter;
    private ProgressBar loadingbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        loadingbar = view.findViewById(R.id.progressbar_up_coming_fragment);
        loadingbar.setVisibility(View.INVISIBLE);

        rv_up_coming = view.findViewById(R.id.rv_up_coming_fragment);
        rv_up_coming.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UpComingAdapter(getActivity());
        rv_up_coming.setAdapter(adapter);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getUpComing(page);
        view_model.getResultUpComing().observe(getActivity(), showUpComing);

        rv_up_coming.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    view_model.getUpComing(page);
                    view_model.getResultUpComing().observe(getActivity(), showUpComingNextPage);
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

    private Observer<UpComing> showUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            adapter.setListUpComing(upComing.getResults());
            rv_up_coming.setAdapter(adapter);

            itemClickSupport.addTo(rv_up_coming).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+upComing.getResults().get(position).getId());
                    if (Navigation.findNavController(v).getCurrentDestination().getId() == R.id.upComingFragment){
                        Navigation.findNavController(v).navigate((R.id.action_upComingFragment_to_movieDetailsFragment), (bundle));
                    }
                }
            });
        }
    };

    private Observer<UpComing> showUpComingNextPage = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            adapter.addListUpComing(upComing.getResults());
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