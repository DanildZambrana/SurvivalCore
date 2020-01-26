/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules;

import java.util.logging.Logger;

/**
 * Structure of spigotPlugin-module for WezCore
 */
public abstract class AbstractModule {

    private String moduleName;
    private Logger logger;

    public AbstractModule(String moduleName, Logger logger){
        this.moduleName = moduleName;
        this.logger = logger;
    }


    /**
     * This method was called when the server load the plugin.
     */
    public void onLoad(){}

    /**
     * This method was called when the server enable the plugin.
     */
    public void onEnable(){
        logger.info("\t- " + moduleName);
    }

    /**
     * This method was called when the server disable the plugin.
     */
    public void onDisable(){}

    /**
     * Obtain a name of module.
     * @return a name of the module.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Obtain module logger.
     * @return Logger of plugin.
     */
    public Logger getLogger() {
        return logger;
    }
}
