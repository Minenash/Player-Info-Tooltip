package net.fabricmc.example;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class PlayerInfoTooltip {

    public static final Style ICONS = Style.EMPTY.withFont(Identifier.of("player-info-tooltip", "icons"));
    public static final Style DEFAULT = Style.EMPTY.withFont(Style.DEFAULT_FONT_ID);

    public static List<Text> tooltip(ClientPlayerEntity p) {
        int health = MathHelper.ceil(p.getHealth() + p.getAbsorptionAmount());
        int armor = p.getArmor();
        int hunger = p.getHungerManager().getFoodLevel();
        int sat = MathHelper.ceil(p.getHungerManager().getSaturationLevel());
        boolean shift = health > 9 || armor > 9 || hunger > 9 || sat > 9;

        return List.of( Text.literal("ⓘ ").append(p.getDisplayName()),
                line(shift, "\uEFF1", health, MathHelper.ceil(p.getMaxHealth()) ),
                line(shift, "\uEFF2", armor, 20 ),
                line(shift, "\uEFF3", hunger, 20 ),
                line(shift, "\uEFF4", sat, 20) );
    }

    public static Text line(boolean shift, String icon, int value, int max) {
        return Text.literal(icon + (shift && value < 10 ? " \uEFF5" : " ")).setStyle(ICONS).append(Text.literal(value + " / " + max).setStyle(DEFAULT));
    }

}
