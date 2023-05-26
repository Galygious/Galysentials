package com.galygious.galysentials;

import net.minecraftforge.common.config.Config;

@Config(modid = GalysentialsMain.MODID)
public class ModConfig {

    @Config.Comment("Maximum distance for random teleport")
    public static int rtpDistance = 1000;

    @Config.Comment("Cooldown time for random teleport (in seconds)")
    public static int rtpCooldown = 1;
}
