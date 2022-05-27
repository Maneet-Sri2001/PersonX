package com.collegeproject.personx.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.collegeproject.personx.Model.NewsModel;
import com.collegeproject.personx.R;

import java.util.List;

public class NewsRecycleViewAdaptor extends RecyclerView.Adapter<NewsRecycleViewAdaptor.ViewHolder> {
  
  private final List<NewsModel> newsModelList;
  private final OnNewsClickListener onNewsClickListener;
  Activity activity;
  
  public NewsRecycleViewAdaptor(List<NewsModel> newsModelList, OnNewsClickListener onNewsClickListener, Activity activity) {
    this.newsModelList = newsModelList;
    this.onNewsClickListener = onNewsClickListener;
    this.activity = activity;
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_news_recycler, null, true);
    return new NewsRecycleViewAdaptor.ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final NewsModel model = newsModelList.get(position);
    holder.des.setText(model.getTitle());
    holder.auth.setText(model.getAuthor());
    holder.pub.setText(model.getPublishAt());
    holder.source.setText(model.getSourceName());
    Glide.with(holder.itemView).load(model.getImgUrl()).into(holder.img);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(model.getNewsUrl()));
        view.getContext().startActivity(intent);
      }
    });
  }
  
  @Override
  public int getItemCount() {
    return newsModelList.size();
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
    ImageView img;
    TextView auth, des, pub, source;
    
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      
      img = itemView.findViewById(R.id.news_img);
      des = itemView.findViewById(R.id.news_title);
      pub = itemView.findViewById(R.id.news_publishAt);
      auth = itemView.findViewById(R.id.news_author);
      source = itemView.findViewById(R.id.news_source);
    }
    
    @Override
    public void onClick(View view) {
      onNewsClickListener.onNewsClick(newsModelList.get(getAdapterPosition()));
    }
  }
}
