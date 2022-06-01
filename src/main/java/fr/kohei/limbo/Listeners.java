package fr.kohei.limbo;

import fr.kohei.BukkitAPI;
import fr.kohei.common.api.CommonAPI;
import fr.kohei.common.cache.ProfileData;
import fr.kohei.limbo.queue.QueuePlayer;
import fr.kohei.limbo.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class Listeners implements Listener {

    @EventHandler
    public void onJoinHide(PlayerJoinEvent event) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            ProfileData profile = BukkitAPI.getCommonAPI().getProfile(player.getUniqueId());
            if (profile.getRank().getPermissionPower() < 30) {
                event.getPlayer().hidePlayer(player);
            }
            ProfileData target = BukkitAPI.getCommonAPI().getProfile(event.getPlayer().getUniqueId());
            if (target.getRank().getPermissionPower() < 30) {
                player.hidePlayer(event.getPlayer());
            }
        });
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 8.5, 102, 8.5, 180, 0));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        ProfileData profile = BukkitAPI.getCommonAPI().getProfile(event.getPlayer().getUniqueId());

        event.getPlayer().setAllowFlight(false);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage(null);

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
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void onDamageByOthers(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Limbo.getInstance().getQueue().getQueue().remove(Limbo.getInstance().getQueue().getFromUUID(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp())
        event.setCancelled(true);
    }

}
