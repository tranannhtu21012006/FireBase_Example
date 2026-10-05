package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private TextView txtTitle, txtDescription, txtViewCount;
  private ImageView imgArticle;
  private ArticleViewAdapter adapter;

  public ArticleViewHolder(@NonNull View itemView, ArticleViewAdapter adapter) {
    super(itemView);
    txtTitle = itemView.findViewById(R.id.txt_name);
    txtDescription = itemView.findViewById(R.id.txt_phone);
    txtViewCount = itemView.findViewById(R.id.txt_viewcount);
    imgArticle = itemView.findViewById(R.id.img_article);
    this.adapter = adapter;
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public TextView getTxtDescription() {
    return txtDescription;
  }

  public TextView getTxtViewCount() {
    return txtViewCount;
  }

  public ImageView getImgArticle() {
    return imgArticle;
  }
}