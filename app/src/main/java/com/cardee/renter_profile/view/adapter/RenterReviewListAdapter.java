package com.cardee.renter_profile.view.adapter;


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
import com.cardee.data_source.remote.api.profile.response.entity.RenterReview;
import com.cardee.util.glide.CircleTransform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RenterReviewListAdapter extends RecyclerView.Adapter<RenterReviewListAdapter.ReviewListViewHolder> {

    private final List<RenterReview> mReviewsList;
    private final LayoutInflater mInflater;
    private final RequestManager mGlideRequestManager;
    private Context mContext;

    public RenterReviewListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideRequestManager = Glide.with(context);
        mReviewsList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public ReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_renter_profile_review, parent, false);
        return new RenterReviewListAdapter.ReviewListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewListViewHolder holder, int position) {
        holder.setImage(mReviewsList.get(position).getAuthor().getProfilePhoto(), mGlideRequestManager, mContext);
        holder.setTextFields(mReviewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    public void insert(List<RenterReview> reviews) {
        for (RenterReview review : reviews) {
            if (!mReviewsList.contains(review)) {
                mReviewsList.add(review);
            }
        }
        notifyDataSetChanged();
    }

    public void destroy() {
        mContext = null;
    }

    public static class ReviewListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mProfileImage;
        private final TextView mProfileName;
        private final TextView mReviewDate;
        private final TextView mRate;
        private final TextView mReviewText;

        public ReviewListViewHolder(View itemView) {
            super(itemView);

            mProfileImage = itemView.findViewById(R.id.review_profile_image);
            mProfileName = itemView.findViewById(R.id.review_profile_name);
            mReviewDate = itemView.findViewById(R.id.review_date);
            mRate = itemView.findViewById(R.id.review_rate_text);
            mReviewText = itemView.findViewById(R.id.review_text);
        }

        public void setImage(String link, RequestManager imageRequestManager, Context context) {
            imageRequestManager.load(link)
                    .transform(new CircleTransform(context))
                    .error(context.getResources().getDrawable(R.drawable.ic_photo_placeholder))
                    .into(mProfileImage);
        }

        public void setTextFields(RenterReview review) {
            mProfileName.setText(review.getAuthor().getName());

            try {
                String rawDate = review.getReviewDate();
                rawDate = rawDate.substring(0, 10);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = df.parse(rawDate);
                df = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
                mReviewDate.setText(df.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            mReviewText.setText(review.getReview());

            float rate = review.getRating();
            if (rate == (long) rate) {
                mRate.setText((String.format(Locale.getDefault(), "%d", (long) rate)));
            } else {
                mRate.setText((String.format(Locale.getDefault(), "%.1f", rate)));
            }
        }
    }
}
