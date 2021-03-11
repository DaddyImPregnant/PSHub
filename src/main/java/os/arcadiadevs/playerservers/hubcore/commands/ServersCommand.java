package os.arcadiadevs.playerservers.hubcore.commands;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;
import os.arcadiadevs.playerservers.hubcore.utils.GUIUtils;

@CommandMeta(label = "servers")
public class ServersCommand {

    public void execute(Player player){
        new GUIUtils().openSelector(player);
    }
}
