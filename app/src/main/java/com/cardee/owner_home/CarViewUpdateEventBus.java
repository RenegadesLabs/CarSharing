package com.cardee.owner_home;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CarViewUpdateEventBus {

    private static CarViewUpdateEventBus INSTANCE;

    private final Observable<OwnerCarListContract.CarEvent> mObservable;
    private ObservableEmitter<OwnerCarListContract.CarEvent> mEmitter;
    private Consumer<OwnerCarListContract.CarEvent> mConsumer;
    private Disposable mDisposable;

    private CarViewUpdateEventBus() {
        mObservable = Observable.create(new ObservableOnSubscribe<OwnerCarListContract.CarEvent>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<OwnerCarListContract.CarEvent> emitter) throws Exception {
                mEmitter = emitter;
            }
        });
    }

    public static CarViewUpdateEventBus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarViewUpdateEventBus();
        }
        return INSTANCE;
    }

    public void push(OwnerCarListContract.CarEvent event) {
        if (mEmitter != null && !mEmitter.isDisposed()) {
            mEmitter.onNext(event);
        }
    }

    public void subscribe(Consumer<OwnerCarListContract.CarEvent> consumer) {
        if (mConsumer != null) {
            return;
        }
        mConsumer = consumer;
        mDisposable = mObservable.subscribe(consumer);
    }

    public void unsubscribe(Consumer<OwnerCarListContract.CarEvent> consumer) {
        if (mConsumer != null && mConsumer.equals(consumer)) {
            release();
        }
    }

    public void release() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
        mConsumer = null;
        mEmitter = null;
    }

    public boolean hasSubscriber() {
        return mConsumer != null;
    }
}
