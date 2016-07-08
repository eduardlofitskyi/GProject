package com.lofitskyi;

import com.lofitskyi.controller.rest.RoleRestService;
import com.lofitskyi.controller.rest.UserRestService;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MainApplication extends Application {
    private Set<Object> singletons = new HashSet<>();

    public MainApplication() {
        singletons.add(new UserRestService());
        singletons.add(new RoleRestService());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
