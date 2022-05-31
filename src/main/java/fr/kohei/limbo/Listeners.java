package fr.kohei.limbo;

import fr.kohei.common.api.CommonAPI;
import fr.kohei.common.cache.ProfileData;
import fr.kohei.limbo.queue.QueuePlayer;
import fr.kohei.limbo.utils.CC;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;

public class Listeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CommonAPI api = Limbo.getInstance().getApi();
        ProfileData profile = api.getProfile(event.getPlayer().getUniqueId());

        event.getPlayer().setAllowFlight(false);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage("");

        if (profile.getRank().getPermissionPower() > 100) {
            event.getPlayer().setOp(true);
            event.getPlayer().sendMessage(CC.translate("&6&lKOHEI &8» &fVous êtes autorisé à parler et éxectuer des commandes."));
        }

        QueuePlayer queuePlayer = new QueuePlayer(event.getPlayer().getUniqueId(), profile.getRank().getPermissionPower(), System.currentTimeMillis());
        Limbo.getInstance().getQueue().getQueue().add(queuePlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.getPlayer().isOp())
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Limbo.getInstance().getQueue().getQueue().remove(Limbo.getInstance().getQueue().getFromUUID(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp())
        event.setCancelled(true);
    }

}
