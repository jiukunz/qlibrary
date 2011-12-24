package net.luminis.android.felix.view;


import android.app.Activity;

public interface ActivityService {
    public Activity getActivity();
    public Object lookupSystemService(String name);
}
