package com.cavetale.fly;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public final class FlyPlugin extends JavaPlugin implements Listener {
    public static final String PERM = "fly.speed";

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        for (Player player : getServer().getOnlinePlayers()) {
            player.setFlySpeed(0.1f);
            player.setWalkSpeed(0.2f);
        }
    }

    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            player.setFlySpeed(0.1f);
            player.setWalkSpeed(0.2f);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission(PERM)) {
            event.getPlayer().setFlySpeed(0.1f);
            event.getPlayer().setWalkSpeed(0.2f);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!event.getPlayer().hasPermission(PERM)) {
            event.getPlayer().setFlySpeed(0.1f);
            event.getPlayer().setWalkSpeed(0.2f);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player expected");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            switch (label) {
            case "flyspeed":
                int flySpeed = (int) (player.getFlySpeed() * 10.0f);
                player.sendMessage(text("Fly speed: " + flySpeed, AQUA));
                break;
            case "walkspeed":
                int walkSpeed = (int) (player.getWalkSpeed() * 10.0f);
                player.sendMessage(text("Walk speed: " + walkSpeed, GREEN));
                break;
            default:
                throw new IllegalStateException("label=" + label);
            }
            return true;
        }
        if (args.length != 1) return false;
        float speed;
        try {
            speed = Float.parseFloat(args[0]);
        } catch (NumberFormatException nfe) {
            speed = 0;
        }
        if (speed < 0.0f || speed > 10.0f) {
            sender.sendMessage(text("Invalid speed: " + args[0], RED));
            return true;
        }
        float floatSpeed = (float) speed * 0.1f;
        switch (label) {
        case "flyspeed":
        case "fly:flyspeed":
            player.setFlySpeed(floatSpeed);
            player.sendMessage(text("Fly speed set to " + speed, AQUA));
            break;
        case "walkspeed":
        case "fly:walkspeed":
            player.setWalkSpeed(floatSpeed);
            player.sendMessage(text("Walk speed set to " + speed, GREEN));
            break;
        default:
            throw new IllegalStateException("label=" + label);
        }
        return true;
    }
}
