package com.Apothic0n.Locator;

import com.Apothic0n.Locator.config.CommonConfig;
import com.Apothic0n.Locator.config.Configs;
import com.Apothic0n.Locator.core.events.CommonForgeEvents;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ModInitializer;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class Locator implements ModInitializer {
    public static final String MODID = "locator";

    public static CommonConfig CONFIG;

    public void onInitialize() {
        Configs.register();

        AutoConfig.register(CommonConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();
    }
}