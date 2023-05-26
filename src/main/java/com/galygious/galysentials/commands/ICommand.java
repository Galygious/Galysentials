package com.galygious.galysentials.commands;

import net.minecraft.command.ICommandSender;

public interface ICommand {
    void execute(ICommandSender sender, String[] args);
}
