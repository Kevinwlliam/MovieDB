package com.uc.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.model.UpComing;
import com.uc.moviedb.view.activities.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.CardViewViewHolder> {

    private Context context;
    private List<UpComing.Results> listUpComing;
    private List<UpComing.Results> getListUpComing(){return listUpComing;}
    public void setListUpComing(List<UpComing.Results> listUpComing){
        this.listUpComing = listUpComing;
    }
    public void addListUpComing(List<UpComing.Results> listUpComing){
        this.listUpComing.addAll(listUpComing);
    }
    public UpComingAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_up_coming, parent,
                false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_date.setText(results.getRelease_date());
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.img_poster);
//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Intent intent = new Intent(context, MovieDetailsActivity.class);
//            intent.putExtra("movie_id","" + results.getId());
//            intent.putExtra("title_id","" + results.getTitle());
//            intent.putExtra("date_id","Release Date : " + results.getRelease_date());
//            intent.putExtra("overview_id", "" + results.getOverview());
//            intent.putExtra("rating_id", "Rating : " + results.getVote_average());
//            intent.putExtra("popular_id", "Popularity : " + results.getPopularity());
//            intent.putIntegerArrayListExtra("genre_id", (ArrayList<Integer>)results.getGenre_ids());
//            intent.putExtra("img_movie_details", "" + results.getPoster_path());
//            context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (getListUpComing() != null && !getListUpComing().isEmpty()) {
            itemCount = getListUpComing().size();
        }
        else {
            itemCount = 0;
        }
        return itemCount;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_upcoming);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_upcoming);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_upcoming);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_upcoming);
            cv = itemView.findViewById(R.id.cv_card_upcoming);
        }
    }
}
