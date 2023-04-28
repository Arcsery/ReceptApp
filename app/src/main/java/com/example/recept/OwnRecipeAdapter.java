package com.example.recept;

import android.app.Activity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class OwnRecipeAdapter extends RecyclerView.Adapter<OwnRecipeAdapter.ViewHolder> implements Filterable {
    private ArrayList<RecipeItem> mRecipeItemsData;
    private ArrayList<RecipeItem> mRecipeItemsDataAll;
    private Context mContext;

    private FirebaseFirestore db;
    private int lastPosition = -1;

    OwnRecipeAdapter(Context context, ArrayList<RecipeItem> itemsData){
        this.mRecipeItemsData = itemsData;
        this.mRecipeItemsDataAll = itemsData;
        this.mContext = context;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("ActivityVIEW", String.valueOf(viewType));
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ownlist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OwnRecipeAdapter.ViewHolder holder, int position) {
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

            itemView.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Activity", mId);
                    Log.d("Activity", mUserId);
                    Log.d("Activity", mDescription);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.collection("recipes").document(mId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("deletsucces", "DocumentSnapshot successfully deleted!");
                                    int position = getAdapterPosition();
                                    mRecipeItemsData.remove(position);
                                    ((Activity)mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyItemRemoved(position);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("deletefail", "Error deleting document", e);
                                }
                            });
                        }
                    }).start();
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
        }
    }


}



