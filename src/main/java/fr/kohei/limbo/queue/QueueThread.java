package fr.kohei.limbo.queue;

import fr.kohei.BukkitAPI;
import fr.kohei.limbo.Limbo;
import fr.kohei.limbo.utils.BungeeUtil;
import fr.kohei.limbo.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QueueThread {
    private final Limbo plugin;

    public QueueThread(Limbo plugin) {

        this.plugin = plugin;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        this.loadMessages();
        executor.scheduleAtFixedRate(this.run(), 1000, 250, TimeUnit.MILLISECONDS);
    }

    public void loadMessages() {

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                UUID id = player.getUniqueId();
                Queue queue = Limbo.getInstance().getQueue();
                Title.sendActionBar(player, "&cPosition &8» &f" + queue.getPosition(id) + "&8/&7" + queue.getQueue().size());
            });
        },  20, 5);

    }

    public Runnable run() {
        return () -> {
            try {
                QueuePlayer player = Limbo.getInstance().getQueue().getQueue().peek();
                if (Bukkit.getPlayer(player.getPlayer()) != null) {
                    sendToLobby(player.getPlayer());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

    public void sendToLobby(UUID id) {
        if (BukkitAPI.getCommonAPI().getServerCache().findBestLobby() == null) {
            return;
        }

        Player player = Bukkit.getPlayer(id);
        if (player == null) return;

        String serverName = BukkitAPI.getFactory(BukkitAPI.getCommonAPI().getServerCache().findBestLobbyFor(id).getPort()).getName();
        BungeeUtil.sendToServer(player, serverName);
    }
}
