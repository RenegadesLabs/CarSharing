package com.cardee.owner_profile_info.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.R;
import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.util.glide.CircleTransform;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {

    private final List<Review> mReviewsList;

    private final LayoutInflater mInflater;
    private final RequestManager mGlideRequestManager;
    private Context mContext;

    public ReviewListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideRequestManager = Glide.with(context);
        mReviewsList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public ReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_owner_profile_info_review, parent, false);
        return new ReviewListAdapter.ReviewListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReviewListViewHolder holder, int position) {

        holder.setImage(mReviewsList.get(position).getRenter().getProfilePhoto(), mGlideRequestManager, mContext);
//        holder.setCarRate();
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    public void insert(List<Review> reviews) {
        for (Review review : reviews) {
            if (!mReviewsList.contains(review)) {
                mReviewsList.add(review);
            }
        }
        notifyDataSetChanged();
    }

    public static class ReviewListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mProfileImage;
        private final TextView mProfileName;
        private final TextView mReviewDate;
        private final TextView mCarTitle;
        private final TextView mCarRate;
        private final TextView mReviewText;

        public ReviewListViewHolder(View itemView) {
            super(itemView);

            mProfileImage = itemView.findViewById(R.id.review_profile_image);
            mProfileName = itemView.findViewById(R.id.review_profile_name);
            mReviewDate = itemView.findViewById(R.id.review_date);
            mCarTitle = itemView.findViewById(R.id.review_car_text);
            mCarRate = itemView.findViewById(R.id.review_rate_text);
            mReviewText = itemView.findViewById(R.id.review_text);
        }

        public void setImage(String link, RequestManager imageRequestManager, Context context) {
            imageRequestManager.load(link)
                    .transform(new CircleTransform(context))
                    .error(R.drawable.img_no_car)
                    .into(mProfileImage);
        }

        public void setTextFields(Review review) {
            mProfileName.setText(review.getRenter().getName());
            mReviewDate.setText(review.getReviewDate()); // TODO format date
            mReviewText.setText(review.getReview());

            mCarTitle.setText(""); // TODO
            mCarRate.setText(""); // TODO
        }

    }
}
