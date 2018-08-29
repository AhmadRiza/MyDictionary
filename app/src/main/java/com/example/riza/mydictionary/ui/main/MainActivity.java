package com.example.riza.mydictionary.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.riza.mydictionary.R;
import com.example.riza.mydictionary.data.db.DatabaseContract;
import com.example.riza.mydictionary.data.db.WordHelper;
import com.example.riza.mydictionary.data.model.Word;
import com.example.riza.mydictionary.ui.adapter.ItemClickListener;
import com.example.riza.mydictionary.ui.adapter.WordAdapter;
import com.example.riza.mydictionary.ui.details.DetailsActivity;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    public static final String WORD_KEY = "word";

    @BindView(R.id.search_view)SearchView searchView;
    @BindView(R.id.view_lang)LinearLayout langView;
    @BindView(R.id.tv_lang1)TextView tvLang1;
    @BindView(R.id.tv_lang2)TextView tvLang2;
    @BindView(R.id.recycle_view)RecyclerView recyclerView;

    private Unbinder unbinder;
    private boolean isIndotoEnglish;
    private WordAdapter wordAdapter;
    private WordHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        unbinder = ButterKnife.bind(this);

        wordAdapter = new WordAdapter();
        dbHelper = new WordHelper(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        setUp();
    }

    private void setUp() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(wordAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

//        setVisibleView

        isIndotoEnglish = true;

        tvLang1.setText(R.string.indo);
        tvLang2.setText(R.string.eng);

        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.query_hint_indo));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return true;
            }
        });

        langView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isIndotoEnglish){
                    isIndotoEnglish = false;
                    tvLang1.setText(R.string.eng);
                    tvLang2.setText(R.string.indo);
                    searchView.setQueryHint(getString(R.string.query_hint_eng));
                }else{
                    isIndotoEnglish = true;
                    tvLang1.setText(R.string.indo);
                    tvLang2.setText(R.string.eng);
                    searchView.setQueryHint(getString(R.string.query_hint_indo));
                }
                searchView.setQuery("",true);

            }
        });

        wordAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Word word = wordAdapter.getItem(position);
                if(word!=null){

                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra(WORD_KEY, word);
                    startActivity(intent);

                }
            }
        });

    }

    private void searchData(String newText) {

        if(TextUtils.isEmpty(newText)){
            wordAdapter.clearData();
            return;
        }

        if(isIndotoEnglish){
            wordAdapter.updateData(dbHelper.getDataByName(newText, DatabaseContract.TABLE_INEN));
        }else{
            wordAdapter.updateData(dbHelper.getDataByName(newText, DatabaseContract.TABLE_ENIN));
        }

        scrollUp();

    }

    private void scrollUp(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView
                .getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    protected void onDestroy() {
        if(dbHelper!=null){
            dbHelper.close();
        }
        if(unbinder!=null)unbinder.unbind();
        super.onDestroy();
    }

}
