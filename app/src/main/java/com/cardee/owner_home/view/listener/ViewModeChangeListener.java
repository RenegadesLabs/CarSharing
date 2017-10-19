package com.cardee.owner_home.view.listener;


public interface ViewModeChangeListener {

    enum OptionMenuMode {
        HIDEN, ACTIONS, NO_ACTIONS
    }

    void onViewModeChange(OptionMenuMode mode);
}
