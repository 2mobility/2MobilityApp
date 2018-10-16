package com.mobility.a2mobilityapp.project.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by limjo15 on 5/7/2018.
 */

public class Uber implements Serializable{
    @SerializedName("product_id")
    private String productId;
    @SerializedName("display_name")
    private String display_name;
    @SerializedName("distance")
    private float distance;
    @SerializedName("duration")
    private int duration;
    @SerializedName("estimate")
    private String estimate;
    @SerializedName("high_estimate")
    private int highEstimate;
    @SerializedName("low_estimate")
    private int lowEstimate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public int getHighEstimate() {
        return highEstimate;
    }

    public void setHighEstimate(int highEstimate) {
        this.highEstimate = highEstimate;
    }

    public int getLowEstimate() {
        return lowEstimate;
    }

    public void setLowEstimate(int lowEstimate) {
        this.lowEstimate = lowEstimate;
    }
}
