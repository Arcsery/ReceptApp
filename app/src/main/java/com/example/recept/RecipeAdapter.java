package com.example.recept;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> implements Filterable {
    private ArrayList<RecipeItem> mRecipeItemsData;
    private ArrayList<RecipeItem> mRecipeItemsDataAll;
    private Context mContext;
    private int lastPosition = -1;

    RecipeAdapter(Context context, ArrayList<RecipeItem> itemsData){
        this.mRecipeItemsData = itemsData;
        this.mRecipeItemsDataAll = itemsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        RecipeItem currentItem = mRecipeItemsData.get(position);

        holder.bindTo(currentItem);
        holder.setId(currentItem.getId());
        holder.setUserId(currentItem.getUserId());
        holder.setDescription(currentItem.getDescription());

        if(holder.getAbsoluteAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAbsoluteAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mRecipeItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return RecipeFilter;
    }

    private Filter RecipeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<RecipeItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = mRecipeItemsDataAll.size();
                results.values = mRecipeItemsDataAll;
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(RecipeItem item : mRecipeItemsDataAll){
                    if(item.getFoodName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mRecipeItemsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        private String mId;
        private String mUserId;
        private String mDescription;

        private TextView mfoodName;
        private TextView mSmallDescription;

        private ImageView mitemImage;
        private RatingBar mRatingbar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mfoodName = itemView.findViewById(R.id.itemTitle);
            mSmallDescription = itemView.findViewById(R.id.subTitle);
            mitemImage  = itemView.findViewById(R.id.itemImage);
            mRatingbar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.detailsbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Activity", mId);
                    Log.d("Activity", mUserId);
                    Log.d("Activity", mDescription);
                }
            });
        }

        public void setId(String id) {
            mId = id;
        }
        public void setUserId(String userId){mUserId = userId;}
        public void setDescription(String description){mDescription = description;}

        public void bindTo(RecipeItem currentItem) {
            mfoodName.setText(currentItem.getFoodName());
            mSmallDescription.setText(currentItem.getSmallDescription());
            mRatingbar.setRating(currentItem.getRatedInfo());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mitemImage);
        }
    }


}



