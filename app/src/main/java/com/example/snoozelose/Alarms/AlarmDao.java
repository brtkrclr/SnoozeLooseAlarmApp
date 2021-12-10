package com.example.snoozelose.Alarms;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert
    long insert(Alarms alarm);

    @Update
    void update(Alarms alarm);

    @Delete
    void delete(Alarms alarm);

    @Query("DELETE FROM alarms_list")
    void deleteAllAlarms();

    @Query("SELECT * FROM alarms_list ORDER BY time ASC")
    LiveData<List<Alarms>> getAllAlarms();

    @Query("SELECT * FROM alarms_list WHERE alarmID=:alarmId LIMIT 1")
    LiveData<Alarms> getAlarm(int alarmId);

}
