package com.cardee.owner_car_details.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.cardee.R;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.owner_car_details.view.OwnerCarRentalFragment;
import com.cardee.owner_car_rental_info.fuel.RentalFuelPolicyActivity;
import com.cardee.owner_car_rental_info.terms.view.RentalTermsActivity;


public class DailyRentalViewHolder extends BaseViewHolder<RentalDetails>
        implements View.OnClickListener {


    public DailyRentalViewHolder(@NonNull View rootView, @NonNull Activity activity) {
        super(rootView, activity);

    }

    @Override
    public void bind(RentalDetails model) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_rentalTermsContainer:
                getActivity().startActivity(new Intent(getActivity(),
                        RentalTermsActivity.class));
                break;
            case R.id.tv_rentalFuelEdit:
                Intent i = new Intent(getActivity(),
                        RentalFuelPolicyActivity.class);
                i.putExtra(OwnerCarRentalFragment.MODE, OwnerCarRentalFragment.DAILY);
                getActivity().startActivity(i);
                break;
        }
    }
}
