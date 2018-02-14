package com.cardee.domain.balance

import com.cardee.data_source.Error
import com.cardee.data_source.OwnerProfileDataSource
import com.cardee.data_source.remote.RemoteOwnerProfileDataSource
import com.cardee.data_source.remote.api.profile.response.entity.OwnerProfile
import com.cardee.domain.rx.Request
import com.cardee.domain.rx.Response
import com.cardee.domain.rx.ThreadExecutor
import com.cardee.domain.rx.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class FetchCreditBalance(private val repository: OwnerProfileDataSource = RemoteOwnerProfileDataSource.getInstance()) :
        UseCase<Double>(ThreadExecutor.getInstance()!!, AndroidSchedulers.mainThread()) {


    override fun buildUseCaseObserver(request: Request): Observable<Response<Double>> {
        return Observable.create<Response<Double>> { emitter ->
            repository.loadOwnerProfile(object : OwnerProfileDataSource.ProfileCallback {
                override fun onSuccess(ownerProfile: OwnerProfile?) {
                    ownerProfile?.let { profile ->
                        emitter.onNext(Response(profile.creditBalance ?: 0.0, null, null))
                        return
                    }
                    emitter.onNext(Response(null, Response.SERVER_ERROR, "Server error"))
                }

                override fun onError(error: Error?) {
                    emitter.onNext(Response<Double>(null, Response.SOCKET_ERROR, error?.message))
                }
            })
        }
    }
}