package com.musa.smartcampus.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo() {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("name", "Smart Campus Sensor & Room Management API");
        response.put("version", "v1");
        response.put("description", "REST API for managing rooms, sensors, and sensor readings.");

        Map<String, String> admin = new LinkedHashMap<>();
        admin.put("moduleLeader", "Hamed Hamzeh");
        admin.put("contact", "your-email@westminster.ac.uk");
        response.put("admin", admin);

        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        response.put("resources", resources);

        return response;
    }
}