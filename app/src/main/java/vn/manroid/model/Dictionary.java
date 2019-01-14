package vn.manroid.model;

import java.io.Serializable;

/**
 * Created by Manroid on 01/04/2017.
 */

public class Dictionary implements Serializable {
    private String mean;

    public Dictionary() {
    }

    public Dictionary(String mean) {
        this.mean = mean;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
