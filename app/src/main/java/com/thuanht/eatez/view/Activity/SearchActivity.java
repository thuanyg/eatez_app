package com.thuanht.eatez.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.thuanht.eatez.Adapter.PostHomeAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.database.database.AppDatabase;
import com.thuanht.eatez.database.entity.Suggestion;
import com.thuanht.eatez.databinding.ActivitySearchBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.utils.KeyboardUtils;
import com.thuanht.eatez.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private PostHomeAdapter adapter;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        setContentView(binding.getRoot());
        initUI();
        observer();
        eventHandler();
    }

    private void observer() {
        viewModel.getPost_results().observe(this, posts -> {
            if (posts == null) {
                this.posts.clear();
                binding.tvNoResultSearch.setVisibility(View.VISIBLE);
                binding.tvNoResultSearch.setText("No result found");
            } else {
                this.posts.clear();
                this.posts.addAll(posts);
                binding.tvNoResultSearch.setVisibility(View.GONE);
            }
            binding.progressSearch.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        });
    }

    private void initUI() {
        posts = new ArrayList<>();
        adapter = new PostHomeAdapter(this, posts, post -> goToPostDetailActivity(Integer.parseInt(post.getPostId())));
        binding.rcvSearchResult.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvSearchResult.setAdapter(adapter);
    }

    private void eventHandler() {
        binding.txtSearch.requestFocus();
        binding.btnBackSearch.setOnClickListener(v -> {
            finish();
        });

        androidx.cursoradapter.widget.CursorAdapter cursorAdapter = new CursorAdapter(this, null, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // Tạo một view mới cho mỗi item trong danh sách gợi ý
                LayoutInflater inflater = LayoutInflater.from(context);
                return inflater.inflate(R.layout.item_suggestion, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Bind dữ liệu từ con trỏ vào view
                TextView tvSuggestion = view.findViewById(R.id.tvSuggestion);
                int columnIndex = cursor.getColumnIndex("query_suggestion");
                String suggestion = cursor.getString(columnIndex);
                tvSuggestion.setText(suggestion);
                tvSuggestion.setOnClickListener(v -> {
                    SearchData(suggestion);
                    binding.txtSearch.setQuery(suggestion, true);
                });
            }
        };

        binding.txtSearch.setSuggestionsAdapter(cursorAdapter);
        binding.txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor newCursor = getCursorWithSuggestions(newText);
                cursorAdapter.swapCursor(newCursor);
                return true;
            }
        });
    }

    private void SearchData(String suggestion) {
        binding.progressSearch.setVisibility(View.VISIBLE);
        viewModel.Search(suggestion);
    }

    private Cursor getCursorWithSuggestions(String query) {
        List<Suggestion> suggestionList = AppDatabase.getInstance(this).suggestionDAO().selectAll();
        System.out.println(suggestionList);
        String[] columnNames = {"_id", "query_suggestion"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        int idx = 0;
        for (Suggestion suggestion: suggestionList) {
            if (suggestion.getSuggest_value().toLowerCase().contains(query.toLowerCase())) {
                cursor.addRow(new Object[]{idx++, suggestion.getSuggest_value()});
            }
        }
        return cursor;
    }

    private void goToPostDetailActivity(int postid) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postid", postid);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}