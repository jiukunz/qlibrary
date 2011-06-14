
package org.bangbang.song.android.fileman.activity;

import org.bangbang.song.android.fileman.R;
import org.bangbang.song.android.fileman.component.ACtivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends ACtivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);
        setTheme(android.R.style.Theme_Dialog);

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_META_DATA);

            int versionCode = info.versionCode;
            String versionName = info.versionName;
            String about = String.format(getString(R.string.about), versionCode, versionName);
            ((TextView) findViewById(R.id.text)).setText(about);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ;
    }
}
