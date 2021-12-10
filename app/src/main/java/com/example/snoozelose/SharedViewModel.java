package com.example.snoozelose;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> sharedDebt = new MutableLiveData<>();



    public int getSharedDebt() {
        if(sharedDebt.getValue() != null)
            return sharedDebt.getValue();
        else
            return 0;
    }

    public MutableLiveData<Integer> getLiveSharedDebt() {
        return sharedDebt;
    }

    public void setLiveSharedDebt(MutableLiveData<Integer> sharedDebt) {
        this.sharedDebt = sharedDebt;
    }

    public void setSharedDebt(int a){
        this.sharedDebt.setValue(a);
    }
}
