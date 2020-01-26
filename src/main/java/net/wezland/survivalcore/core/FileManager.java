/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.core;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class FileManager {
    private File file = null;
    private FileConfiguration fileC = null;
    private JavaPlugin plugin;
    private String nameOfFile;
    private String defaultFile =  null;

    /**
     * Construct file manager with.
     * @param nameOfFile the nickname of file.
     * @param plugin the java plugin main.
     */
    public FileManager(String nameOfFile, JavaPlugin plugin){
        this.plugin = plugin;
        this.nameOfFile = nameOfFile;
    }

    /**
     * Construct file manager with other yaml file.
     * @param nameOfFile the nickname of file.
     * @param plugin the java plugin main.
     * @param defaultFile the name of the file in the directory.
     */
    public FileManager(String nameOfFile, JavaPlugin plugin, String defaultFile){
        this.plugin = plugin;
        this.nameOfFile = nameOfFile;
        this.defaultFile = defaultFile;
    }


    /**
     * Get the file {@link FileConfiguration}
     * @return the file object.
     */
    public FileConfiguration getFile(){
        if(fileC == null){
            reloadFile();
        }
        return fileC;
    }

    /**
     * Reload the config.
     */
    public void reloadFile() {
        String filename = (defaultFile != null) ? defaultFile : "Default.yml";

        if(fileC == null) {
            file = new File(plugin.getDataFolder(),nameOfFile);
        }
        fileC = YamlConfiguration.loadConfiguration(file);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(plugin.getResource(filename), StandardCharsets.UTF_8);

        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        fileC.setDefaults(defConfig);
    }

    /**
     * Save changes in the file.
     */
    public void saveFile() {
        try {
            fileC.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Create file in the directory if not exist.
     * @param archive the nickname of file.
     */
    public void loadDefaultFile(String archive) {
        file = new File(plugin.getDataFolder(),nameOfFile);
        if(!file.exists()) {
            this.getFile().options().copyDefaults(true);
            plugin.getLogger().info(ChatColor.RED +archive+ ChatColor.GRAY+" is not founded, creating archive...");
            saveFile();
        }
    }

    /**
     * Create the file without nickname if not exist in the directory.
     */
    public void loadDefaultFile() {
        loadDefaultFile(nameOfFile);
    }
}
