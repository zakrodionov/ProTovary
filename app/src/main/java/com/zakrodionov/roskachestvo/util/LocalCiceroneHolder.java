package com.zakrodionov.roskachestvo.util;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

import java.util.HashMap;

public class LocalCiceroneHolder {
    private HashMap<String, Cicerone<Router>> containers;

    public LocalCiceroneHolder() {
        containers = new HashMap<>();
    }

    public Cicerone<Router> getCicerone(String containerTag) {
        if (!containers.containsKey(containerTag)) {
            containers.put(containerTag, Cicerone.create());
        }
        return containers.get(containerTag);
    }
}