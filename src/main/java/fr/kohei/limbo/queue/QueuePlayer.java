package fr.kohei.limbo.queue;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class QueuePlayer implements Comparable {

    public UUID player;
    public int rankPower;
    public long inserted;


    @Override
    public int compareTo(@NotNull Object object) {
        int result = 0;

        if (object instanceof QueuePlayer) {
            QueuePlayer otherPlayer = (QueuePlayer) object;
            result = this.rankPower - otherPlayer.rankPower;

            if (result == 0) {
                if (this.inserted < otherPlayer.getInserted()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        return result;
    }
}
