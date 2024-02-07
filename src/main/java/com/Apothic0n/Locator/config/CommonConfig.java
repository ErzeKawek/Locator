package com.Apothic0n.Locator.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static ForgeConfigSpec.BooleanValue scanForTerrain;

    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("General settings for Locator").push("common");

        scanForTerrain = COMMON_BUILDER
                .comment("When true, the locate biome command only returns positions if they are within 8 blocks vertically of terrain. This prevents it returning positions where the biome is technically there, but theres no terrain and its just above an ocean. Reduces speed of command massively. Default: false")
                .define("scanForTerrain", false);

        COMMON_BUILDER.pop();
    }
}
