package dev.nevergodz.reportsplugin.command;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только игроки могут использовать эту команду.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("Использование: /report <ник> <причина>");
            return true;
        }

        Player reportedPlayer = Bukkit.getPlayerExact(args[0]);
        if (reportedPlayer == null) {
            sender.sendMessage("Игрок с таким ником не найден на сервере.");
            return true;
        }

        String reason = Arrays.stream(args, 1, args.length).collect(Collectors.joining(" "));
        Player reporter = (Player) sender;
        String reportMessage = "!!! " + reporter.getName() + " отправил репорт на игрока " + args[0] + "!!!";
        String hoverMessage = "Игрок: " + args[0] + "\nПричина: " + reason;

        TextComponent messageComponent = new TextComponent(reportMessage);
        messageComponent.setColor(net.md_5.bungee.api.ChatColor.RED);

        BaseComponent[] hoverComponents = new ComponentBuilder(hoverMessage).create();
        messageComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponents));
        messageComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spec " + args[0]));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isOp()) {
                onlinePlayer.spigot().sendMessage(messageComponent);
            }
        }
        reporter.sendMessage("Репорт отправлен.");
        return true;
    }
}