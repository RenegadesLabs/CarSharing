package com.cardee.owner_car_add;


import com.cardee.owner_car_add.NewCarFormsContract;

public interface CarAddContract {

    interface View extends NewCarFormsContract.View {

        void setTypeCompleted(boolean completed);

        void setInfoCompleted(boolean completed);

        void setImageCompleted(boolean completed);

        void setLocationCompleted(boolean completed);

        void setContactsCompleted(boolean completed);

        void setPaymentCompleted(boolean completed);
    }
}
