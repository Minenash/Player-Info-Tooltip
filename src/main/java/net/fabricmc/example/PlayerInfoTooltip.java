package net.fabricmc.example;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.math.MathHelper.ceil;
import static net.minecraft.util.math.MathHelper.floor;

public class PlayerInfoTooltip {

    public static final Style ICONS = Style.EMPTY.withFont(Identifier.of("player-info-tooltip", "icons"));
    public static final Style DEFAULT = Style.EMPTY.withFont(Style.DEFAULT_FONT_ID);

    public static List<Text> tooltip(ClientPlayerEntity p) {
        int health = ceil(p.getHealth() + p.getAbsorptionAmount());
        int armor = p.getArmor();
        int hunger = p.getHungerManager().getFoodLevel();
        int sat = ceil(p.getHungerManager().getSaturationLevel());
        boolean pShift = health > 9 || armor > 9 || hunger > 9 || sat > 9;

        String air = Math.max(0, ceil(100D * p.getAir() / p.getMaxAir())) + "%";
        String xp = p.experienceLevel + "L " + floor(p.experienceProgress * p.getNextLevelExperience()) + " / " + p.getNextLevelExperience();

        LivingEntity v = p.getVehicle() instanceof LivingEntity le ? le : null;

        ArrayList<Text> lines = new ArrayList<>(9);
        lines.add( Text.literal("ⓘ ").append(p.getDisplayName()) );
        lines.add( line(pShift, "\uEFF1", health, ceil(p.getMaxHealth())) );
        lines.add( line(pShift, "\uEFF2", armor, 20) );
        lines.add( line(pShift, "\uEFF3", hunger, 20) );
        lines.add( line(pShift, "\uEFF4", sat, 20) );
        lines.add( Text.literal("") );
        lines.add( line("\uEFF5 ", air) );
        lines.add( line( "\uEFF6 ", xp) );

        if (v != null) {
            int vHealth = ceil(v.getHealth() + v.getAbsorptionAmount());
            boolean vShift = vHealth > 9 || v.getArmor() > 9;
            lines.add( Text.literal("") );
            lines.add( Text.literal("ⓘ ").append(v.getDisplayName()) );
            lines.add( line(vShift, "\uEFF7", vHealth, ceil( v.getMaxHealth() )) );
            lines.add( line(vShift, "\uEFF2", v.getArmor(), 11) );
        }

        return lines;
    }

    public static Text line(boolean shift, String icon, int value, int max) {
        return line(icon + (shift && value < 10 ? " \uEFFA" : " "), value + " / " + max);
    }

    public static Text line(String icon, String msg) {
        return Text.literal(icon).setStyle(ICONS).append(Text.literal(msg).setStyle(DEFAULT));
    }

}
