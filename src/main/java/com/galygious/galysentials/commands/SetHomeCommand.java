package com.galygious.galysentials.commands;

import com.galygious.galysentials.Home;
import com.galygious.galysentials.HomeManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class SetHomeCommand extends BaseCommand {
    private final HomeManager homeManager;

    public SetHomeCommand(HomeManager homeManager) {
        super("sethome", "/gs sethome <name>");
        this.homeManager = homeManager;
    }

    // Implement the execute method
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            if (args.length > 0) {
                String homeName = args[0];
                Home home = new Home(homeName, player.dimension, player.getPosition());
                homeManager.setHome(player.getUniqueID(), homeName, home);
                sender.sendMessage(new TextComponentString("Home '" + homeName + "' set!"));
            } else {
                sender.sendMessage(new TextComponentString("You must provide a name for the home."));
            }
        } else {
            sender.sendMessage(new TextComponentString("Only players can set homes!"));
        }
    }

}

