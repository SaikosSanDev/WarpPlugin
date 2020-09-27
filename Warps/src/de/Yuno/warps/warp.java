package de.Yuno.warps;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class warp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            FileConfiguration warps = Main.getWarps();
            if(args.length == 1) {
                    if(args[0].equals("list")) {
                        if(p.hasPermission("warp.list")) {
                            if(warps.getConfigurationSection("warps") != null) {
                                String[] warpList = (String[]) warps.getConfigurationSection("warps").getKeys(false).toArray();
                                if(warpList.length > 1) {
                                    String finalList = "";
                                    for(String part : warpList) {
                                        finalList += "§e, §a" + part;
                                    }
                                    finalList = finalList.substring(4);
                                    p.sendMessage("§e" + warpList.length + " §aWarps :");
                                    p.sendMessage(finalList);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                } else {
                                    p.sendMessage("§e1 §aWarp :");
                                    p.sendMessage("§a" + warpList[0]);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                                }
                            } else p.sendMessage("§cEs existieren keine Warps !");
                        } else p.sendMessage("§cDu hast nicht die Permission dafür !");
                    } else {
                        if(p.hasPermission("warp.teleport")) {
                            String warp = args[0];
                            if(warps.get("warps." + warp) != null) {
                                Location warpLoc = (Location) warps.get("warps." + warp);
                                p.teleport(warpLoc);
                                p.sendMessage("§aDu wurdest zu §e" + warp + " §agewarpt !");
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1 , 1);
                            } else p.sendMessage("§cDieser Warp existiert nicht !");
                        } else p.sendMessage("§cDu hast nicht die Permission dafür !");
                    }
            } else if(args.length == 2) {
                if(args[0].equals("set")) {
                    if(p.hasPermission("warp.set")) {
                        if(warps.get("warps." + args[1]) == null) {
                            warps.set("warps." + args[1] , p.getLocation());
                            Main.saveWarps(warps);
                            p.sendMessage("§aDer Warp §e" + args[1] + " §awurde erfolgreich erstellt !");
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                        } else p.sendMessage("§cDieser Warp existiert schon !");
                    } else p.sendMessage("§cDu hast nicht die Permission dafür !");
                } else if(args[0].equals("delete")) {
                    if(p.hasPermission("warp.delete")) {
                        if(warps.get("warps." + args[1]) != null) {
                            warps.set("warps." + args[1] , null);
                            Main.saveWarps(warps);
                            p.sendMessage("§aDer Warp wurde erfolgreich gelöscht !");
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
                        } else p.sendMessage("§cDieser Warp existiert nicht !");
                    } else p.sendMessage("§cDu hast nicht die Permission dafür !");
                }
            } else p.sendMessage("§cNutze §e/warp [name] §coder §e/warp set/delete [name] §c!");
        } else sender.sendMessage("Du kannst das nur als Spieler !");
        return false;
    }
}
