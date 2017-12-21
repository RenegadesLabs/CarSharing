package com.cardee.owner_car_details.view;


import com.cardee.data_source.remote.api.reviews.response.entity.Review;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface CarReviewsContract {

    interface View extends BaseView {

        void setCarReviews(List<Review> reviews);

        void setConditions(int conditions);

        void setComfort(int comfort);

        void setOwnerRate(int owner);

        void setExperience(int overall);

        void setRatingsCount(String s);

        void setReviewsCount(String s);
    }

    interface Presenter extends BasePresenter {

        void get();
    }
}
