/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.fly.commands;

import net.wezland.survivalcore.core.FileManager;
import net.wezland.survivalcore.core.utils.Color;
import net.wezland.survivalcore.modules.AbstractModule;
import net.wezland.survivalcore.modules.fly.utils.FlyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public final class FlyCommand implements CommandExecutor {

    private AbstractModule module;
    private FileManager configManager;
    private FlyManager flyManager;

    public FlyCommand(AbstractModule module, FileManager configManager, FlyManager flyManager) {
        this.module = module;
        this.configManager = configManager;
        this.flyManager = flyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = configManager.getFile();
        String prefix = Color.toColor(config.getString("lang.prefix"));
        if (!(sender instanceof Player)) {
            if(args.length < 1){

                //{prefix} = prefix
                //{0} = usage

                module.getLogger().warning(
                        Color.strip(
                                config.getString("lang.error.incorrect-usage")
                                        .replaceAll("\\{prefix}", prefix)
                                        .replaceAll("\\{0}", "/fly <user> [enable | disable | on | off]")
                        ));
            }else {
                Player target = Bukkit.getPlayer(args[0]);

                //{prefix} = prefix
                //{0} = player

                if(target == null){
                    module.getLogger().warning(
                        Color.strip(
                                config.getString("lang.error.player-not-found")
                                        .replaceAll("\\{prefix}", prefix)
                                        .replaceAll("\\{0}", args[0])
                        )
                    );
                }else {
                    if(args.length == 1){

                        //{prefix} = prefix
                        //{0} = player

                        if(flyManager.iterate(target)) {
                            module.getLogger().info(
                                    Color.strip(
                                            config.getString("lang.success.others.fly-enabled")
                                                    .replaceAll("\\{prefix}", prefix)
                                                    .replaceAll("\\{0}", target.getName())
                                    )
                            );
                        }else {
                            module.getLogger().info(
                                    Color.strip(
                                            config.getString("lang.success.others.fly-disabled")
                                                    .replaceAll("\\{prefix}", prefix)
                                                    .replaceAll("\\{0}", target.getName())
                                    )
                            );
                        }
                    }else {
                        switch (args[1].toLowerCase()){
                            case "enable":
                            case "on":
                                if(flyManager.isActive(target)) {
                                    module.getLogger().warning(
                                            Color.strip(
                                                    config.getString("lang.error.flying-its-enabled")
                                                            .replaceAll("\\{prefix}", prefix)
                                                            .replaceAll("\\{0}", target.getName())
                                            )
                                    );
                                }else {
                                    flyManager.enable(target);
                                    module.getLogger().info(
                                            Color.strip(
                                                    config.getString("lang.success.others.fly-enabled")
                                                            .replaceAll("\\{prefix}", prefix)
                                                            .replaceAll("\\{0}", target.getName())
                                            )
                                    );
                                }
                                break;

                            case "disable":
                            case "off":
                                if(!flyManager.isActive(target)) {
                                    module.getLogger().warning(
                                            Color.strip(
                                                    config.getString("lang.error.flying-its-disabled")
                                                            .replaceAll("\\{prefix}", prefix)
                                                            .replaceAll("\\{0}", target.getName())
                                            )
                                    );
                                }else {
                                    flyManager.disable(target);
                                    module.getLogger().info(
                                            Color.strip(
                                                    config.getString("lang.success.others.fly-disabled")
                                                            .replaceAll("\\{prefix}", prefix)
                                                            .replaceAll("\\{0}", target.getName())
                                            )
                                    );
                                }
                                break;
                            default:
                                module.getLogger().warning(
                                        Color.strip(
                                                config.getString("lang.error.incorrect-usage")
                                                        .replaceAll("\\{prefix}", prefix)
                                                        .replaceAll("\\{0}", "/fly <user> [enable | disable | on | off]")
                                        ));
                        }
                    }
                }
            }
        }else {
            Player player = (Player) sender;
            if(args.length < 1){
                if(player.hasPermission("wezcore.fly.use")){
                    List<String> worlds = config.getStringList("config.disabled-worlds");
                    if(worlds.contains(player.getWorld().getName())){
                        if (player.hasPermission("wezcore.fly.bypass")){
                            flyManager.iterate(player);
                        }else {
                            player.sendMessage(Color.toColor(
                                    config.getString("lang.error.fly-not-allowed")
                                            .replaceAll("\\{prefix}", prefix)
                            ));
                        }
                    }else {
                        flyManager.iterate(player);
                    }
                }else {
                    player.sendMessage(
                            Color.toColor(
                                    config.getString("lang.error.no-permission")
                                            .replaceAll("\\{prefix}", prefix)
                            )
                    );
                }
            }else {
                Player target = Bukkit.getPlayer(args[0]);

                if(player.hasPermission("wezcore.fly.others")) {
                    if (target == null) {
                        player.sendMessage(
                                Color.toColor(
                                        config.getString("lang.error.player-not-found")
                                                .replaceAll("\\{prefix}", prefix)
                                                .replaceAll("\\{0}", args[0])
                                )
                        );
                    } else {
                        if (args.length == 1) {
                            if (flyManager.iterate(player)) {
                                player.sendMessage(
                                        Color.toColor(
                                                config.getString("lang.success.others.fly-enabled")
                                                        .replaceAll("\\{prefix}", prefix)
                                                        .replaceAll("\\{0}", target.getName())
                                        )
                                );
                            } else {
                                player.sendMessage(
                                        Color.toColor(
                                                config.getString("lang.success.others.fly-disabled")
                                                        .replaceAll("\\{prefix}", prefix)
                                                        .replaceAll("\\{0}", target.getName())
                                        )
                                );
                            }
                        }else {
                            switch (args[1].toLowerCase()){
                                case "enable":
                                case "on":
                                    if(flyManager.isActive(target)) {
                                        player.sendMessage(
                                                Color.toColor(
                                                        config.getString("lang.error.flying-its-enabled")
                                                                .replaceAll("\\{prefix}", prefix)
                                                                .replaceAll("\\{0}", target.getName())
                                                )
                                        );
                                    }else {
                                        flyManager.enable(target);
                                        player.sendMessage(
                                                Color.toColor(
                                                        config.getString("lang.success.others.fly-enabled")
                                                                .replaceAll("\\{prefix}", prefix)
                                                                .replaceAll("\\{0}", target.getName())
                                                )
                                        );
                                    }
                                    break;

                                case "disable":
                                case "off":
                                    if(!flyManager.isActive(target)) {
                                        player.sendMessage(
                                                Color.toColor(
                                                        config.getString("lang.error.flying-its-disabled")
                                                                .replaceAll("\\{prefix}", prefix)
                                                                .replaceAll("\\{0}", target.getName())
                                                )
                                        );
                                    }else {
                                        flyManager.disable(target);
                                        player.sendMessage(
                                                Color.strip(
                                                        config.getString("lang.success.others.fly-disabled")
                                                                .replaceAll("\\{prefix}", prefix)
                                                                .replaceAll("\\{0}", target.getName())
                                                )
                                        );
                                    }
                                    break;

                                default:
                                    player.sendMessage(
                                            Color.toColor(
                                                    config.getString("lang.error.incorrect-usage")
                                                            .replaceAll("\\{prefix}", prefix)
                                                            .replaceAll("\\{0}", "/fly <user> [enable | disable | on | off]")
                                            ));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
