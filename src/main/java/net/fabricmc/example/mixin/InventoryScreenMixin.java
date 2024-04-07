package net.fabricmc.example.mixin;

import net.fabricmc.example.PlayerInfoTooltip;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {

	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void addPlayerTooltip(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (mouseX >= x+26 && mouseX <= x+26+49 && mouseY >= y+8 && mouseY <= y+8+70)
			renderTooltip(matrices, PlayerInfoTooltip.tooltip(client.player), mouseX, mouseY);
	}
}
