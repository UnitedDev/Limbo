package fr.kohei.limbo.queue;

import fr.kohei.limbo.Limbo;
import lombok.Getter;

import java.util.PriorityQueue;
import java.util.UUID;

@Getter
public class Queue {

    private final PriorityQueue<QueuePlayer> queue;
    private final Limbo plugin;

    public Queue(Limbo plugin) {
        this.plugin = plugin;
        queue = new PriorityQueue<>(new QueuePlayerComparator());

    }

    public QueuePlayer getFromUUID(UUID id) {
        return this.queue.stream().filter(queuePlayer -> queuePlayer.getPlayer() == id).findFirst().orElse(null);
    }

    public int getPosition(UUID uuid) {
        PriorityQueue<QueuePlayer> q = new PriorityQueue<>(this.queue);

        int position = 0;

        while (!q.isEmpty()) {
            QueuePlayer player = q.poll();

            if (player.getPlayer().equals(uuid)) {
                break;
            }

            position++;
        }

        return position + 1;
    }


}
