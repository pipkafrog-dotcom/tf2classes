package com.tf2classes;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class ClassMenuScreen extends Screen {
    public ClassMenuScreen() {
        super(Text.literal("Выбор класса"));
    }

    @Override
    protected void init() {
        TfClass[] all = TfClass.values();
        int w = 110, h = 20, gap = 6, cols = 3;
        int startX = width / 2 - (cols * w + (cols - 1) * gap) / 2;
        int startY = height / 2 - 40;
        for (int i = 0; i < all.length; i++) {
            final int idx = i;
            int x = startX + (i % cols) * (w + gap);
            int y = startY + (i / cols) * (h + gap);
            addDrawableChild(ButtonWidget.builder(Text.literal(all[i].displayName), b -> choose(idx))
                    .dimensions(x, y, w, h).build());
        }
    }

    private void choose(int idx) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(idx);
        ClientPlayNetworking.send(Tf2Classes.SET_CLASS, buf);
        close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 70, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
