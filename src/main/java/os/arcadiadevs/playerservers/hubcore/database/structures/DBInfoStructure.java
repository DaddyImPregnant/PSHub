package os.arcadiadevs.playerservers.hubcore.database.structures;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DBInfoStructure {

    private String UUID;
    private String ServerID;
    private String Port;
    private String ServerName;
    private String PlayerName;
}
