package com.galygious.galysentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;

public class HelpCommand extends BaseCommand {
    private final BaseCommand mainCommand;

    public HelpCommand(BaseCommand mainCommand) {
        super("help", "/gs help");
        this.mainCommand = mainCommand;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("These are the available commands:"));
        for (BaseCommand subCommand : mainCommand.getSubcommands().values()) {
            sender.sendMessage(new TextComponentString("- " + subCommand.getUsage(sender)));
        }
    }
}
