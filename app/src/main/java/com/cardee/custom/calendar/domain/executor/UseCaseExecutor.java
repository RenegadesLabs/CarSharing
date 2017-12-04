package com.cardee.custom.calendar.domain.executor;


import android.os.Handler;
import android.os.Looper;

import com.cardee.custom.calendar.domain.UseCase;
import com.cardee.custom.calendar.model.Error;

public class UseCaseExecutor {

    private final Handler handler;
    private final ExecutorPool pool;

    public UseCaseExecutor() {
        this.handler = new Handler(Looper.getMainLooper());
        this.pool = new ExecutorPool();
    }

    public <R extends UseCase.ResponseValues> void execute(final UseCase useCase, final UseCase.RequestValues request,
                                                           final UseCase.Callback<R> callback) {
        pool.put(new Runnable() {
            @Override
            public void run() {
                useCase.execute(request, new CallbackHandler(handler, callback));
            }
        });
    }

    private class CallbackHandler<R extends UseCase.ResponseValues> implements UseCase.Callback<R> {

        private final Handler handler;
        private final UseCase.Callback<R> callback;

        private CallbackHandler(Handler handler, UseCase.Callback<R> callback) {
            this.handler = handler;
            this.callback = callback;
        }

        @Override
        public void onSuccess(final R response) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(response);
                }
            });
        }

        @Override
        public void onError(final Error error) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(error);
                }
            });
        }
    }
}
