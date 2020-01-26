/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.core.utils;

import org.bukkit.ChatColor;

public interface Color {
    static String toColor(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    static String strip(String text){
        return ChatColor.stripColor(toColor(text));
    }

    static boolean compare(String text1, String text2){
        return strip(text1).equalsIgnoreCase(text2);
    }
}