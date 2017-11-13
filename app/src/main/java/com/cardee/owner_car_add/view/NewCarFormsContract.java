package com.cardee.owner_car_add.view;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.mvp.BasePresenter;
import com.cardee.mvp.BaseView;

import java.io.File;

public interface NewCarFormsContract {

    String VIEW_MODE = "_view_mode";
    String ACTION = "_child_action";
    String CAR_ID = "_car_id";

    enum Transmission {
        AUTO(1, "Automatic"), MANUAL(2, "Manual");

        private final int id;
        private final String name;

        Transmission(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static Integer getIdByName(String name) {
            Transmission[] values = Transmission.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name.equals(name)) {
                    return values[i].id;
                }
            }
            return null;
        }

        public static String getNameById(int id) {
            Transmission[] values = Transmission.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].id == id) {
                    return values[i].name;
                }
            }
            return null;
        }
    }

    enum BodyType {
        SEDAN(1, "Sedan", 0),
        LIFTBACK(2, "Liftback", 1),
        SUV(3, "SUV", 2),
        HATCHBACK(4, "Hatchback", 3),
        WAGON(5, "Wagon", 4),
        COUPE(6, "Coupe", 5),
        CONVERTIBLE(7, "Convertible", 6),
        MINIVAN(8, "Minivan", 7),
        PICKUP(9, "Pickup", 8),
        VAN(10, "Van", 9),
        LIMOUSIN(11, "Limousin", 10);

        private final int id;
        private final String name;
        private final int order;

        BodyType(int id, String name, int order) {
            this.id = id;
            this.name = name;
            this.order = order;
        }

        public static int getPositionByName(String name) {
            BodyType[] values = BodyType.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name.equals(name)) {
                    return i;
                }
            }
            return -1;
        }

        public static Integer getIdByName(String name) {
            BodyType[] values = BodyType.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].name.equals(name)) {
                    return values[i].id;
                }
            }
            return null;
        }

        public static String getNameById(int id) {
            BodyType[] values = BodyType.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].id == id) {
                    return values[i].name;
                }
            }
            return null;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }
    }

    enum Action {
        SAVE, UPDATE, FINISH
    }

    enum Mode {
        TYPE(R.string.car_add_vehicle_title),
        INFO(R.string.car_add_info_car_title),
        IMAGE(R.string.car_add_image_title),
        LOCATION(R.string.car_add_location_title),
        CONTACT(R.string.car_add_contact_title),
        PAYMENT(R.string.car_add_payment_title);

        @StringRes
        private int titleId;

        Mode(int titleId) {
            this.titleId = titleId;
        }

        public int getTitleId() {
            return titleId;
        }
    }

    interface View extends BaseView {

        void setCarData(CarData carData);

        void onFinish();

    }

    interface Presenter extends BasePresenter {

        void onCarDataResponse(CarData carData);
    }
}
