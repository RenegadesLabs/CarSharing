package com.cardee.owner_car_details.presenter

import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.data_source.OwnerCarRepository
import com.cardee.data_source.OwnerCarsRepository
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.owner.entity.RentalDetails
import com.cardee.domain.owner.usecase.*
import com.cardee.owner_car_details.RentalDetailsContract

class StrategyRentalDetailPresenter(private var view: RentalDetailsContract.ControlView?, private val strategy: Strategy) : RentalDetailsContract.Presenter {
    override fun init() {
    }

    private val executor: UseCaseExecutor = UseCaseExecutor.getInstance()
    private val saveHourlyDates: SaveHourlyTiming = SaveHourlyTiming()
    private val saveDailyRental: SaveDailyRental = SaveDailyRental()
    private var rentalDetails: RentalDetails? = null

    enum class Strategy {
        DAILY, HOURLY
    }

    fun onBind(rentalDetails: RentalDetails) {
        this.rentalDetails = rentalDetails
        view?.setData(rentalDetails)
    }

    fun updateAvailabilityTiming(beginTime: String, endTime: String) {
        if (rentalDetails == null) {
            return
        }
        when (strategy) {
            StrategyRentalDetailPresenter.Strategy.HOURLY -> updateHourlyTiming(beginTime, endTime)
            StrategyRentalDetailPresenter.Strategy.DAILY -> updateDailyTiming(beginTime, endTime)
        }
    }

    fun updateInstantBooking(isInstantBooking: Boolean) {
        if (rentalDetails == null) {
            return
        }
        when (strategy) {
            StrategyRentalDetailPresenter.Strategy.DAILY -> updateDailyInstantBooking(isInstantBooking)
            StrategyRentalDetailPresenter.Strategy.HOURLY -> updateHourlyInstantBooking(isInstantBooking)
        }
    }

    fun updateInstantBookingCount(count: Int) {
        if (rentalDetails == null) {
            return
        }
        when (strategy) {
            StrategyRentalDetailPresenter.Strategy.DAILY -> updateDailyInstantBookingCount(count)
            StrategyRentalDetailPresenter.Strategy.HOURLY -> updateHourlyInstantBookingCount(count)
        }
    }

    fun updateCurbsideDelivery(isCurbsideDelivery: Boolean) {
        if (rentalDetails == null) {
            return
        }
        when (strategy) {
            StrategyRentalDetailPresenter.Strategy.DAILY -> updateDailyCurbsideDelivery(isCurbsideDelivery)
            StrategyRentalDetailPresenter.Strategy.HOURLY -> updateHourlyCurbsideDelivery(isCurbsideDelivery)
        }
    }

    fun updateMinimumBooking(minimumBookingEnabled: Boolean) {
        rentalDetails ?: return
        when (strategy) {
            StrategyRentalDetailPresenter.Strategy.DAILY -> {
                val duration =
                        if (minimumBookingEnabled) {
                            val dur = rentalDetails?.dailyMinRentalDuration
                            if (dur == null || dur < 2) {
                                2
                            } else {
                                dur
                            }
                        } else {
                            0
                        }
                updateDailyMinimumBooking(duration)
            }

            StrategyRentalDetailPresenter.Strategy.HOURLY -> {
                val duration =
                        if (minimumBookingEnabled) {
                            val dur = rentalDetails?.hourlyMinRentalDuration
                            if (dur == null || dur < 2) {
                                2
                            } else {
                                dur
                            }
                        } else {
                            0
                        }
                updateHourlyMinimumBooking(duration)
            }
        }
    }

    fun updateDailyMinimumBooking(minimumDuration: Int) {
        executor.execute(UpdateDailyMinRentDuration(),
                UpdateDailyMinRentDuration.RequestValues(OwnerCarRepository.currentCarId(), minimumDuration),
                object : UseCase.Callback<UpdateDailyMinRentDuration.ResponseValues> {
                    override fun onSuccess(response: UpdateDailyMinRentDuration.ResponseValues?) {
                        view?.showProgress(false)
                        if (response?.isSuccess == true) {
                            OwnerCarRepository.getInstance().refresh(OwnerCarRepository.currentCarId())
                            OwnerCarsRepository.getInstance().refreshCars()
                            view?.showMessage(R.string.saved_successfully)
                            rentalDetails?.dailyMinRentalDuration = minimumDuration
                            view?.setMinBookingValue(minimumDuration)
                        }
                    }

                    override fun onError(error: Error?) {
                        view?.showProgress(false)
                        view?.showMessage(error?.message)
                    }
                })
    }

