package com.galygious.galysentials;

import com.galygious.galysentials.commands.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "galysentials", name = "Galysentials", version = "1.0")
public class GalysentialsMain {

    public static final String MODID = "galysentials";
    public static final String NAME = "Galysentials Mod";
    public static final String VERSION = "1.0";

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        BaseCommand mainCommand = new BaseCommand("gs", "/gs help");
        mainCommand.addSubcommand(new VersionCommand());

        HelpCommand helpCommand = new HelpCommand(mainCommand);
        mainCommand.addSubcommand(helpCommand);

        RTPCommand rtpCommand = new RTPCommand();
        mainCommand.addSubcommand(rtpCommand);

        event.registerServerCommand(mainCommand);

        HomeManager homeManager = new HomeManager();
        mainCommand.addSubcommand(new HomeCommand(homeManager));
        mainCommand.addSubcommand(new SetHomeCommand(homeManager));
        mainCommand.addSubcommand(new DelHomeCommand(homeManager));

    }
}
