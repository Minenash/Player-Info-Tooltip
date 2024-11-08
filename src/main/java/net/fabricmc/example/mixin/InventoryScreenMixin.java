package net.fabricmc.example.mixin;

import net.fabricmc.example.PlayerInfoTooltip;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends RecipeBookScreen<PlayerScreenHandler> {
	public InventoryScreenMixin(PlayerScreenHandler handler, RecipeBookWidget<?> recipeBook, PlayerInventory inventory, Text title) {
		super(handler, recipeBook, inventory, title);
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void addPlayerTooltip(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (mouseX >= x+26 && mouseX <= x+26+49 && mouseY >= y+8 && mouseY <= y+8+70)
			context.drawTooltip(this.client.textRenderer, PlayerInfoTooltip.tooltip(this.client.player), mouseX, mouseY);
	}
}
