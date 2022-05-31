package fr.kohei.limbo.queue;

import fr.kohei.BukkitAPI;
import fr.kohei.common.api.CommonAPI;
import fr.kohei.limbo.Limbo;
import fr.kohei.limbo.utils.BungeeUtil;
import fr.kohei.limbo.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QueueThread extends Thread {

    private Limbo plugin;

    public QueueThread(Limbo plugin) {
        super("Limbo - Queue Thread");

        this.plugin = plugin;
        this.setDaemon(false);

        this.loadMessages();
        Bukkit.getScheduler().runTaskLater(plugin, this::start, 20);
    }

    public void loadMessages() {

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                UUID id = player.getUniqueId();
                Queue queue = Limbo.getInstance().getQueue();
                Title.sendActionBar(player, "&6Position: &c" + queue.getPosition(id) + "&7/&f" + queue.getQueue().size());
            });
        },  20, 20);

    }

    @Override
    public void run() {
        while (true) {

            QueuePlayer player = Limbo.getInstance().getQueue().getQueue().peek();

            try {
                if (Bukkit.getPlayer(player.getPlayer()) != null) {
                    sendToLobby(player.getPlayer());
                }
                sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendToLobby(UUID id) {
        if (BukkitAPI.getServerCache().findBestLobby() == null) return;

        Player player = Bukkit.getPlayer(id);
        if (player == null) return;

        String serverName = BukkitAPI.getFactory(BukkitAPI.getServerCache().findBestLobbyFor(id).getPort()).getName();

        BungeeUtil.sendToServer(player, serverName);

    }
}
