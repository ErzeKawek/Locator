package com.Apothic0n.Locator;

import com.Apothic0n.Locator.config.Configs;
import net.minecraftforge.fml.common.Mod;

@Mod(Locator.MODID)
public class Locator {
    public static final String MODID = "locator";

    public Locator() {
        Configs.register();
    }
}