package com.collegeproject.personx.Adaptor;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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

public class NewsRecycleViewAdaptor extends  RecyclerView.Adapter<NewsRecycleViewAdaptor.ViewHolder>{
  
  private Context context;
  private List<NewsModel> newsModelList;
  
  public NewsRecycleViewAdaptor(Context context, List<NewsModel> newsModelList) {
    this.context = context;
    this.newsModelList = newsModelList;
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
    holder.des.setText(model.getTiltle());
    holder.auth.setText(model.getAuth());
    holder.pub.setText(model.getName());
    Glide.with(context).load(model.getImgUrl()).into(holder.img);
  
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Dialog dialog = new Dialog(holder.itemView.getContext());
        dialog.setContentView(R.layout.news_dialog_popup);
        TextView name = dialog.findViewById(R.id.news_name_dia),
            auth = dialog.findViewById(R.id.news_auth_dia),
            title = dialog.findViewById(R.id.news_title_dia),
            con = dialog.findViewById(R.id.news_content_dia),
            pubAt = dialog.findViewById(R.id.news_pubat_dia),
            read = dialog.findViewById(R.id.read_more);
        ImageView img = dialog.findViewById(R.id.news_img_dia);
      
        name.setText(model.getName());
        auth.setText(model.getAuth());
        title.setText(model.getTiltle());
        con.setText(model.getDes());
        String linkedText = String.format("<a href=\"%s\">Read Complete Article.</a> ", model.getUrl());
        read.setText(Html.fromHtml(linkedText));
        read.setMovementMethod(LinkMovementMethod.getInstance());
        pubAt.setText(model.getPublishAt().replace("T", ", ").replace("Z", ""));
        Glide.with(dialog.getContext()).load(model.getImgUrl()).into(img);
      
        dialog.show();
      }
    });
  }
  
  @Override
  public int getItemCount() {
    return newsModelList.size();
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    
    ImageView img;
    TextView auth, des, pub;
    
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      
      img = itemView.findViewById(R.id.news_img);
      des = itemView.findViewById(R.id.news_head);
      pub = itemView.findViewById(R.id.news_pub);
      auth = itemView.findViewById(R.id.news_auth);
    }
  }
}
