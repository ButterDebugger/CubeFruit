package dev.debutter.cubefruit.paper;

import dev.debutter.cubefruit.paper.utils.Caboodle;
import dev.debutter.cubefruit.paper.utils.DataStorage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InfoSaver implements Listener {

	public static void start() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Paper.plugin(), new Runnable() {
			@Override
			public void run() {
				DataStorage doubleData = Paper.plugin().getData("data.yml");

				for (World world : Bukkit.getWorlds()) {
					doubleData.set("worlds." + world.getName() + ".environment", world.getEnvironment().toString());
				}
			}
		}, 0, 1);
	}

	public static void end() {}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		DataStorage playerData = Paper.plugin().getData("players.yml");

		String username = player.getDisplayName().toString();
		if (playerData.exists(player.getUniqueId() + ".username")) {
			String oldUsername = playerData.getString(player.getUniqueId() + ".username");

			if (!oldUsername.equals(username)) {
				playerData.addToList(player.getUniqueId() + ".name-history", oldUsername, true);
			}
		}
		playerData.set(player.getUniqueId() + ".username", username);

		playerData.set(player.getUniqueId() + ".ipaddress", player.getAddress().getAddress().getHostAddress().toString());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		DataStorage playerData = Paper.plugin().getData("players.yml");

		playerData.set(player.getUniqueId() + ".logoutlocation", Caboodle.stringifyLocation(player));
	}
}
