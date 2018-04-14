package com.mobileacademy.newsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileacademy.newsreader.R;
import com.mobileacademy.newsreader.models.Article;

import java.util.List;

/**
 * Created by danielastamati on 27/03/2018.
 */

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder> {

    private List<Article> articleList;
    private Context context;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;


    public ArticlesRecyclerAdapter(Context context, List<Article> articleList, RecyclerView recyclerView) {
        this.context = context;
        this.articleList = articleList;
        inflater = LayoutInflater.from(context);
        this.recyclerView = recyclerView;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(articleList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recyclerView.isAnimating()) return;

                articleList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());

            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView title;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
}
