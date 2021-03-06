package com.cardee.domain;


import com.cardee.domain.concurency.UIThreadCallback;
import com.cardee.domain.concurency.UseCaseResponseHandler;
import com.cardee.domain.concurency.UseCaseThreadPool;

public class UseCaseExecutor {

    private static UseCaseExecutor INSTANCE;

    private final UseCaseThreadPool mThreadPool;
    private final UseCaseResponseHandler mResponseHandler;

    private UseCaseExecutor(UseCaseThreadPool threadPool, UseCaseResponseHandler responseHandler) {
        mThreadPool = threadPool;
        mResponseHandler = responseHandler;
    }

    public <V extends UseCase.RequestValues, R extends UseCase.ResponseValues> void execute(
            final UseCase<V, R> useCase, final V values, UseCase.Callback<R> callback) {
        final UIThreadCallback<R> uiCallback = new UIThreadCallback<>(mResponseHandler, callback);
        mThreadPool.execute(() -> useCase.execute(values, uiCallback));
    }

    public static UseCaseExecutor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseExecutor(new UseCaseThreadPool(),
                    new UseCaseResponseHandler());
        }
        return INSTANCE;
    }
}
