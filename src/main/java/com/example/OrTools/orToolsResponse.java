package com.example.OrTools;

public class orToolsResponse {
    private long Objective;
    private String path;
    private long RouteDistance;

    public long getObjective() {
        return Objective;
    }

    public void setObjective(long objective) {
        Objective = objective;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getRouteDistance() {
        return RouteDistance;
    }

    public void setRouteDistance(long routeDistance) {
        RouteDistance = routeDistance;
    }
}
