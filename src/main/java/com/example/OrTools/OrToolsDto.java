package com.example.OrTools;

public class OrToolsDto {
    private long[][] distanceMatrix;
    private int vehicleNumber;
    private int depot;

    public long[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(long[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getDepot() {
        return depot;
    }

    public void setDepot(int depot) {
        this.depot = depot;
    }
}
