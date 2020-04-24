package me.santio.nodefaultchat.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

public class PlayerManager {

    private static final ClientPlayerEntity PLAYER = Minecraft.getInstance().player;
    public static void send(String message) {
        if (PLAYER == null) return;
        PLAYER.sendMessage(new StringTextComponent(ChatFormatting.DARK_GRAY+"[" + ChatFormatting.DARK_AQUA + "NoDefaultChat" + ChatFormatting.DARK_GRAY + "] " + ChatFormatting.GRAY + message));
    }
    public static void say(String message) {
        if (PLAYER == null) return;
        PLAYER.sendChatMessage(message);
    }
    public static void run(String command) {
        if (PLAYER == null) return;
        PLAYER.sendChatMessage("/"+command);
    }
    public static UUID getUUID() { if (PLAYER != null) { return PLAYER.getUniqueID(); } return null; }
    public static String getName() { if (PLAYER != null) { return PLAYER.getName().getFormattedText(); } return null; }
}
