/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.fly.listeners;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.core.utils.Color;
import net.wezland.survivalcore.modules.AbstractModule;
import net.wezland.survivalcore.modules.fly.utils.FlyManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public final class ChangeWorldListener implements Listener {

    private FileManager configManager;
    private AbstractModule module;
    private FlyManager flyManager;
    private String prefix;

    public ChangeWorldListener(FileManager configManager, AbstractModule module, FlyManager flyManager) {
        this.configManager = configManager;
        this.module = module;
        this.flyManager = flyManager;
        prefix = Color.toColor(configManager.getFile().getString("lang.prefix"));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        FileConfiguration config = configManager.getFile();
        List<String> disabledWorld = config.getStringList("config.disabled-worlds");
        Player player = event.getPlayer();

        if(disabledWorld.contains(player.getWorld().getName())){
            if(flyManager.isActive(player)){
                if(player.hasPermission("wezcore.fly.bypass") && player.hasPermission("wezcore.fly.use")){
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }else {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    /*player.sendMessage(
                            Color.toColor(
                                    config.getString("lang.success.fly-disabled")
                                            .replaceAll("\\{prefix}", prefix)
                            )
                    );*/
                }
            }
        }else {
            if(event.getPlayer().hasPermission("wezcore.fly.use")){
                if (flyManager.isActive(player)) {
                    event.getPlayer().setAllowFlight(true);
                    event.getPlayer().setFlying(true);
                }
            }
        }
    }
}
