/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.fly.utils;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.core.utils.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class FlyManager {

    private FileManager configManager;
    private List<String> flying;
    private String prefix;

    public FlyManager(FileManager configManager) {
        this.configManager = configManager;
        flying = new ArrayList<>();
        prefix = Color.toColor(configManager.getFile().getString("lang.prefix"));
    }

    public void enable(Player player){
        flying.add(player.getUniqueId().toString());
        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage(
                Color.toColor(
                        configManager.getFile().getString("lang.success.fly-enabled")
                                .replaceAll("\\{prefix}", prefix)
                )
        );
    }

    public void disable(Player player){
        flying.remove(player.getUniqueId().toString());
        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(
                Color.toColor(
                        configManager.getFile().getString("lang.success.fly-disabled")
                                .replaceAll("\\{prefix}", prefix)
                )
        );
    }

    public boolean isActive(Player player){
        return flying.contains(player.getUniqueId().toString());
    }

    public boolean iterate(Player player){
        if (isActive(player)) {
            disable(player);
            return false;
        }else {
            enable(player);
            return true;
        }
    }
}