    fun updateHourlyMinimumBooking(minimumDuration: Int) {
        executor.execute(UpdateHourlyMinRentDuration(),
                UpdateHourlyMinRentDuration.RequestValues(OwnerCarRepository.currentCarId(), minimumDuration),
                object : UseCase.Callback<UpdateHourlyMinRentDuration.ResponseValues> {
                    override fun onSuccess(response: UpdateHourlyMinRentDuration.ResponseValues?) {
                        view?.showProgress(false)
                        if (response?.isSuccess == true) {
                            OwnerCarRepository.getInstance().refresh(OwnerCarRepository.currentCarId())
                            OwnerCarsRepository.getInstance().refreshCars()
                            view?.showMessage(R.string.saved_successfully)
                            rentalDetails?.hourlyMinRentalDuration = minimumDuration
                            view?.setMinBookingValue(minimumDuration)
                        }
                    }

                    override fun onError(error: Error?) {
                        view?.showProgress(false)
                        view?.showMessage(error?.message)
                    }
                })
    }

    fun updateAcceptCash(acceptCash: Boolean) {
        if (rentalDetails == null) {
            return
        }
        when (strategy) {
            StrategyRentalDetailPresenter.Strategy.DAILY -> updateDailyAcceptCash(acceptCash)
            StrategyRentalDetailPresenter.Strategy.HOURLY -> updateHourlyAcceptCash(acceptCash)
        }
    }

