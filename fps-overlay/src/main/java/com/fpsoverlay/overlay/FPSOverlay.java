package com.fpsoverlay.overlay;

import com.fpsoverlay.config.FPSConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FPSOverlay {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        if (!FPSConfig.VISIBLE.get()) return;

        Minecraft mc = Minecraft.getInstance();
        FontRenderer font = mc.font;
        MatrixStack ms = event.getMatrixStack();

        int fps = Minecraft.fps;

        String prefix = FPSConfig.PREFIX.get();
        String suffix = FPSConfig.SUFFIX.get();

        StringBuilder sb = new StringBuilder();
        if (FPSConfig.BOLD.get())          sb.append("\u00a7l");
        if (FPSConfig.ITALIC.get())        sb.append("\u00a7o");
        if (FPSConfig.STRIKETHROUGH.get()) sb.append("\u00a7m");
        if (FPSConfig.UNDERLINE.get())     sb.append("\u00a7n");
        if (FPSConfig.OBFUSCATED.get())    sb.append("\u00a7k");
        sb.append(prefix).append(fps).append(suffix);

        String text = sb.toString();

        int x = FPSConfig.POS_X.get();
        int y = FPSConfig.POS_Y.get();
        int color = FPSConfig.getARGB();
        boolean shadow = FPSConfig.SHADOW.get();
        double scale = FPSConfig.SCALE.get();

        ms.pushPose();
        ms.scale((float) scale, (float) scale, 1.0f);

        int scaledX = (int) (x / scale);
        int scaledY = (int) (y / scale);

        StringTextComponent component = new StringTextComponent(text);

        if (shadow) {
            font.drawShadow(ms, component, scaledX, scaledY, color);
        } else {
            font.draw(ms, component, scaledX, scaledY, color);
        }

        ms.popPose();
    }
}
