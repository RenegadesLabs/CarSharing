package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cardee.data_source.inbox.local.alert.entity.Alert;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface AlertDao {

    @Query("SELECT * FROM alerts WHERE attachment IS :attachment ORDER BY date_created DESC")
    Flowable<List<Alert>> getAlerts(String attachment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlert(Alert alert);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlerts(List<Alert> alerts);

    @Query("UPDATE alerts SET state = 0 WHERE alert_id IS :alertId AND attachment IS :attachment")
    void updateAlert(int alertId, String attachment);
}
