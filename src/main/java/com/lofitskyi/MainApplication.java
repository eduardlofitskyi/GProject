package com.lofitskyi;

import com.lofitskyi.controller.rest.RoleResource;
import com.lofitskyi.controller.rest.UserResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MainApplication extends Application {
    private Set<Object> singletons = new HashSet<>();

    public MainApplication() {
        singletons.add(new UserResource());
        singletons.add(new RoleResource());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
