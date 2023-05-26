package com.galygious.galysentials.commands;

import com.galygious.galysentials.GalysentialsMain;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class VersionCommand extends BaseCommand {

    public VersionCommand() {
        super("version", "/gs version");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        TextComponentString versionText = new TextComponentString(GalysentialsMain.NAME + " Version: " + GalysentialsMain.VERSION);
        sender.sendMessage(versionText);
    }
}
