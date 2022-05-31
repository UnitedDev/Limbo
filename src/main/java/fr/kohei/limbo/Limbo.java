package fr.kohei.limbo;

import fr.kohei.BukkitAPI;
import fr.kohei.common.api.CommonAPI;
import fr.kohei.limbo.queue.Queue;
import fr.kohei.limbo.queue.QueueThread;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
@Getter
public final class Limbo extends JavaPlugin {

    @Getter
    private static Limbo Instance;

    private Queue queue;
    private QueueThread queueThread;
    private CommonAPI api;



    @Override
    public void onEnable() {
        Instance = this;
        this.api = BukkitAPI.getCommonAPI();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);

        this.queue = new Queue(this);
        this.queueThread = new QueueThread(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
