package vn.edu.ueh.thanhdnh.firebase_example;

import com.google.firebase.firestore.Exclude;

public class Article {
  @Exclude
  private String id; // Không lưu ID này dưới dạng field trong document
  private String title;
  private String description;
  private String img;
  private int viewcount;
  private long timestamp; // Thêm trường thời gian

  // Constructor rỗng cần thiết cho Firebase Firestore
  public Article() {}

  public Article(String title, String description, String img, int viewcount, long timestamp) {
    this.title = title;
    this.description = description;
    this.img = img;
    this.viewcount = viewcount;
    this.timestamp = timestamp;
  }

  @Exclude
  public String getId() {
    return id;
  }

  @Exclude
  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public int getViewcount() {
    return viewcount;
  }

  public void setViewcount(int viewcount) {
    this.viewcount = viewcount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
}