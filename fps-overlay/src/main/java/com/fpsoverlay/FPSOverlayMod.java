package com.fpsoverlay;

import com.fpsoverlay.config.FPSConfig;
import com.fpsoverlay.keybind.KeyBindings;
import com.fpsoverlay.overlay.FPSOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("fpsoverlay")
public class FPSOverlayMod {

    public static final String MOD_ID = "fpsoverlay";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public FPSOverlayMod() {
        FPSConfig.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        KeyBindings.register();
        MinecraftForge.EVENT_BUS.register(new FPSOverlay());
        MinecraftForge.EVENT_BUS.register(new KeyBindings());
        LOGGER.info("FPS Overlay mod loaded. Press F9 to open settings.");
    }
}
