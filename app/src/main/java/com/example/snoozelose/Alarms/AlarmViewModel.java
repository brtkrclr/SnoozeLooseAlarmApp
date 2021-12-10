package com.example.snoozelose.Alarms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private AlarmRepository repository;
    private LiveData<List<Alarms>> allAlarms;
    private MutableLiveData<Boolean> show = new MutableLiveData<Boolean>(false);

    public AlarmViewModel(@NonNull Application application) {
        super(application);
        repository=new AlarmRepository(application);
        allAlarms=repository.getAllAlarms();
    }

    public void insert(Alarms alarms, AlarmRepository.InsertAlarmResponse response){
        repository.insert(alarms, response);
    }
    public void update(Alarms alarms){
        repository.update(alarms);
    }
    public void delete(Alarms alarms){
        repository.delete(alarms);
    }
    public void deleteAllAlarms(){
        repository.deleteAllAlarms();
    }

    public LiveData<List<Alarms>> getAllAlarms() {
        return allAlarms;
    }

}
