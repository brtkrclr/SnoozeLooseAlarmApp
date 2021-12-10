package com.example.snoozelose.Alarms;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Alarms.class,version = 1,exportSchema = false)
public abstract class AlarmsDatabase extends RoomDatabase {

    private static AlarmsDatabase instance;

    public abstract AlarmDao alarmDao();

    public static synchronized  AlarmsDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    AlarmsDatabase.class,"alarm_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return  instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private AlarmDao alarmDao;
        private PopulateDbAsyncTask(AlarmsDatabase db){
            alarmDao=db.alarmDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            alarmDao.insert(new Alarms("10:21","Lecture",10,true,true,false,false,false,true,true,false,true,false));
            alarmDao.insert(new Alarms("14:52","Pick up Engin",5,true,true,true,true,true,true,true,true,true,true));
            alarmDao.insert(new Alarms("20:38","Dinner",15,true,true,false,true,false,false,true,false,false,false));
            alarmDao.insert(new Alarms("07:31","Wake Up",50,false,false,false,false,false,false,false,false,false,false));

            return null;
        }
    }
}
