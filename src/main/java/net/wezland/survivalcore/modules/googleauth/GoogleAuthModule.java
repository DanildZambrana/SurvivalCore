/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.googleauth;

import net.wezland.survivalcore.modules.AbstractModule;
import org.bukkit.plugin.java.JavaPlugin;

public class GoogleAuthModule extends AbstractModule {
    private JavaPlugin plugin;

    public GoogleAuthModule(JavaPlugin plugin){
        super("Google Auth", plugin.getLogger());
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
