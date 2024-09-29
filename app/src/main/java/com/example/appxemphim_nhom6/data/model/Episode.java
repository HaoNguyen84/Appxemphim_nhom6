package com.example.appxemphim_nhom6.data.model;

import java.util.List;

public class Episode {
    private String server_name;
    private List<ServerData> server_data;

    // Getters and Setters
    public String getServerName() {
        return server_name;
    }

    public void setServerName(String server_name) {
        this.server_name = server_name;
    }

    public List<ServerData> getServerData() {
        return server_data;
    }

    public void setServerData(List<ServerData> server_data) {
        this.server_data = server_data;
    }
}


