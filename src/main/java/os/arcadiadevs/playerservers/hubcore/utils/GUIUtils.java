package os.arcadiadevs.playerservers.hubcore.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import os.arcadiadevs.playerservers.hubcore.PSHubCore;
import os.arcadiadevs.playerservers.hubcore.database.DataBase;
import os.arcadiadevs.playerservers.hubcore.database.structures.DBInfoStructure;
import os.arcadiadevs.playerservers.hubcore.database.structures.PingInfoStructure;

import java.util.ArrayList;
import java.util.List;

public class GUIUtils {

    public void openSelector(Player player) {
        DataBase db = new DataBase();
        PingUtil pu = new PingUtil();

        SGMenu menu = PSHubCore.getSpiGUI().create("&cActive servers", 3);

        List<SGButton> buttons = new ArrayList<>();

        Bukkit.getScheduler().runTaskAsynchronously(PSHubCore.getPlugin(), () -> {
            for (DBInfoStructure is : db.getServersInfo()) {
                if (pu.isOnline("127.0.0.1", is.getPort())) {

                    PingInfoStructure pus = pu.getData(Integer.parseInt(is.getPort()));

                    SGButton server = new SGButton(new ItemBuilder(Material.SKULL_ITEM).skullOwner(is.getPlayerName()).name(is.getPlayerName() + "'s server")
                            .lore("&cPort: &7" + is.getPort(),
                                    "&cUUID: &7" + is.getServerID().split("-")[0],
                                    String.format("&cOnline: &7%d/%d", pus.getOnline(), pus.getMax()),
                                    "&cMOTD: &7" + pus.getMOTD()).build()).withListener((InventoryClickEvent event) -> {

                        Player user = (Player) event.getWhoClicked();
                        String UUID = event.getCurrentItem().getItemMeta().getLore().get(1).split(" ")[1].replaceAll("§7", "");
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                        out.writeUTF("Connect");
                        out.writeUTF(UUID);

                        user.sendPluginMessage(PSHubCore.getPlugin(), "BungeeCord", out.toByteArray());
                        event.setCancelled(true);
                        event.getWhoClicked().closeInventory();
                    });
                    buttons.add(server);
                }
                buttons.forEach(menu::addButton);
                Bukkit.getScheduler().runTask(PSHubCore.getPlugin(), () -> player.openInventory(menu.getInventory()));
            }
        });
    }
}