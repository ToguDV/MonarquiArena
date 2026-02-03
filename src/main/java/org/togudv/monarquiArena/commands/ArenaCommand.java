package org.togudv.monarquiArena.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.togudv.monarquiArena.core.ArenaManager;

public class ArenaCommand implements CommandExecutor {

    private final ArenaManager arenaManager;

    public ArenaCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Uso: /arena join <nombre_arena>");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {
            if (args.length < 2) {
                player.sendMessage("Uso: /arena join <nombre_arena>");
                return true;
            }

            String arenaName = args[1];
            String result = arenaManager.joinArena(player, arenaName);
            player.sendMessage(result);
            return true;
        }

        player.sendMessage("Uso: /arena join <nombre_arena>");
        return true;
    }
}