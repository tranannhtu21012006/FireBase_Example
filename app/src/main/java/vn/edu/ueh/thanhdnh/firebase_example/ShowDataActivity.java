package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowDataActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.reclyclerview);
        ArticleViewAdapter adapter = new ArticleViewAdapter(getBaseContext(), articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
      db.collection("articles")
        .orderBy("timestamp", Query.Direction.DESCENDING) // Sắp xếp giảm dần theo thời gian
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
          if (snapshots != null) {
            articles.clear();
            for (QueryDocumentSnapshot q : snapshots) {
              Map<String, Object> data = q.getData();
              String title = data.get("title") != null ? (String) data.get("title") : "";
              String desc = data.get("description") != null ? (String) data.get("description") : "";
              String img = data.get("img") != null ? (String) data.get("img") : "";
              int viewcount = data.get("viewcount") != null ? ((Long) data.get("viewcount")).intValue() : 0;
              long timestamp = data.get("timestamp") != null ? ((Long) data.get("timestamp")) : 0;
              
              Article article = new Article(title, desc, img, viewcount, timestamp);
              article.setId(q.getId()); // Lưu ID để biết bài nào mà click
              articles.add(article);
            }
            adapter.update(articles);
            adapter.notifyDataSetChanged();
          }
        }
      });
    }
}
