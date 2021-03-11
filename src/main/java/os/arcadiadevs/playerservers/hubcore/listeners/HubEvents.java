package os.arcadiadevs.playerservers.hubcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import os.arcadiadevs.playerservers.hubcore.PSHubCore;

public class HubEvents implements Listener {

    @EventHandler
    public void weatherChange(WeatherChangeEvent e) {
        if (PSHubCore.getPlugin().getConfig().getBoolean("disable-weather")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && PSHubCore.getPlugin().getConfig().getBoolean("disable-damage"))
            e.setCancelled(true);
    }

}
