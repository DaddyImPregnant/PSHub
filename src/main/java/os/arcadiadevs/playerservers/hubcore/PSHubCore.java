package os.arcadiadevs.playerservers.hubcore;

import com.qrakn.honcho.Honcho;
import com.samjakob.spigui.SpiGUI;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import os.arcadiadevs.playerservers.hubcore.commands.ServersCommand;
import os.arcadiadevs.playerservers.hubcore.database.DataSource;
import os.arcadiadevs.playerservers.hubcore.listeners.ClickEvent;
import os.arcadiadevs.playerservers.hubcore.listeners.HubEvents;
import os.arcadiadevs.playerservers.hubcore.listeners.JoinEvent;
import os.arcadiadevs.playerservers.hubcore.placeholders.PlayerCount;

public class PSHubCore extends JavaPlugin {

    @Getter private static PSHubCore plugin;
    @Getter private static SpiGUI spiGUI;

    @SneakyThrows
    @Override
    public void onEnable() {

        plugin = this;
        spiGUI = new SpiGUI(this);

        Honcho honcho = new Honcho(this);
        honcho.registerCommand(new ServersCommand());


        getConfig().options().copyDefaults(true);
        saveConfig();

        DataSource ds = new DataSource();
        ds.registerDataSource();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListeneres here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            new PlayerCount(this).register();
        }

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HubEvents(), this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
