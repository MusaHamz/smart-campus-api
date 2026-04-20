package com.musa.smartcampus;

import com.musa.smartcampus.filter.ApiLoggingFilter;
import com.musa.smartcampus.mapper.GenericExceptionMapper;
import com.musa.smartcampus.mapper.LinkedResourceNotFoundExceptionMapper;
import com.musa.smartcampus.mapper.RoomNotEmptyExceptionMapper;
import com.musa.smartcampus.mapper.SensorUnavailableExceptionMapper;
import com.musa.smartcampus.resource.DiscoveryResource;
import com.musa.smartcampus.resource.RoomResource;
import com.musa.smartcampus.resource.SensorResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class SmartCampusApplication extends ResourceConfig {

    public SmartCampusApplication() {
        register(DiscoveryResource.class);
        register(RoomResource.class);
        register(SensorResource.class);

        register(RoomNotEmptyExceptionMapper.class);
        register(LinkedResourceNotFoundExceptionMapper.class);
        register(SensorUnavailableExceptionMapper.class);
        register(GenericExceptionMapper.class);

        register(ApiLoggingFilter.class);
    }
}