package com.cardee.owner_home.view.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardee.BuildConfig;
import com.cardee.R;
import com.cardee.owner_home.OwnerProfileContract;

import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MoreTabAdapter extends RecyclerView.Adapter<MoreTabAdapter.MoreTabItemViewHolder> {

    private final List<String> mMenuItems;
    private final LayoutInflater mInflater;
    private final String mCardee;
    private final String mVersionString;

    private final PublishSubject<OwnerProfileContract.Action> mEventObservable;

    public MoreTabAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuItems = Arrays.asList(context.getResources().getStringArray(R.array.more_tab_menu));
        mEventObservable = PublishSubject.create();
        mCardee = context.getResources().getString(R.string.more_tab_cardee);
        mVersionString = context.getResources().getString(R.string.more_tab_version);

    }

    @Override
    public MoreTabItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_owner_more_tab, parent, false);
        return new MoreTabItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoreTabItemViewHolder holder, int position) {
        String text = mMenuItems.get(position);
        holder.setText(text);
        holder.setOnClickListener(mEventObservable, position);

        if (text.equals(mCardee)) {
            String versionCode = BuildConfig.VERSION_NAME;
            String version = mVersionString + " " + versionCode;
            holder.setItemInfo(version);
        }

        // TODO set icon and info;
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    public static class MoreTabItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mIcon;
        private final TextView mText;
        private final TextView mItemInfo;
        private final View rootView;

        public MoreTabItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mIcon = itemView.findViewById(R.id.item_icon);
            mText = itemView.findViewById(R.id.item_text);
            mItemInfo = itemView.findViewById(R.id.item_info);
        }

        private void setIcon(Drawable icon) {
            mIcon.setImageDrawable(icon);
        }

        private void setText(String text) {
            mText.setText(text);
        }

        private void setItemInfo(String info) {
            mItemInfo.setText(info);
        }

        private void setOnClickListener(final PublishSubject<OwnerProfileContract.Action> observable, final int position) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position) {
                        case 0:
                            observable.onNext(OwnerProfileContract.Action.ACCOUNT_CLICKED);
                            break;
                        case 1:
                            observable.onNext(OwnerProfileContract.Action.SETTINGS_CLICKED);
                            break;
                        case 2:
                            observable.onNext(OwnerProfileContract.Action.CREDIT_CLICKED);
                            break;
                        case 3:
                            observable.onNext(OwnerProfileContract.Action.CHAT_CLICKED);
                            break;
                        case 4:
                            observable.onNext(OwnerProfileContract.Action.INVITE_CLICKED);
                            break;
                        case 5:
                            observable.onNext(OwnerProfileContract.Action.CARDEE_CLICKED);
                            break;
                        case 6:
                            observable.onNext(OwnerProfileContract.Action.SWITCH_CLICKED);
                            break;
                    }
                }
            });
        }
    }

    public void subscribe(Consumer<OwnerProfileContract.Action> consumer) {
        mEventObservable.subscribe(consumer);
    }
}
