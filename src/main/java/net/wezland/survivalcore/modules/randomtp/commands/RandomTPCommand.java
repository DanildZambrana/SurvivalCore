/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.randomtp.commands;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.core.utils.Color;
import net.wezland.survivalcore.modules.AbstractModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;

public final class RandomTPCommand implements CommandExecutor {

    private FileManager configManager;
    private AbstractModule module;
    private String prefix;

    public RandomTPCommand(FileManager configManager, AbstractModule module) {
        this.configManager = configManager;
        this.module = module;
        prefix = configManager.getFile().getString("lang.prefix");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = configManager.getFile();

        if (!(sender instanceof Player)) {
            if (args.length < 1) {

                //{prefix} = prefix
                //{0} = usage

                module.getLogger().warning(
                        Color.strip(
                                config.getString("lang.error.incorrect-usage")
                                        .replaceAll("\\{prefix}", prefix)
                                        .replaceAll("\\{0}", "randomtp <player>")
                        ));
            }else {
                Player target = Bukkit.getPlayer(args[0]);

                if(target == null){
                    module.getLogger().warning(
                            Color.strip(
                                    config.getString("lang.error.player-not-found")
                                            .replaceAll("\\{prefix}", prefix)
                                            .replaceAll("\\{0}", args[0])
                            )
                    );
                }else {
                    teleport(target);
                }
            }
        }else {
            Player player = (Player) sender;
            if(args.length < 1){
                if(sender.hasPermission("wezcore.randomtp.use")){
                    teleport(player);
                }else {
                    player.sendMessage(
                            Color.toColor(
                                    config.getString("lang.error.no-permission")
                                            .replaceAll("\\{prefix}", prefix)
                            )
                    );
                }
            }else {
                if(sender.hasPermission("wezcore.randomtp.others")){
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null){
                        player.sendMessage(
                                Color.toColor(
                                        config.getString("lang.error.player-not-found")
                                                .replaceAll("\\{prefix}", prefix)
                                                .replaceAll("\\{0}", args[0])
                                )
                        );
                    }else {
                        teleport(target);
                    }
                }
            }
        }

        return true;
    }

    private boolean teleport(Player player){
        FileConfiguration config = configManager.getFile();

        double distance = config.getDouble("settings.distance");
        double centerX = config.getDouble("settings.center-x");
        double centerY = config.getDouble("settings.center-y");
        int maxIntents = config.getInt("settings.max-intents");
        double[] rPos = generatePos(player.getLocation(), distance);
        Location randomLocation = new Location( player.getWorld(), rPos[0], 0, rPos[1]);

        Location toTeleport = getSafeLocation(randomLocation);

        for (int i = 0; i < maxIntents; i++){
            if(toTeleport == null){
                rPos = generatePos(new Location(player.getWorld(), centerX, 0, centerY), distance);
                randomLocation = new Location(player.getWorld(), rPos[0], 0, rPos[1]);

                toTeleport = getSafeLocation(randomLocation);
            }else {
                player.teleport(toTeleport.add(0.5, 1, 0.5));
                player.sendMessage(
                        Color.toColor(
                                config.getString("lang.success.teleported")
                                        .replaceAll("\\{prefix}", prefix)
                                        .replaceAll("\\{0}", toTeleport.getX() + "")
                                        .replaceAll("\\{1}", toTeleport.getY() + "")
                                        .replaceAll("\\{2}", toTeleport.getZ() + "")
                        )
                );
                return true;
            }
        }
        return false;
    }

    private double[] generatePos(Location playerLocation, double distance) {
        double[] xBorder = {playerLocation.getBlockX() - distance, playerLocation.getBlockX() + distance};
        double[] zBorder = {playerLocation.getBlockZ() - distance, playerLocation.getBlockZ() + distance};

        Random rand = new Random();

        double xRPos = rand.nextInt((int) (Math.floor(xBorder[1]) - Math.floor(xBorder[0]))) + xBorder[0] + 0.5;
        double zRPos = rand.nextInt((int) (Math.floor(zBorder[1]) - Math.floor(zBorder[0]))) + zBorder[0] + 0.5;

        return new double[]{xRPos, zRPos};
    }

    private Location getSafeLocation(Location location) {
        Block b = location.getWorld().getHighestBlockAt(location);
        if(!b.isEmpty() && !b.isLiquid()) {
            return b.getLocation();
        }
        return null;
    }
}
