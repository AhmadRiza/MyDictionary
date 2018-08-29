package com.example.riza.mydictionary.ui.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.riza.mydictionary.R;
import com.example.riza.mydictionary.data.model.Word;
import com.example.riza.mydictionary.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unbinder = ButterKnife.bind(this);

        Word word = getIntent().getParcelableExtra(MainActivity.WORD_KEY);
        if(word!=null){
            setView(word);
        }else {
            finish();
        }
    }

    private void setView(Word word) {
        tvName.setText(word.getName());
        tvDesc.setText(word.getDesc());
    }


    @Override
    protected void onDestroy() {
        if(unbinder!=null) unbinder.unbind();
        super.onDestroy();
    }
}
