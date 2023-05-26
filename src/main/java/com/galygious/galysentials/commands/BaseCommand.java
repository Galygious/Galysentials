package com.galygious.galysentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.HashMap;
import java.util.Map;

public class BaseCommand extends CommandBase {
    protected final String name;
    protected String usage;
    protected Map<String, BaseCommand> subCommands = new HashMap<>();

    public BaseCommand(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    public void addSubcommand(BaseCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length > 0) {
            BaseCommand subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                subCommand.execute(server, sender, removeFirst(args));
                return;
            }
        }

        // Display usage when subcommand is not found or no arguments are provided
        TextComponentString usageText = new TextComponentString(getUsage(sender));
        sender.sendMessage(usageText);
    }

    private String[] removeFirst(String[] args) {
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);
        return newArgs;
    }

    public Map<String, BaseCommand> getSubcommands() {
        return subCommands;
    }

}
