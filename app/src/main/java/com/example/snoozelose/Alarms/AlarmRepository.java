package com.example.snoozelose.Alarms;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {

    private AlarmDao alarmDao;
    private LiveData<List<Alarms>> allAlarms;


    public AlarmRepository(Application  application){
        AlarmsDatabase database=AlarmsDatabase.getInstance(application);
        alarmDao =database.alarmDao();
        allAlarms=alarmDao.getAllAlarms();
    }

    public void insert(Alarms alarms, InsertAlarmResponse response) {
        new InsertAlarmsAsyncTask(alarmDao, response).execute(alarms);
    }

    public void update(Alarms alarms){
        new UpdateAlarmsAsyncTask(alarmDao).execute(alarms);
    }

    public void delete(Alarms alarms){

        new DeleteAlarmsAsyncTask(alarmDao).execute(alarms);
    }

    public void deleteAllAlarms(){
        new DeleteAllAlarmsAsyncTask(alarmDao).execute();
    }

    public LiveData<List<Alarms>> getAllAlarms() {
        return allAlarms;
    }

    public LiveData<Alarms> getAlarm(int id) {
        return alarmDao.getAlarm(id);
    }

    public static interface InsertAlarmResponse {

        public void alarmInserted(int id);

    }

    private static class InsertAlarmsAsyncTask extends AsyncTask<Alarms,Void,Void>{
        private AlarmDao alarmDao;
        private InsertAlarmResponse response;

        private InsertAlarmsAsyncTask(AlarmDao alarmDao, InsertAlarmResponse response){
            this.alarmDao = alarmDao;
            this.response = response;
        }
        @Override
        protected Void doInBackground(Alarms... alarms) {
            long id = alarmDao.insert(alarms[0]);
            response.alarmInserted((int) id);
            return null;
        }
    }
    private static class UpdateAlarmsAsyncTask extends AsyncTask<Alarms,Void,Void>{
        private AlarmDao alarmDao;

        private UpdateAlarmsAsyncTask(AlarmDao alarmDao){
            this.alarmDao =alarmDao;

        }
        @Override
        protected Void doInBackground(Alarms... alarms) {
            alarmDao.update(alarms[0]);
            return null;
        }
    }
    private static class DeleteAlarmsAsyncTask extends AsyncTask<Alarms,Void,Void>{
        private AlarmDao alarmDao;

        private DeleteAlarmsAsyncTask(AlarmDao alarmDao){
            this.alarmDao =alarmDao;

        }
        @Override
        protected Void doInBackground(Alarms... alarms) {
            alarmDao.delete(alarms[0]);
            return null;
        }
    }
    private static class DeleteAllAlarmsAsyncTask extends AsyncTask<Void,Void,Void>{
        private AlarmDao alarmDao;

        private DeleteAllAlarmsAsyncTask(AlarmDao alarmDao){
            this.alarmDao =alarmDao;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            alarmDao.deleteAllAlarms();
            return null;
        }
    }
}
