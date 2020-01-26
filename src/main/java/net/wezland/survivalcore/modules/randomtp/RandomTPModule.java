/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.randomtp;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.modules.AbstractModule;
import net.wezland.survivalcore.modules.randomtp.commands.RandomTPCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class RandomTPModule extends AbstractModule {

    private JavaPlugin plugin;
    private FileManager configManager;

    public RandomTPModule(JavaPlugin plugin) {
        super("Random TP", plugin.getLogger());
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
        configManager = new FileManager("randomtp_config.yml", plugin, "randomtp_config.yml");
        configManager.loadDefaultFile();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        plugin.getCommand("randomtp").setExecutor(new RandomTPCommand(configManager, this));
    }
}
