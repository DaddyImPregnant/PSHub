package os.arcadiadevs.playerservers.hubcore.listeners;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import os.arcadiadevs.playerservers.hubcore.PSHubCore;
import os.arcadiadevs.playerservers.hubcore.utils.ColorUtils;

import java.util.ArrayList;

public class JoinEvent implements Listener {

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (PSHubCore.getPlugin().getConfig().getBoolean("enable-compass")) {
            Bukkit.getScheduler().runTaskAsynchronously(PSHubCore.getPlugin(), () -> {

                ItemStack is = new ItemStack(XMaterial.COMPASS.parseMaterial());
                ItemMeta im = is.getItemMeta();

                im.setDisplayName(ColorUtils.translate(PSHubCore.getPlugin().getConfig().getString("compass-name")));

                ArrayList<String> lore = new ArrayList<>();
                PSHubCore.getPlugin().getConfig().getStringList("compass-description").forEach(string -> lore.add(ColorUtils.translate(string)));
                im.setLore(lore);
                is.setItemMeta(im);

                Bukkit.getScheduler().runTask(PSHubCore.getPlugin(), () -> p.getInventory().setItem(PSHubCore.getPlugin().getConfig().getInt("compass-location"), is));

            }); 
        } else {
            p.getInventory().forEach(itemStack -> {
                if (itemStack.getType() == XMaterial.COMPASS.parseMaterial())
                    p.getInventory().remove(itemStack);
            });
        }
    }


}
