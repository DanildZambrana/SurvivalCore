/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.fly;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.modules.AbstractModule;
import net.wezland.survivalcore.modules.fly.commands.FlyCommand;
import net.wezland.survivalcore.modules.fly.listeners.ChangeWorldListener;
import net.wezland.survivalcore.modules.fly.utils.FlyManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlyModule extends AbstractModule {
    private JavaPlugin plugin;
    private FileManager configManager;
    private FlyManager flyManager;

    public FlyModule(JavaPlugin plugin) {
        super("Fly", plugin.getLogger());
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
        configManager = new FileManager("fly_config.yml", plugin, "fly_config.yml");
        configManager.loadDefaultFile();
        flyManager = new FlyManager(configManager);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        plugin.getCommand("fly").setExecutor(new FlyCommand(this, configManager, flyManager));
        plugin.getServer().getPluginManager().registerEvents(new ChangeWorldListener(configManager, this, flyManager), plugin);
    }
}
