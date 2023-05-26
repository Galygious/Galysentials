package com.galygious.galysentials.commands;

import com.galygious.galysentials.HomeManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.UUID;

public class DelHomeCommand extends BaseCommand {
    private HomeManager homeManager;

    public DelHomeCommand(HomeManager homeManager) {
        super("delhome", "/gs delhome <name>");
        this.homeManager = homeManager;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            // Command cannot be used by non-players
            sender.sendMessage(new TextComponentString("Only players can use this command."));
            return;
        }

        if (args.length == 0) {
            // Missing argument
            sender.sendMessage(new TextComponentString("Please provide the name of the home to delete."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        UUID playerUUID = player.getUniqueID();
        String homeName = args[0];

        boolean deleted = homeManager.deleteHome(playerUUID, homeName);
        if (deleted) {
            sender.sendMessage(new TextComponentString("Home '" + homeName + "' has been deleted."));
        } else {
            sender.sendMessage(new TextComponentString("Could not find a home with the name '" + homeName + "'."));
        }
    }
}

