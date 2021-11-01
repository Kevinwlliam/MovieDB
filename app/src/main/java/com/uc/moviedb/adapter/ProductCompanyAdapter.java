package com.uc.moviedb.adapter;

import android.content.Context;
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
import com.uc.moviedb.model.Movies;
import com.uc.moviedb.model.NowPlaying;

import java.util.List;

public class ProductCompanyAdapter extends RecyclerView.Adapter<ProductCompanyAdapter.CardViewViewHolder> {

    private Context context;
    private List<Movies.ProductionCompanies> listProductCompany;
    private List<Movies.ProductionCompanies> getListProductCompany(){
        return listProductCompany;
    }

    public void setListProductCompany(List<Movies.ProductionCompanies> listProductCompany){
        this.listProductCompany = listProductCompany;
    }
    public ProductCompanyAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_product_company, parent, false);
        return new ProductCompanyAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCompanyAdapter.CardViewViewHolder holder, int position) {
        final Movies.ProductionCompanies results = getListProductCompany().get(position);
        holder.lbl_title.setText(results.getName());
        if (results.getLogo_path() == null){
            Glide.with(context)
                    .load(R.drawable.ic_baseline_product_company)
                    .into(holder.img_poster_product_company);
        } else {
            Glide.with(context)
                    .load(Const.IMG_URL + results.getLogo_path())
                    .into(holder.img_poster_product_company);
        }

    }

    @Override
    public int getItemCount() {
        return getListProductCompany().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster_product_company;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster_product_company = itemView.findViewById(R.id.img_product_company);
            lbl_title = itemView.findViewById(R.id.text_product_company);
            cv = itemView.findViewById(R.id.cv_card_product_company);
        }
    }
}
