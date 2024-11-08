package net.fabricmc.example.mixin;

import net.fabricmc.example.PlayerInfoTooltip;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends HandledScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    @Shadow private static ItemGroup selectedTab;

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void addPlayerTooltip(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (selectedTab.getType() == ItemGroup.Type.INVENTORY && mouseX >= x+73 && mouseX <= x+73+32 && mouseY >= y+6 && mouseY <= y+6+43)
            context.drawTooltip(this.client.textRenderer, PlayerInfoTooltip.tooltip(this.client.player), mouseX, mouseY);
    }
}
