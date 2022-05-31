package fr.kohei.limbo.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.kohei.limbo.Limbo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public final class BungeeUtil {

    public static void sendMessage(Player source, String target, String message) {

        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Message");
            out.writeUTF(target);
            out.writeUTF(message);

            source.sendPluginMessage(Limbo.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void kickPlayer(Player source, String target, String reason) {

        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("KickPlayer");
            out.writeUTF(target);
            out.writeUTF(reason);

            source.sendPluginMessage(Limbo.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendToServer(Player player, String server) {
        Bukkit.getScheduler().runTask(Limbo.getInstance(), () -> {
            try {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server);

                player.sendPluginMessage(Limbo.getInstance(), "BungeeCord", out.toByteArray());
                player.sendMessage(CC.SECONDARY + "You're now being sent to " + CC.PRIMARY + server + CC.SECONDARY + '.');
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
