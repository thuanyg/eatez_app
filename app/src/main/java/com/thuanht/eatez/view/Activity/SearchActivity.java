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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.thuanht.eatez.Adapter.PostHomeAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivitySearchBinding;
import com.thuanht.eatez.model.Post;
import com.thuanht.eatez.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private PostHomeAdapter adapter;
    private  List<Post> posts;
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
            if(posts == null){
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
        adapter = new PostHomeAdapter(this, posts, post -> {});
        binding.rcvSearchResult.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvSearchResult.setAdapter(adapter);
    }

    private void eventHandler() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        binding.txtSearch.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

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
        String[] listFoods = new String[]{
                "Bánh mì", "Bún bò Huế", "Bún chả", "Bún riêu", "Bún thịt nướng", "Cà phê sữa đá",
                "Chả cá Lã Vọng", "Chả giò", "Cơm tấm", "Gỏi cuốn", "Mì Quảng", "Nem chua", "Phở bò",
                "Phở gà", "Bánh cuốn", "Bánh xèo", "Bún đậu mắm tôm", "Bún đậu", "Bún mắm",
                "Bánh tráng trộn", "Bánh canh", "Bánh ít", "Bánh ngọt", "Bánh bèo",
                "Bún thang", "Chè", "Gỏi", "Hủ tiếu", "Mì xào", "Ốc", "Bánh canh cua", "Bánh bao",
                "Bánh bột lọc", "Bánh tét", "Bánh mì pate", "Bánh mì bơ", "Bánh tráng nướng", "Bún ốc",
                "Bánh mì chảo", "Bánh tráng cuốn", "Bánh phồng tôm", "Bánh đa cua"
        };

        String[] columnNames = {"_id", "query_suggestion"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        for (int i = 0; i < listFoods.length; i++) {
            if (listFoods[i].toLowerCase().contains(query.toLowerCase())) {
                cursor.addRow(new Object[]{i, listFoods[i]});
            }
        }
        return cursor;
    }
}