package com.cardee.data_source.inbox.remote.chat;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.remote.api.model.entity.NewMessage;
import com.cardee.data_source.remote.api.NoDataResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RemoteData {

    interface ChatListSource extends RemoteData {

        Single<List<Chat>> getRemoteChats(String attachment);

        Single<Chat> getSingleChat(int serverId, String attachment);
    }

    interface ChatSingleSource extends RemoteData {

        Single<List<ChatMessage>> getMessages(int chatId);

        Single<NewMessage> sendMessage(String newMessage, int chatId);

        Completable markAsRead(int messageId);
    }

    interface AlertListSource extends RemoteData {

        Single<List<Alert>> getRemoteAlerts(String attachment);

        Single<NoDataResponse> markAlertsAsRead(List<Integer> alerts);
    }

    interface NotificationType {

        String NEW = "NEW";

        String NEW_REQUEST = "NEW_REQUEST";
        String HANDOVER_REMINDER = "HANDOVER_REMINDER";
        String RETURN_REMINDER = "RETURN_REMINDER";
        String RETURN_OVERDUE = "RETURN_OVERDUE";
        String REQUEST_EXPIRED = "REQUEST_EXPIRED";
        String SYSTEM_MESSAGES = "SYSTEM_MESSAGES";
        String ACCEPTED = "ACCEPTED";
        String CAR_VERIFICATION = "CAR_VERIFICATION";
        String USER_VERIFICATION = "USER_VERIFICATION";
        String RENTER_STATE_CHANGE = "RENTER_STATE_CHANGE";
        String OWNER_STATE_CHANGE = "OWNER_STATE_CHANGE";
        String CAR_STATE_CHANGE = "CAR_STATE_CHANGE";
        String BOOKING_EXT = "BOOKING_EXT";
        String BOOKING_CANCELLATION = "BOOKING_CANCELLATION";
        String RENTER_REVIEW_REMINDER = "RENTER_REVIEW_REMINDER";
        String OWNER_CHECKLIST_UPD = "OWNER_CHECKLIST_UPD";
        String RENTER_CHECKLIST_UPD = "RENTER_CHECKLIST_UPD";
        String BROADCAST = "BROADCAST";
        String INIT_CHECKLIST = "INIT_CHECKLIST";
        String OWNER_REVIEW_REMINDER = " OWNER_REVIEW_REMINDER";
        String RENTER_REVIEW = "RENTER_REVIEW";
        String OWNER_REVIEW = "OWNER_REVIEW";
        String EXTENSION_REQUEST = "EXTENSION_REQUEST";
        String EXTENSION_CANCELED = "EXTENSION_CANCELED";
        String EXTENSION_APPROVED = "EXTENSION_APPROVED";
    }
}
