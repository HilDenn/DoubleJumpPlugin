package me.hilden.doublejump;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;

public class Events implements Listener {

    private HashMap<Player, Long> jumpCooldown = new HashMap<>();
    private Long cooldown = 5000L;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setAllowFlight(true);
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        event.setCancelled(true);

        if (!jumpCooldown.containsKey(player)) {
            player.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
            jumpCooldown.put(player, System.currentTimeMillis());
        }

        if (System.currentTimeMillis() - jumpCooldown.get(player) >= cooldown) {
            player.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
            jumpCooldown.replace(player, System.currentTimeMillis());
        } else {
            player.sendMessage("Ждать - " + (cooldown - (System.currentTimeMillis() - jumpCooldown.get(player))));
        }



    }

}
