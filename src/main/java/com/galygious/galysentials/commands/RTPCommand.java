package com.galygious.galysentials.commands;

import com.galygious.galysentials.ModConfig;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RTPCommand extends BaseCommand {
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public RTPCommand() {
        super("rtp", "/gs rtp");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            UUID playerUUID = player.getUniqueID();

            // Check if the player is on cooldown
            Long lastTeleportTime = cooldowns.get(playerUUID);
            long currentTime = System.currentTimeMillis();

            System.out.println("Last teleport time: " + lastTeleportTime);
            System.out.println("Current time: " + currentTime);
            System.out.println("Cooldown: " + ModConfig.rtpCooldown);

            if (lastTeleportTime != null && currentTime - lastTeleportTime < (long)ModConfig.rtpCooldown * 1000) {
                // Player is on cooldown
                sender.sendMessage(new TextComponentString("You cannot use /gs rtp yet. Please wait."));
                return;
            }

            Random random = new Random();
            World world = player.world;

            for (int attempts = 0; attempts < 100; attempts++) {
                int x = random.nextInt(ModConfig.rtpDistance * 2) - ModConfig.rtpDistance;
                int z = random.nextInt(ModConfig.rtpDistance * 2) - ModConfig.rtpDistance;
                int y = world.getHeight(x, z);

                // Check if the blocks below are solid and the blocks where the player will stand are air
                if (world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial().isSolid()
                        && world.getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.AIR
                        && world.getBlockState(new BlockPos(x, y + 1, z)).getMaterial() == Material.AIR) {

                    // Check if there's no liquid around
                    boolean isLiquidNearby = false;
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            if (world.getBlockState(new BlockPos(x + dx, y - 1, z + dz)).getMaterial().isLiquid()
                                    || world.getBlockState(new BlockPos(x + dx, y, z + dz)).getMaterial().isLiquid()) {
                                isLiquidNearby = true;
                                break;
                            }
                        }
                        if (isLiquidNearby) {
                            break;
                        }
                    }

                    if (!isLiquidNearby) {
                        // Teleport the player
                        player.setPositionAndUpdate(x, y, z);

                        // Update the cooldown time
                        cooldowns.put(playerUUID, currentTime);

                        return;
                    }
                }
            }

            // If no safe location is found after all attempts, send a message to the player
            sender.sendMessage(new TextComponentString("Could not find a safe location to teleport."));
        } else {
            sender.sendMessage(new TextComponentString("Only players can use this command."));
        }
    }
}
