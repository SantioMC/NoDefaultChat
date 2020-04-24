package me.santio.nodefaultchat;

import me.santio.nodefaultchat.util.PlayerManager;
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
        if (event.getMessage().getSiblings().get(0).getFormattedText().contains(" or move to enable chat.")) {
            ON_MINEHUT = true;
            PlayerManager.send("Default chat is currently " + (ENABLED ? "disabled" : "enabled") + ". Toggle this by using -toggle");
        } else if (event.getMessage().getSiblings().get(0).getFormattedText().contains("Sending you")) {
            ON_MINEHUT = false;
            PlayerManager.send("You have disconnected from Minehut lobbies.. Mod is now disabled.");
        } else {
            String name = event.getMessage().getSiblings().get(0).getFormattedText();
            if (!ENABLED) return;
            if (!ON_MINEHUT) return;
            if (!name.contains("[")) {
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
