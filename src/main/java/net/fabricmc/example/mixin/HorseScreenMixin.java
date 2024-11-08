package net.fabricmc.example.mixin;

import net.fabricmc.example.PlayerInfoTooltip;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseScreen.class)
public abstract class HorseScreenMixin extends HandledScreen<HorseScreenHandler> {


    public HorseScreenMixin(HorseScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void addPlayerTooltip(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (mouseX >= x+26 && mouseX <= x+26+52 && mouseY >= y+18 && mouseY <= y+18+52)
            context.drawTooltip(this.client.textRenderer, PlayerInfoTooltip.tooltip(this.client.player), mouseX, mouseY);
    }

}
