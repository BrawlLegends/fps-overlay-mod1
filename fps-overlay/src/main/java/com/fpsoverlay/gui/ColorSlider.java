package com.fpsoverlay.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.IntConsumer;

/**
 * A simple integer-range slider with a label.
 * Range [min, max] → integer output via consumer.
 */
public class ColorSlider extends AbstractSlider {

    private final String label;
    private final int min;
    private final int max;
    private final IntConsumer onChange;

    public ColorSlider(int x, int y, int width, int height,
                       String label, int initialValue, int min, int max,
                       IntConsumer onChange) {
        super(x, y, width, height, new StringTextComponent(""), 0);
        this.label = label;
        this.min = min;
        this.max = max;
        this.onChange = onChange;
        // Map initial value → [0,1]
        this.value = (double)(initialValue - min) / (max - min);
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        int intVal = getIntValue();
        setMessage(new StringTextComponent(label + ": " + intVal));
    }

    @Override
    protected void applyValue() {
        onChange.accept(getIntValue());
    }

    public int getIntValue() {
        return (int) MathHelper.lerp(this.value, min, max);
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTick) {
        super.render(ms, mouseX, mouseY, partialTick);
    }
}
