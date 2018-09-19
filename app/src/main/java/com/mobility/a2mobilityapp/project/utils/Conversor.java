package com.mobility.a2mobilityapp.project.utils;

public class Conversor {

    double valorKm = 1.609;

    public double ConverteParaKM(double milha){
        return valorKm * milha;
    }
}
