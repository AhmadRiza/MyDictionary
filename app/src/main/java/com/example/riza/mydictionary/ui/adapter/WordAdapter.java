package com.example.riza.mydictionary.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riza.mydictionary.R;
import com.example.riza.mydictionary.data.model.Word;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by riza on 16/08/18.
 */
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.CustomViewHolder> {

    private ArrayList<Word> data;
    private ItemClickListener mListener;

    public Word getItem(int position) {
        return data.get(position);
    }

    public void updateData(ArrayList<Word> wordList) {
        data = wordList;
        notifyDataSetChanged();
    }

    public void clearData(){
        data.clear();
        notifyDataSetChanged();
    }

    public WordAdapter() {
        data = new ArrayList<>();
    }

    public void setOnItemClickListener(ItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        final CustomViewHolder mHolder = new CustomViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null){
                    mListener.onClick(view,mHolder.getPosition());
                }
            }
        });

        return mHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvName.setText(getItem(position).getName());
        holder.tvDesc.setText(getItem(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
