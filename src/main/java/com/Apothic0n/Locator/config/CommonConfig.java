package com.Apothic0n.Locator.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

@Config(name = "locator")
public class CommonConfig implements ConfigData {
    public static boolean scanForTerrain = true;

    public static void registerCommonConfig(ConfigBuilder COMMON_BUILDER) {
        ConfigEntryBuilder entryBuilder = builder.entryBuilder(); // Get Entry Builder
        ConfigCategory category = builder.getOrCreateCategory(Text.of("Common Settings"));

        category.addEntry(entryBuilder.startBooleanToggle(Text.of("Scan For Terrain"), false)
                .setDefaultValue(false)
                .setTooltip(Text.of("When true, the locate biome command only returns positions if they are within 8 blocks vertically of terrain. Prevents false positive results."))
                .setSaveConsumer(newValue -> CommonConfig.scanForTerrain = newValue)
                .build());

        scanForTerrain = COMMON_BUILDER
                .comment("When true, the locate biome command only returns positions if they are within 8 blocks vertically of terrain. This prevents it returning positions where the biome is technically there, but theres no terrain and its just above an ocean. Reduces speed of command massively. Default: false")
                .define("scanForTerrain", false);

        COMMON_BUILDER.pop();
    }
}
