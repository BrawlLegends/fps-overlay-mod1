package com.fpsoverlay.gui;

import com.fpsoverlay.config.FPSConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OverlaySettingsScreen extends Screen {

    // --- drag state ---
    private boolean dragging = false;
    private int dragOffX, dragOffY;

    // --- live preview values (updated from widgets) ---
    private int previewX, previewY;
    private int previewR, previewG, previewB, previewA;
    private boolean previewBold, previewItalic, previewStrike, previewUnder, previewObfs, previewShadow, previewVisible;
    private double previewScale;
    private String previewPrefix, previewSuffix;

    // --- widgets ---
    private ColorSlider sliderR, sliderG, sliderB, sliderA, sliderScale;
    private TextFieldWidget fieldPrefix, fieldSuffix;
    private CheckboxButton cbBold, cbItalic, cbStrike, cbUnder, cbObfs, cbShadow, cbVisible;

    public OverlaySettingsScreen() {
        super(new StringTextComponent("FPS Overlay Settings"));
        loadFromConfig();
    }

    private void loadFromConfig() {
        previewX      = FPSConfig.POS_X.get();
        previewY      = FPSConfig.POS_Y.get();
        previewR      = FPSConfig.COLOR_R.get();
        previewG      = FPSConfig.COLOR_G.get();
        previewB      = FPSConfig.COLOR_B.get();
        previewA      = FPSConfig.COLOR_A.get();
        previewBold        = FPSConfig.BOLD.get();
        previewItalic      = FPSConfig.ITALIC.get();
        previewStrike      = FPSConfig.STRIKETHROUGH.get();
        previewUnder       = FPSConfig.UNDERLINE.get();
        previewObfs        = FPSConfig.OBFUSCATED.get();
        previewShadow      = FPSConfig.SHADOW.get();
        previewVisible     = FPSConfig.VISIBLE.get();
        previewScale  = FPSConfig.SCALE.get();
        previewPrefix = FPSConfig.PREFIX.get();
        previewSuffix = FPSConfig.SUFFIX.get();
    }

    @Override
    protected void init() {
        int midX = width / 2;
        int panelLeft = midX - 190;
        int panelTop  = 20;
        int col2 = panelLeft + 130;
        int rowH = 22;
        int row = panelTop + 10;

        // --- Color sliders ---
        sliderR = addWidget(new ColorSlider(panelLeft, row, 160, 18, "R", previewR, 0, 255, v -> previewR = v));
        row += rowH;
        sliderG = addWidget(new ColorSlider(panelLeft, row, 160, 18, "G", previewG, 0, 255, v -> previewG = v));
        row += rowH;
        sliderB = addWidget(new ColorSlider(panelLeft, row, 160, 18, "B", previewB, 0, 255, v -> previewB = v));
        row += rowH;
        sliderA = addWidget(new ColorSlider(panelLeft, row, 160, 18, "A", previewA, 0, 255, v -> previewA = v));
        row += rowH + 4;

        // --- Scale slider ---
        sliderScale = addWidget(new ColorSlider(panelLeft, row, 160, 18, "Scale", (int)(previewScale * 100), 25, 500, v -> previewScale = v / 100.0));
        row += rowH + 4;

        // --- Prefix / Suffix ---
        addWidget(new net.minecraft.client.gui.widget.Widget(panelLeft, row, 0, 18, new StringTextComponent("Prefix:")) {
            @Override public void render(MatrixStack ms, int mx, int my, float pt) {
                OverlaySettingsScreen.this.font.draw(ms, "Prefix:", x, y + 4, 0xDDDDDD);
            }
        });
        fieldPrefix = addWidget(new TextFieldWidget(font, col2, row, 120, 18, new StringTextComponent("prefix")));
        fieldPrefix.setMaxLength(64);
        fieldPrefix.setValue(previewPrefix);
        fieldPrefix.setResponder(s -> previewPrefix = s);
        row += rowH;

        addWidget(new net.minecraft.client.gui.widget.Widget(panelLeft, row, 0, 18, new StringTextComponent("Suffix:")) {
            @Override public void render(MatrixStack ms, int mx, int my, float pt) {
                OverlaySettingsScreen.this.font.draw(ms, "Suffix:", x, y + 4, 0xDDDDDD);
            }
        });
        fieldSuffix = addWidget(new TextFieldWidget(font, col2, row, 120, 18, new StringTextComponent("suffix")));
        fieldSuffix.setMaxLength(64);
        fieldSuffix.setValue(previewSuffix);
        fieldSuffix.setResponder(s -> previewSuffix = s);
        row += rowH + 6;

        // --- Style checkboxes (2 columns) ---
        int cbLeft  = panelLeft;
        int cbRight = panelLeft + 100;
        cbBold   = addWidget(new CheckboxButton(cbLeft,  row, 90, 20, new StringTextComponent("Bold"),          previewBold));
        cbItalic = addWidget(new CheckboxButton(cbRight, row, 90, 20, new StringTextComponent("Italic"),        previewItalic));
        row += rowH;
        cbStrike  = addWidget(new CheckboxButton(cbLeft,  row, 90, 20, new StringTextComponent("Strikethrough"), previewStrike));
        cbUnder   = addWidget(new CheckboxButton(cbRight, row, 90, 20, new StringTextComponent("Underline"),     previewUnder));
        row += rowH;
        cbObfs    = addWidget(new CheckboxButton(cbLeft,  row, 90, 20, new StringTextComponent("Obfuscated"),    previewObfs));
        cbShadow  = addWidget(new CheckboxButton(cbRight, row, 90, 20, new StringTextComponent("Shadow"),        previewShadow));
        row += rowH;
        cbVisible = addWidget(new CheckboxButton(cbLeft,  row, 90, 20, new StringTextComponent("Visible"),       previewVisible));
        row += rowH + 8;

        // --- Buttons ---
        addButton(new Button(panelLeft, row, 100, 20,
                new StringTextComponent("Save & Close"), btn -> {
            saveToConfig();
            onClose();
        }));
        addButton(new Button(panelLeft + 110, row, 80, 20,
                new StringTextComponent("Cancel"), btn -> onClose()));

        // --- Drag hint ---
        // (rendered in render method)
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTick) {
        // Background panel
        int panelLeft = width / 2 - 190;
        int panelTop  = 15;
        int panelW    = 270;
        int panelH    = height - 30;
        fill(ms, panelLeft - 5, panelTop, panelLeft + panelW, panelTop + panelH, 0xCC000000);

        // Title
        font.drawShadow(ms, "§lFPS Overlay Settings", panelLeft, panelTop - 12, 0xFFFFFF);

        // Drag hint
        int hintX = width / 2 + 100;
        font.drawShadow(ms, "§7[Drag preview to reposition]", hintX - 60, height - 20, 0xAAAAAA);

        // Color swatch
        int swatchX = panelLeft + 170;
        int swatchY = panelTop + 10;
        int argb = buildARGB();
        fill(ms, swatchX, swatchY, swatchX + 18, swatchY + 18 * 4 + 4, argb);

        // Draw widgets / children
        super.render(ms, mouseX, mouseY, partialTick);

        // Live preview of the FPS text (positioned near the actual overlay spot)
        renderPreviewText(ms);

        // Coordinates label
        font.draw(ms, "X: " + previewX + "  Y: " + previewY, width / 2 + 95, height - 35, 0xCCCCCC);
    }

    private void renderPreviewText(MatrixStack ms) {
        sync();
        String text = previewPrefix + minecraft.getFps() + previewSuffix;

        Style style = Style.EMPTY
                .withBold(previewBold)
                .withItalic(previewItalic)
                .withStrikethrough(previewStrike)
                .withUnderlined(previewUnder)
                .withObfuscated(previewObfs);

        IFormattableTextComponent component = new StringTextComponent(text).withStyle(style);
        int color = buildARGB();

        ms.pushPose();
        ms.scale((float) previewScale, (float) previewScale, 1f);

        int scaledX = (int) (previewX / previewScale);
        int scaledY = (int) (previewY / previewScale);

        if (previewShadow) {
            font.drawShadow(ms, component, scaledX, scaledY, color);
        } else {
            font.draw(ms, component, scaledX, scaledY, color);
        }
        ms.popPose();
    }

    // --- Drag to reposition ---
    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0 && isOnPreview(mx, my)) {
            dragging = true;
            dragOffX = (int) mx - previewX;
            dragOffY = (int) my - previewY;
            return true;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        dragging = false;
        return super.mouseReleased(mx, my, button);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dx, double dy) {
        if (dragging) {
            previewX = Math.max(0, Math.min(width  - 80, (int) mx - dragOffX));
            previewY = Math.max(0, Math.min(height - 20, (int) my - dragOffY));
            return true;
        }
        return super.mouseDragged(mx, my, button, dx, dy);
    }

    private boolean isOnPreview(double mx, double my) {
        // Hit-test a small region around the current overlay position
        int w = (int)(font.width(previewPrefix + "120" + previewSuffix) * previewScale) + 4;
        int h = (int)(font.lineHeight * previewScale) + 4;
        return mx >= previewX - 2 && mx <= previewX + w &&
               my >= previewY - 2 && my <= previewY + h;
    }

    // --- Sync checkbox state (Forge CheckboxButton doesn't have a listener) ---
    private void sync() {
        if (cbBold    != null) previewBold    = cbBold.selected();
        if (cbItalic  != null) previewItalic  = cbItalic.selected();
        if (cbStrike  != null) previewStrike  = cbStrike.selected();
        if (cbUnder   != null) previewUnder   = cbUnder.selected();
        if (cbObfs    != null) previewObfs    = cbObfs.selected();
        if (cbShadow  != null) previewShadow  = cbShadow.selected();
        if (cbVisible != null) previewVisible = cbVisible.selected();
    }

    private int buildARGB() {
        return (previewA << 24) | (previewR << 16) | (previewG << 8) | previewB;
    }

    // --- Save all values back into Forge config ---
    private void saveToConfig() {
        sync();
        setInt(FPSConfig.POS_X,       previewX);
        setInt(FPSConfig.POS_Y,       previewY);
        setInt(FPSConfig.COLOR_R,     previewR);
        setInt(FPSConfig.COLOR_G,     previewG);
        setInt(FPSConfig.COLOR_B,     previewB);
        setInt(FPSConfig.COLOR_A,     previewA);
        FPSConfig.BOLD.set(previewBold);
        FPSConfig.ITALIC.set(previewItalic);
        FPSConfig.STRIKETHROUGH.set(previewStrike);
        FPSConfig.UNDERLINE.set(previewUnder);
        FPSConfig.OBFUSCATED.set(previewObfs);
        FPSConfig.SHADOW.set(previewShadow);
        FPSConfig.VISIBLE.set(previewVisible);
        FPSConfig.SCALE.set(previewScale);
        FPSConfig.PREFIX.set(previewPrefix);
        FPSConfig.SUFFIX.set(previewSuffix);
        FPSConfig.SPEC.save();
    }

    private void setInt(ForgeConfigSpec.IntValue cfg, int val) {
        cfg.set(Math.max(cfg.getMin().intValue(), Math.min(cfg.getMax().intValue(), val)));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
