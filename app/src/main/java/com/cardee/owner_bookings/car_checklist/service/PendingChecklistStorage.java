package com.cardee.owner_bookings.car_checklist.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PendingChecklistStorage {

    private static final String PENDING_CHECKLIST_STORAGE = "pending_checklist_storage";
    private static final String CHECKLISTS = "checklist_set";

    public static void addChecklist(Context context, int bookingId) {
        SharedPreferences pref = context.getSharedPreferences(PENDING_CHECKLIST_STORAGE, Context.MODE_PRIVATE);
        Set<String> defaultSet = new HashSet<>();
        Set<String> checklistSet = pref.getStringSet(CHECKLISTS, defaultSet);
        if (!checklistSet.isEmpty()) {
            defaultSet.addAll(checklistSet);
        }
        defaultSet.add(String.valueOf(bookingId));
        pref.edit().putStringSet(CHECKLISTS, defaultSet).apply();
    }

    public static boolean containsChecklist(Context context, int bookingId) {
        SharedPreferences pref = context.getSharedPreferences(PENDING_CHECKLIST_STORAGE, Context.MODE_PRIVATE);
        boolean b = pref.getStringSet(CHECKLISTS, new HashSet<>()).contains(String.valueOf(bookingId));
        return b;
    }

    public static void remove(Context context, int bookingId) {
        SharedPreferences pref = context.getSharedPreferences(PENDING_CHECKLIST_STORAGE, Context.MODE_PRIVATE);
        Set<String> checklists = pref.getStringSet(CHECKLISTS, null);
        if (checklists != null) {
            checklists.remove(String.valueOf(bookingId));
            pref.edit().putStringSet(CHECKLISTS, checklists).apply();
        }
    }
}
