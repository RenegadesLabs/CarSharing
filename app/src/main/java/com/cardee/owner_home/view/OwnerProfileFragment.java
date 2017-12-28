package com.cardee.owner_home.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cardee.R;
import com.cardee.account_details.view.AccountDetailsActivity;
import com.cardee.owner_cardee.view.OwnerCardeeActivity;
import com.cardee.owner_home.OwnerProfileContract;
import com.cardee.owner_home.presenter.OwnerMoreTabPresenter;
import com.cardee.owner_home.view.adapter.OwnerMoreTabAdapter;
import com.cardee.owner_home.view.listener.MoreTabItemEventListener;
import com.cardee.owner_profile_info.view.OwnerProfileInfoActivity;
import com.cardee.owner_settings.view.OwnerSettingsActivity;
import com.cardee.renter_home.view.RenterHomeActivity;
import com.cardee.util.glide.CircleTransform;

import static android.app.Activity.RESULT_OK;
import static com.cardee.data_source.remote.service.AccountManager.RENTER_SESSION;

public class OwnerProfileFragment extends Fragment implements OwnerProfileContract.View {

    private MoreTabItemEventListener mEventListener;
    private RecyclerView mMenuListView;
    private OwnerMoreTabAdapter mAdapter;
    private OwnerMoreTabPresenter mPresenter;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private View mHeaderContainer;
    private View mContainer;
    private Toast mCurrentToast;

    public static final int GET_PICTURE_REQUEST = 19;

    public static Fragment newInstance() {
        return new OwnerProfileFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEventListener = (MoreTabItemEventListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mEventListener = (MoreTabItemEventListener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new OwnerMoreTabAdapter(getActivity());
        mPresenter = new OwnerMoreTabPresenter(this);
        mAdapter.subscribe(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_more_tab, container, false);
        mContainer = rootView;
        mHeaderContainer = rootView.findViewById(R.id.profile_header_container);
        mProfileImage = rootView.findViewById(R.id.profile_image);
        mProfileName = rootView.findViewById(R.id.profile_name);

        mMenuListView = rootView.findViewById(R.id.menu_list);
        initMenuList(mMenuListView);

        mPresenter.setOnClickListenerToHeader(mHeaderContainer);

        //show placeholder;
        setProfileImage(null);

        mPresenter.loadProfile();
        return rootView;
    }

    private void initMenuList(RecyclerView listView) {
        listView.setHasFixedSize(true);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listView.getContext(),
                LinearLayoutManager.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void openOwnerProfile() {
        Intent intent = new Intent(getActivity(), OwnerProfileInfoActivity.class);
        intent.putExtra("editable", true);
        startActivityForResult(intent, GET_PICTURE_REQUEST);
    }

    @Override
    public void openAccount() {
        Intent intent = new Intent(getActivity(), AccountDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettings() {
        Intent intent = new Intent(getActivity(), OwnerSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openCreditBalance() {
        showMessage("Coming soon");
    }

    @Override
    public void openLiveChat() {
        showMessage("Coming soon");
    }

    @Override
    public void openInviteFriends() {
        String shareBody = getActivity().getString(R.string.invite_friends_body);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.invite_friends_title)));
    }

    @Override
    public void openCardee() {
        Intent intent = new Intent(getActivity(), OwnerCardeeActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSwitchToRenter() {
        mPresenter.setAccState(RENTER_SESSION);
        Intent intent = new Intent(getActivity(), RenterHomeActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setProfileImage(String profilePhotoLink) {
        if (getActivity() != null) {
            Glide.with(getActivity())
                    .load(profilePhotoLink)
                    .placeholder(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                    .error(getResources().getDrawable(R.drawable.ic_photo_placeholder))
                    .transform(new CircleTransform(getActivity()))
                    .into(mProfileImage);
        }
    }

    @Override
    public void setProfileName(String name) {
        mProfileName.setText(name);
    }

    @Override
    public void setCreditBalance(String creditBalance) {
        mAdapter.setCreditBalance(creditBalance);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                mPresenter.loadProfile();
            }
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mEventListener.onStartLoading();
            mContainer.setVisibility(View.GONE);
        } else {
            mEventListener.onStopLoading();
            mContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }
}
