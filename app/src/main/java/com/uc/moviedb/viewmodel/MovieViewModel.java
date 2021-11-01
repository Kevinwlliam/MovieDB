package com.uc.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uc.moviedb.model.Movies;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {


        private MovieRepository repository;

        public MovieViewModel(@NonNull Application application){
            super(application);
            repository = MovieRepository.getInstance();
        }

        //==Begin of viewmodel get movie by id
        private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
        public void getMovieById(String movieId){
            resultGetMovieById = repository.getMovieData(movieId);
        }
        public LiveData<Movies> getResultGetMovieById(){
            return resultGetMovieById;
        }

        //==End of viewmodel get movie by id


        //==Begin of viewmodel get now playing
        private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
        public void getNowPlaying(int page){
            resultGetNowPlaying = repository.getNowPlayingData(page);
        }
        public LiveData<NowPlaying> getResultNowPlaying(){
            return resultGetNowPlaying;
        }

        //==End of viewmodel get now playing

        //==Begin of viewmodel get up coming
        private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();
        public void getUpComing(int page){
            resultGetUpComing = repository.getUpComingData(page);
        }
        public LiveData<UpComing> getResultUpComing(){
            return resultGetUpComing;
        }

        //==End of viewmodel get up coming

}
