package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
  private Context context;
  private LayoutInflater mInflater;
  private List<Article> articles;

  public ArticleViewAdapter(Context context, List<Article> articles) {
    this.context = context;
    this.mInflater = LayoutInflater.from(context);
    this.articles = articles;
  }

  public void update(List<Article> articles){
    this.articles = articles;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View customView = mInflater.inflate(R.layout.contact_list, parent, false);
    return new ArticleViewHolder(customView, this);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
    Article currentArticle = articles.get(position);
    holder.getTxtTitle().setText(currentArticle.getTitle());
    holder.getTxtDescription().setText(currentArticle.getDescription());
    holder.getTxtViewCount().setText(currentArticle.getViewcount() + " views");

    // Load ảnh bằng Glide
    Glide.with(context)
         .load(currentArticle.getImg())
         .centerCrop()
         .into(holder.getImgArticle());

    // Sự kiện click mở màn hình Detail
    holder.itemView.setOnClickListener(v -> {
      Intent intent = new Intent(context, DetailActivity.class);
      intent.putExtra("ARTICLE_ID", currentArticle.getId()); // Truyền ID qua
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }
}