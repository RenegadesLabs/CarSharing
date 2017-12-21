package com.cardee.owner_car_rental_info.terms.presenter;


import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.RentalTerms;
import com.cardee.domain.owner.entity.mapper.CarDetailsToRentalTermsMapper;
import com.cardee.domain.owner.usecase.GetRentalDetails;

public class RentalTermsPresenter {
    private UseCaseExecutor mExecutor;

    public interface View {
        void onRentalTermsRetrieved(RentalTerms terms);
    }
    private View mView;

    public RentalTermsPresenter(View view) {
        mExecutor = UseCaseExecutor.getInstance();
        mView = view;
    }

    public void getRentalDetails(int carId) {
        if (mView == null) {
            return;
        }
        mExecutor.execute(new GetRentalDetails(), new GetRentalDetails.RequestValues(carId),
                new UseCase.Callback<GetRentalDetails.ResponseValues>() {
                    @Override
                    public void onSuccess(GetRentalDetails.ResponseValues response) {
                        mView.onRentalTermsRetrieved(CarDetailsToRentalTermsMapper
                                .transform(response.getRentalDetails().getCarDetails()));
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });
    }
}