    private fun updateDailyTiming(pickupTiming: String, returnTiming: String) {
        val requestBody = RentalDetails.from(rentalDetails!!)
        requestBody.dailyTimePickup = pickupTiming
        requestBody.dailyTimeReturn = returnTiming
        executor.execute<SaveDailyRental.RequestValues, SaveDailyRental.ResponseValues>(saveDailyRental, SaveDailyRental.RequestValues(requestBody),
                object : UseCase.Callback<SaveDailyRental.ResponseValues> {
                    override fun onSuccess(response: SaveDailyRental.ResponseValues) {
                        rentalDetails = requestBody
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateHourlyTiming(beginTiming: String, endTiming: String) {
        executor.execute<SaveHourlyTiming.RequestValues, SaveHourlyTiming.ResponseValues>(saveHourlyDates,
                SaveHourlyTiming.RequestValues(rentalDetails!!.carId, beginTiming, endTiming, rentalDetails!!.hourlyDates),
                object : UseCase.Callback<SaveHourlyTiming.ResponseValues> {
                    override fun onSuccess(response: SaveHourlyTiming.ResponseValues) {
                        rentalDetails!!.hourlyBeginTime = beginTiming
                        rentalDetails!!.hourlyEndTime = endTiming
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateDailyInstantBooking(isInstantBooking: Boolean) {
        executor.execute<UpdateDailyInstantBookingState.RequestValues, UpdateDailyInstantBookingState.ResponseValues>(UpdateDailyInstantBookingState(),
                UpdateDailyInstantBookingState.RequestValues(rentalDetails!!.carId, isInstantBooking),
                object : UseCase.Callback<UpdateDailyInstantBookingState.ResponseValues> {
                    override fun onSuccess(response: UpdateDailyInstantBookingState.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.isDailyInstantBooking = isInstantBooking
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateHourlyInstantBooking(isInstantBooking: Boolean) {
        executor.execute<UpdateHourlyInstantBookingState.RequestValues, UpdateHourlyInstantBookingState.ResponseValues>(UpdateHourlyInstantBookingState(),
                UpdateHourlyInstantBookingState.RequestValues(rentalDetails!!.carId, isInstantBooking),
                object : UseCase.Callback<UpdateHourlyInstantBookingState.ResponseValues> {
                    override fun onSuccess(response: UpdateHourlyInstantBookingState.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.isHourlyInstantBooking = isInstantBooking
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateDailyInstantBookingCount(count: Int) {
        executor.execute<UpdateDailyinstantBookingCount.RequestValues, UpdateDailyinstantBookingCount.ResponseValues>(UpdateDailyinstantBookingCount(),
                UpdateDailyinstantBookingCount.RequestValues(rentalDetails!!.carId, count),
                object : UseCase.Callback<UpdateDailyinstantBookingCount.ResponseValues> {
                    override fun onSuccess(response: UpdateDailyinstantBookingCount.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.dailyInstantBookingCount = count
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateHourlyInstantBookingCount(count: Int) {
        executor.execute<UpdateHourlyInstantBookingCount.RequestValues, UpdateHourlyInstantBookingCount.ResponseValues>(UpdateHourlyInstantBookingCount(),
                UpdateHourlyInstantBookingCount.RequestValues(rentalDetails!!.carId, count),
                object : UseCase.Callback<UpdateHourlyInstantBookingCount.ResponseValues> {
                    override fun onSuccess(response: UpdateHourlyInstantBookingCount.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.hourlyInstantBookingCount = count
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateDailyCurbsideDelivery(isCurbsideDelivery: Boolean) {
        executor.execute<UpdateDailyCurbsideDeliveryState.RequestValues, UpdateDailyCurbsideDeliveryState.ResponseValues>(UpdateDailyCurbsideDeliveryState(),
                UpdateDailyCurbsideDeliveryState.RequestValues(rentalDetails!!.carId, isCurbsideDelivery),
                object : UseCase.Callback<UpdateDailyCurbsideDeliveryState.ResponseValues> {
                    override fun onSuccess(response: UpdateDailyCurbsideDeliveryState.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.isDailyCurbsideDelivery = isCurbsideDelivery
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateHourlyCurbsideDelivery(isCurbsideDelivery: Boolean) {
        executor.execute<UpdateHourlyCurbsideDeliveryState.RequestValues, UpdateHourlyCurbsideDeliveryState.ResponseValues>(UpdateHourlyCurbsideDeliveryState(),
                UpdateHourlyCurbsideDeliveryState.RequestValues(rentalDetails!!.carId, isCurbsideDelivery),
                object : UseCase.Callback<UpdateHourlyCurbsideDeliveryState.ResponseValues> {
                    override fun onSuccess(response: UpdateHourlyCurbsideDeliveryState.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.isHourlyCurbsideDelivery = isCurbsideDelivery
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateDailyAcceptCash(acceptCash: Boolean) {
        executor.execute<UpdateDailyAcceptCashState.RequestValues, UpdateDailyAcceptCashState.ResponseValues>(UpdateDailyAcceptCashState(),
                UpdateDailyAcceptCashState.RequestValues(rentalDetails!!.carId, acceptCash),
                object : UseCase.Callback<UpdateDailyAcceptCashState.ResponseValues> {
                    override fun onSuccess(response: UpdateDailyAcceptCashState.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.isDailyAcceptCash = acceptCash
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    private fun updateHourlyAcceptCash(acceptCash: Boolean) {
        executor.execute<UpdateHourlyAcceptCashState.RequestValues, UpdateHourlyAcceptCashState.ResponseValues>(UpdateHourlyAcceptCashState(),
                UpdateHourlyAcceptCashState.RequestValues(rentalDetails!!.carId, acceptCash),
                object : UseCase.Callback<UpdateHourlyAcceptCashState.ResponseValues> {
                    override fun onSuccess(response: UpdateHourlyAcceptCashState.ResponseValues) {
                        OwnerCarRepository.getInstance().refresh(rentalDetails!!.carId)
                        OwnerCarsRepository.getInstance().refreshCars()
                        rentalDetails!!.isHourlyAcceptCash = acceptCash
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view!!.setData(rentalDetails)
                        }
                    }
                })
    }

    override fun onDestroy() {
        view = null
    }
}
