package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView tvTitleDetail, tvViewCountDetail, tvDescDetail;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgDetail = findViewById(R.id.imgDetail);
        tvTitleDetail = findViewById(R.id.tvTitleDetail);
        tvViewCountDetail = findViewById(R.id.tvViewCountDetail);
        tvDescDetail = findViewById(R.id.tvDescDetail);

        db = FirebaseFirestore.getInstance();

        String articleId = getIntent().getStringExtra("ARTICLE_ID");
        if (articleId != null) {
            // Tăng viewcount lên 1 trên Firebase
            db.collection("articles").document(articleId)
              .update("viewcount", FieldValue.increment(1))
              .addOnSuccessListener(aVoid -> {
                  // Sau khi cập nhật thành công, lấy dữ liệu về hiển thị
                  loadArticleData(articleId);
              })
              .addOnFailureListener(e -> Toast.makeText(DetailActivity.this, "Lỗi cập nhật lượt xem", Toast.LENGTH_SHORT).show());
        }
    }

    private void loadArticleData(String id) {
        // Thay vì dùng .get() (chỉ lấy 1 lần), ta dùng addSnapshotListener để lắng nghe realtime
        db.collection("articles").document(id).addSnapshotListener((documentSnapshot, error) -> {
            if (error != null) {
                Toast.makeText(DetailActivity.this, "Không thể tải bài viết: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Article article = documentSnapshot.toObject(Article.class);
                if (article != null) {
                    tvTitleDetail.setText(article.getTitle());
                    tvDescDetail.setText(article.getDescription());

                    Long viewcount = documentSnapshot.getLong("viewcount");
                    tvViewCountDetail.setText((viewcount != null ? viewcount : 0) + " views");

                    // Tránh load lại ảnh liên tục nếu URL không đổi, nhưng để đơn giản ta cứ load
                    Glide.with(this)
                         .load(article.getImg())
                         .centerCrop()
                         .into(imgDetail);
                }
            }
        });
    }
}