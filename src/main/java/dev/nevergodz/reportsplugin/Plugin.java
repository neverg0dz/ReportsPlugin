package dev.nevergodz.reportsplugin;

import dev.nevergodz.reportsplugin.command.ReportCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("report").setExecutor(new ReportCommand());
    }

    @Override
    public void onDisable() {
    }
}
