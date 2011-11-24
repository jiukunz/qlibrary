package com.broadgalaxy.bluz.persistence;

import android.provider.BaseColumns;

public interface IMsg extends BaseColumns{
    public static final String COLUMN_FROM_ADDRESS = "from_address";
    public static final String COLUMN_DEST_ADDRESS = "dest_address";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_DATA_LEN = "data_len";
    
    public static final int STATUS_INVALID = 0;
    public static final int STATUS_RCVD = 1;
    public static final int STATUS_SENT = 2;
    public static final int STATUS_DRAFT = 3;
    public static final int STATUS_SENDING = 4;
    public static final String COLUMN_STATUS = "status";
}
