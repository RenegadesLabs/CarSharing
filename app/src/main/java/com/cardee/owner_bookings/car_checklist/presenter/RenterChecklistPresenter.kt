package com.cardee.owner_bookings.car_checklist.presenter

import com.cardee.R
import com.cardee.data_source.Error
import com.cardee.domain.UseCase
import com.cardee.domain.UseCaseExecutor
import com.cardee.domain.bookings.BookingState
import com.cardee.domain.bookings.entity.Checklist
import com.cardee.domain.bookings.usecase.ChangeBookingState
import com.cardee.domain.bookings.usecase.GetChecklist
import com.cardee.domain.owner.entity.Image
import com.cardee.owner_bookings.car_checklist.adapter.CarSquareImagesAdapter
import com.cardee.owner_bookings.car_checklist.service.PendingChecklistStorage
import com.cardee.owner_bookings.car_checklist.strategy.PresentationStrategy
import com.cardee.owner_bookings.car_checklist.strategy.RenterChecklistStrategy
import com.cardee.owner_bookings.car_checklist.view.ChecklistView
import java.util.*


class RenterChecklistPresenter(val bookingId: Int = -1) : RenterChecklistContract.Presenter {

    private var view: RenterChecklistContract.View? = null
    private var checklistView: ChecklistView? = null
    private var strategy: PresentationStrategy? = null
    private val executor: UseCaseExecutor = UseCaseExecutor.getInstance()
    private var checklist: Checklist? = null
    private var callbacks: View? = null


    override fun setViewRenterCallbacks(veiwCallbacks: View) {
        callbacks = veiwCallbacks
    }

    override fun init() {
        getChecklist()
    }

    override fun onHandover() {

    }

    override fun setView(renterView: RenterChecklistContract.View) {
        view = renterView
        if (renterView is ChecklistView) {
            checklistView = renterView
            strategy = RenterChecklistStrategy(checklistView ?: return, this)
        }
    }

    override fun onAccurateCancel() {
        callbacks?.onCancelled()
    }

    override fun setStrategy(pStrategy: PresentationStrategy) {
        strategy = pStrategy
    }

    override fun onAccurateConfirm() {
        if (view != null) {
            view?.showProgress(true)
        }

        val request = ChangeBookingState.RequestValues(bookingId, BookingState.COLLECTED)
        executor.execute(ChangeBookingState(), request, object : UseCase.Callback<ChangeBookingState.ResponseValues> {
            override fun onSuccess(response: ChangeBookingState.ResponseValues) {
                if (response.isSuccessful) {
                    view?.showProgress(false)
                    PendingChecklistStorage.remove(checklistView?.context, bookingId)
                    callbacks?.onConfirmed()
                }
            }

            override fun onError(error: Error) {
                view?.showProgress(false)
                view?.showMessage(error.message)
            }
        })
    }

    override fun getChecklist() {
        view ?: return
        view?.showProgress(true)

        executor.execute(GetChecklist(), GetChecklist.RequestValues(bookingId),
                object : UseCase.Callback<GetChecklist.ResponseValues> {
                    override fun onSuccess(response: GetChecklist.ResponseValues) {
                        if (view != null && response.isSuccess) {
                            checklist = response.checklist
                            fillData()
                            view?.showProgress(false)
                        }
                    }

                    override fun onError(error: Error) {
                        if (view != null) {
                            view?.showProgress(false)
                            view?.showMessage(R.string.error_occurred)
                        }
                    }
                })
    }

    private fun fillData() {
        checklist?.apply {
            checklistView?.apply {
                if (isByMileage) {
                    val s: String = masterMileage.toString() + " " +
                            context?.getString(R.string.car_rental_rates_per_km) + ". " +
                            context?.getString(R.string.car_rental_fuel_policy_by_mileage) + "."
                    setMileagePetrolDesc(s)
                } else {
                    val s = tankText + " " +
                            context.getString(R.string.owner_handover_tank_filled) +
                            context.getString(R.string.car_rental_fuel_policy_similar_lvl) + "."
                    setMileagePetrolDesc(s)
                }
                setFirstTitle(isByMileage)
                val adapter = CarSquareImagesAdapter(context, false)
                adapter.setItems(Arrays.asList<Image>(*images))
                setImagesAdapter(adapter)

                setRemarksText(remarks)
            }
        }
    }

    override fun onDestroy() {
        view = null
        checklistView = null
    }

    interface View {
        fun onConfirmed()

        fun onCancelled()
    }

}