package com.fpsoverlay.overlay;

import com.fpsoverlay.config.FPSConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
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

        int fps = mc.getFps();

        String prefix = FPSConfig.PREFIX.get();
        String suffix = FPSConfig.SUFFIX.get();
        String text = prefix + fps + suffix;

        Style style = Style.EMPTY
                .withBold(FPSConfig.BOLD.get())
                .withItalic(FPSConfig.ITALIC.get())
                .withStrikethrough(FPSConfig.STRIKETHROUGH.get())
                .withUnderlined(FPSConfig.UNDERLINE.get())
                .withObfuscated(FPSConfig.OBFUSCATED.get());

        IFormattableTextComponent component = new StringTextComponent(text).withStyle(style);

        int x = FPSConfig.POS_X.get();
        int y = FPSConfig.POS_Y.get();
        int color = FPSConfig.getARGB();
        boolean shadow = FPSConfig.SHADOW.get();
        double scale = FPSConfig.SCALE.get();

        ms.pushPose();
        ms.scale((float) scale, (float) scale, 1.0f);

        int scaledX = (int) (x / scale);
        int scaledY = (int) (y / scale);

        if (shadow) {
            font.drawShadow(ms, component, scaledX, scaledY, color);
        } else {
            font.draw(ms, component, scaledX, scaledY, color);
        }

        ms.popPose();
    }
}
