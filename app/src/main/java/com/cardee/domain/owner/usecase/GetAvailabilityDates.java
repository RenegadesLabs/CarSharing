package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.domain.UseCase;
import com.cardee.domain.util.ArrayUtil;
import com.cardee.domain.util.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetAvailabilityDates
        implements UseCase<GetAvailabilityDates.RequestValues, GetAvailabilityDates.ResponseValues> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final SimpleDateFormat formatter;
    private final OwnerCarRepository repository;

    public GetAvailabilityDates() {
        formatter = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        repository = OwnerCarRepository.getInstance();
    }

    @Override
    public void execute(RequestValues request, final Callback<ResponseValues> callback) {
        int id = request.getId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        repository.obtainCar(id, new OwnerCarDataSource.Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                String[] dailyDates = carResponse.getCarAvailabilityDailyDates();
                String[] hourlyDates = carResponse.getCarAvailabilityHourlyDates();
                String beginTime = carResponse.getCarAvailabilityTimeBegin();
                String endTime = carResponse.getCarAvailabilityTimeEnd();
                ResponseValues response = new ResponseValues(ArrayUtil.asList(dailyDates, new Mapper<String, Date>() {
                    @Override
                    public Date map(String input) {
                        try {
                            return formatter.parse(input);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }), ArrayUtil.asList(hourlyDates, new Mapper<String, Date>() {
                    @Override
                    public Date map(String input) {
                        try {
                            return formatter.parse(input);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }), beginTime, endTime);
                callback.onSuccess(response);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final int id;

        public RequestValues(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Date> dailyDates;
        private final List<Date> hourlyDates;
        private final String beginTime;
        private final String endTime;

        public ResponseValues(List<Date> dailyDates,
                              List<Date> hourlyDates,
                              String beginTime,
                              String endTime) {
            this.dailyDates = dailyDates;
            this.hourlyDates = hourlyDates;
            this.beginTime = beginTime;
            this.endTime = endTime;
        }

        public List<Date> getDailyDates() {
            return dailyDates;
        }

        public List<Date> getHourlyDates() {
            return hourlyDates;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public String getEndTime() {
            return endTime;
        }
    }
}
