package os.arcadiadevs.playerservers.hubcore.database.structures;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PingInfoStructure {

    private int online;
    private int max;
    private String MOTD;
}
