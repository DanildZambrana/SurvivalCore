/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.modules.AbstractModule;
import net.wezland.survivalcore.modules.fly.FlyModule;
import net.wezland.survivalcore.modules.randomtp.RandomTPModule;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class SurvivalCore extends JavaPlugin {
    private List<AbstractModule> modules;

    {
        modules = Arrays.asList(new FlyModule(this), new RandomTPModule(this));
    }

    @Override
    public void onLoad() {
        modules.forEach(AbstractModule::onLoad);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Habilitando modulos:");
        modules.forEach(AbstractModule::onEnable);
        loadFiles();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        modules.forEach(AbstractModule::onDisable);
    }

    private void loadFiles() {
        //Config
        File config = new File(this.getDataFolder(), ("config.yml"));
        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            getLogger().info(ChatColor.RED + "config.yml" + ChatColor.GRAY + "is not found, creating file..." );
            saveConfig();
        }

        //Lang
        String nameOfFile = getConfig().getString("lang");//Load lang.yml
        new FileManager(nameOfFile, this, "default_lang.yml").loadDefaultFile("lang");
    }
}
