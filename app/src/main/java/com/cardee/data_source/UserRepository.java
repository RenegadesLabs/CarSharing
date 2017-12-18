package com.cardee.data_source;

import com.cardee.CardeeApp;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.auth.Authentication;
import com.cardee.data_source.remote.api.auth.request.CheckUniqueLoginRequest;
import com.cardee.data_source.remote.api.auth.request.ForgotPassRequest;
import com.cardee.data_source.remote.api.auth.request.LoginRequest;
import com.cardee.data_source.remote.api.auth.request.PushRequest;
import com.cardee.data_source.remote.api.auth.request.SignUpRequest;
import com.cardee.data_source.remote.api.auth.request.SocialLoginRequest;
import com.cardee.data_source.remote.api.auth.request.VerifyPasswordRequest;
import com.cardee.data_source.remote.api.auth.response.BaseAuthResponse;
import com.cardee.data_source.remote.api.auth.response.SocialAuthResponse;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.owner.usecase.Register;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE;
    private Authentication api;

    private UserRepository() {
        api = CardeeApp.retrofit.create(Authentication.class);
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    @Override
    public void login(String login, String password, final UserDataSource.Callback callback) {
        LoginRequest req = new LoginRequest();
        req.setLogin(login);
        req.setPassword(password);
        Observable<BaseAuthResponse> ob = api.login(req);
        ob.subscribeWith(new DisposableObserver<BaseAuthResponse>() {
            @Override
            public void onNext(BaseAuthResponse baseAuthResponse) {
                if (baseAuthResponse.getSuccess()) {
                    pushFcmOb();
                    AccountManager.getInstance(CardeeApp.context).saveToken(baseAuthResponse.getBody().getToken());
                }
                callback.onSuccess(baseAuthResponse.getSuccess());
            }

            @Override
            public void onError(Throwable e) {
                if (Error.Message.WRONG_CREDENTIALS.equals(e.getMessage())) {
                    callback.onError(new Error(Error.Type.WRONG_CREDENTIALS, e.getMessage()));
                    return;
                }
                callback.onError(new Error(Error.Type.AUTHORIZATION, e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void loginSocial(String provider, String token, final Callback callback) {
        SocialLoginRequest req = new SocialLoginRequest();
        req.setProvider(provider);
        req.setToken(token);
        Observable<SocialAuthResponse> ob = api.loginSocial(req);
        ob.subscribeWith(new DisposableObserver<SocialAuthResponse>() {
            @Override
            public void onNext(SocialAuthResponse baseAuthResponse) {
                if (baseAuthResponse.getSuccess()) {
                    pushFcmOb();
                    AccountManager.getInstance(CardeeApp.context).saveToken(baseAuthResponse.getBody().getToken());
                }
                callback.onSuccess(baseAuthResponse.getSuccess());
            }

            @Override
            public void onError(Throwable e) {
                if (Error.Message.WRONG_CREDENTIALS.equals(e.getMessage())) {
                    callback.onError(new Error(Error.Type.WRONG_CREDENTIALS, e.getMessage()));
                    return;
                }
                callback.onError(new Error(Error.Type.AUTHORIZATION, e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });

        pushFcmOb();
    }

    public void pushFcmOb() {
        if (AccountManager.getInstance(CardeeApp.context).isFcmTokenAuthenticated()){
            return;
        }
        PushRequest pushRequest = new PushRequest();
        pushRequest.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
        api.pushToken(pushRequest).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                AccountManager.getInstance(CardeeApp.context).saveFcmAuthAction();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void sendEmailToChangePassword(String email, final Callback callback) {
        ForgotPassRequest req = new ForgotPassRequest();
        req.setEmail(email);
        Observable<BaseAuthResponse> ob = api.forgotPassword(req);
        ob.subscribeWith(new DisposableObserver<BaseAuthResponse>() {
            @Override
            public void onNext(BaseAuthResponse baseAuthResponse) {
                callback.onSuccess(baseAuthResponse.getSuccess());
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(new Error(Error.Type.AUTHORIZATION, e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });

        pushFcmOb();
    }

    @Override
    public void changePassword(String key, String pass, String passConfirm, final Callback callback) {
        VerifyPasswordRequest req = new VerifyPasswordRequest();
        req.setPassword(pass);
        req.setPasswordConfirm(passConfirm);
        Observable<BaseAuthResponse> ob = api.verifyPassword(req, key);
        ob.subscribeWith(new DisposableObserver<BaseAuthResponse>() {
            @Override
            public void onNext(BaseAuthResponse baseAuthResponse) {
                if (baseAuthResponse.getSuccess()) {
                    AccountManager.getInstance(CardeeApp.context).saveToken(baseAuthResponse.getBody().getToken());
                }
                callback.onSuccess(baseAuthResponse.getSuccess());
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(new Error(Error.Type.AUTHORIZATION, e.getMessage()));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void checkUniqueLogin(String login, String password, final Callback callback) {
        CheckUniqueLoginRequest req = new CheckUniqueLoginRequest();
        req.setLogin(login);
        req.setPassword(password);
        api.checkUniqueLogin(req).enqueue(new retrofit2.Callback<BaseAuthResponse>() {
            @Override
            public void onResponse(Call<BaseAuthResponse> call, Response<BaseAuthResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.isSuccessful());
                    return;
                }

                try {
                    if (response.errorBody() != null) {
                        handleErrors(response.code(), new JSONObject(response.errorBody().string()), callback);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<BaseAuthResponse> call, Throwable t) {
                callback.onError(new Error(Error.Type.OTHER, t.getMessage()));
            }
        });
//        Observable<BaseAuthResponse> ob = api.checkUniqueLogin(req);
//        ob.subscribeWith(new DisposableObserver<BaseAuthResponse>() {
//            @Override
//            public void onNext(BaseAuthResponse baseAuthResponse) {
//                callback.onSuccess(baseAuthResponse.getSuccess());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                RetrofitException error = (RetrofitException) e;
//                callback.onError(new Error(Error.Type.AUTHORIZATION, error.getMessage()));
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    @Override
    public void register(final Register.RequestValues registerValues, final Callback callback) {
        SignUpRequest req = new SignUpRequest();
        req.setLogin(registerValues.getLogin());
        req.setPassword(registerValues.getPassword());
        req.setPasswordConfirm(registerValues.getPassword());
        req.setName(registerValues.getUserName());
        api.signUp(req).enqueue(new retrofit2.Callback<BaseAuthResponse>() {
            @Override
            public void onResponse(Call<BaseAuthResponse> call, Response<BaseAuthResponse> response) {
                if (response.isSuccessful()) {
                    pushFcmOb();
                    String token = response.body().getBody().getToken();
                    AccountManager.getInstance(CardeeApp.context).saveToken(token);
                    if (registerValues.getImage() != null) {
                        setProfilePicture(registerValues, callback);
                    } else {
                        callback.onSuccess(response.isSuccessful());
                    }
                } else {
                    try {
                        handleErrors(response.code(), new JSONObject(response.errorBody().string()), callback);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseAuthResponse> call, Throwable t) {
                callback.onError(new Error(Error.Type.OTHER, t.getMessage()));
            }
        });

    }

    private void handleErrors(int code, JSONObject errorBodyObj, Callback callback) {
        switch (code) {
            case BaseResponse.ERROR_CODE_VALIDATION:

                String loginError = "";
                try {
                    JSONArray loginErrorObj = errorBodyObj.getJSONObject("data")
                            .getJSONObject("errors")
                            .getJSONArray("login");
                    loginError = (Error.Message.LOGIN_DO_EXIST.equals(loginErrorObj.getString(0))
                            ? Error.Message.LOGIN_DO_EXIST : Error.Message.LOGIN_NOT_VALID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String passError = "";
                try {
                    JSONArray passErrorObj = errorBodyObj.getJSONObject("data")
                            .getJSONObject("errors")
                            .getJSONArray("password");
                    passError = (Error.Message.PASSWORD_LENGTH.equals(passErrorObj.getString(0))
                            ? Error.Message.PASSWORD_LENGTH : Error.Message.PASSWORD_DO_EXIST);

                    passError = !loginError.equals("") ? "\n" + passError : passError;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.onError(new Error(Error.Type.AUTHORIZATION, loginError + passError));
                break;
            case BaseResponse.ERROR_CODE_UNAUTHORIZED:
                String authError = "";
                try {
                    authError = errorBodyObj.getJSONObject("data")
                            .getJSONObject("errors")
                            .getJSONArray("authentication")
                            .getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onError(new Error(Error.Type.AUTHORIZATION,
                        !authError.equals("") ? authError : "Authentication problem."));
                break;

            case BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR:
                callback.onError(new Error(Error.Type.SERVER, "Internal server error."));
                break;
            default:
                callback.onError(new Error(Error.Type.OTHER, "Something went wrong."));
        }
    }

    @Override
    public void setProfilePicture(Register.RequestValues registerValues, final Callback callback) {
        File picture = registerValues.getImage();
        MultipartBody.Part picPart = MultipartBody.Part.createFormData("picture", picture.getName(), RequestBody.
                create(MediaType.parse("application/octet-stream"), picture));
        api.setProfilePicture(picPart).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.isSuccessful());
                    return;
                }
                try {
                    handleErrors(response.code(), new JSONObject(response.errorBody().string()), callback);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(new Error(Error.Type.OTHER, t.getMessage()));
            }
        });
    }
}
