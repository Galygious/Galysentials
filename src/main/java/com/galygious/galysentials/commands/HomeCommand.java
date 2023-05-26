package com.galygious.galysentials.commands;

import com.galygious.galysentials.Home;
import com.galygious.galysentials.HomeManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
import java.util.UUID;

public class HomeCommand extends BaseCommand {
    private final HomeManager homeManager;

    public HomeCommand(HomeManager homeManager) {
        super("home", "/gs home <name>");
        this.homeManager = homeManager;
    }

    // Implement the execute method
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            // Command cannot be used by non-players
            sender.sendMessage(new TextComponentString("Only players can use this command."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        UUID playerUUID = player.getUniqueID();

        if (args.length == 0) {
            // If no argument provided, list homes
            List<Home> homes = homeManager.getHomes(playerUUID);
            if (homes.isEmpty()) {
                sender.sendMessage(new TextComponentString("You have no homes set."));
                return;
            }
            String homeNames = homes.stream().map(Home::getName).collect(Collectors.joining(", "));
            sender.sendMessage(new TextComponentString("Your homes: " + homeNames));
            return;
        }

        String homeName = args[0];

        Home home = homeManager.getHome(playerUUID, homeName);
        if (home == null) {
            sender.sendMessage(new TextComponentString("Could not find a home with the name '" + homeName + "'."));
            return;
        }

        BlockPos pos = home.getPosition();
        player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        sender.sendMessage(new TextComponentString("Welcome to home '" + homeName + "'."));
    }
}
