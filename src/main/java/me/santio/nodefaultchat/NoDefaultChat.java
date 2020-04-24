package me.santio.nodefaultchat;

import me.santio.nodefaultchat.util.PlayerManager;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ndc")
public class NoDefaultChat {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean ON_MINEHUT = false;
    private boolean ENABLED = false;

    public NoDefaultChat() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) { }

    private void doClientStuff(final FMLClientSetupEvent event) { }

    @SubscribeEvent
    public void onChatRecieved(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.getMessage().getFormattedText());
        if (message.endsWith(" or move to enable chat.")) {
            ON_MINEHUT = true;
            PlayerManager.send("Default chat is currently " + (ENABLED ? "disabled" : "enabled") + ". Toggle this by using -toggle");
        } else if (message.startsWith("Sending you to")) {
            ON_MINEHUT = false;
            PlayerManager.send("You have disconnected from Minehut lobbies.. Mod is now disabled.");
        } else {
            if (!ENABLED) return;
            if (!ON_MINEHUT) return;
            if (!message.startsWith("[")) {
                if (message.startsWith("From ") || message.startsWith("To ")) return; // Allow messages to appear.
                if (message.startsWith(" - ")) return; // Allows friend list to show up correctly.
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getOriginalMessage().startsWith("-toggle")) {
            event.setCanceled(true);
            ENABLED = !ENABLED;
            PlayerManager.send("Default chat is now " + (ENABLED ? "disabled" : "enabled"));
        }
    }
}
