package com.mobileacademy.newsreader.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobileacademy.newsreader.R;
import com.mobileacademy.newsreader.models.Article;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Daniela on 3/24/18.
 */

public class ArticleAdapter extends BaseAdapter {


    private List<Article> articleList;
    private Context context;
    private LayoutInflater inflater;


    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Article getItem(int position) {
        return articleList.get(position);
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //(1)inflate
            if(getItemViewType(position) == 0) {
                convertView = inflater.inflate(R.layout.item_article, parent, false);
            }else{
                convertView = inflater.inflate(R.layout.item_article2, parent, false);
            }

            ViewHolder holder = new ViewHolder();
            holder.date = convertView.findViewById(R.id.tv_date);
            holder.title = convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);

        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        //(2)convert the view
        holder.title.setText(articleList.get(position).getName());
        String date = DateUtils.formatDateTime(context, articleList.get(position).getDate(), 0);
        holder.date.setText(date);


        return convertView;
    }


    class ViewHolder {
        TextView title;
        TextView date;
    }
}
