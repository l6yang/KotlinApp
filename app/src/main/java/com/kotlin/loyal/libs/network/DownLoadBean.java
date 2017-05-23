package com.kotlin.loyal.libs.network;

import android.os.Parcel;
import android.os.Parcelable;

public class DownLoadBean implements Parcelable {
    private int progress;
    private long currentFileSize;
    private long totalFileSize;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(long currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public DownLoadBean() {
    }

    private DownLoadBean(Parcel in) {
        this.progress = in.readInt();
        this.currentFileSize = in.readLong();
        this.totalFileSize = in.readLong();
    }

    public static final Creator<DownLoadBean> CREATOR = new Creator<DownLoadBean>() {
        @Override
        public DownLoadBean createFromParcel(Parcel in) {
            return new DownLoadBean(in);
        }

        @Override
        public DownLoadBean[] newArray(int size) {
            return new DownLoadBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.progress);
        dest.writeLong(this.currentFileSize);
        dest.writeLong(this.totalFileSize);
    }
}
