package micerat.micecats.listeners.user.world;

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent;
import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.utilis.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EndPortal$Listener2 implements Listener {
    @EventHandler
    public void portal(PlayerPortalEvent portalEvent) {
        if(!MiceCats.Is_Game){return;}
        World world = portalEvent.getTo().getWorld();
        if(!portalEvent.getFrom().getWorld().getName().equals(new WorldUtil().world_name()) && !portalEvent.getFrom().getWorld().getName().equals(new WorldUtil().nether_world_name())){return;}
        if (world.getEnvironment() != World.Environment.THE_END) {return;}
        Player user = portalEvent.getPlayer();
        if (GamePlayer.getGame_player(user) == null) {return;}
        portalEvent.setCanCreatePortal(true);
        portalEvent.setTo(Bukkit.getWorld(new WorldUtil().end_world_name()).getBlockAt(100,50,0).getLocation());
    }
    @EventHandler
    public void gateway(PlayerTeleportEvent teleportEvent){
        if(!MiceCats.Is_Game){return;}
        World world = teleportEvent.getFrom().getWorld();
        if(world.getName().equals(new WorldUtil().end_world_name())) {
            teleportEvent.setTo(Bukkit.getWorld(new WorldUtil().world_name()).getSpawnLocation());
        }
    }
    @EventHandler
    public void gateway(PlayerTeleportEndGatewayEvent teleportEvent){
        if(!MiceCats.Is_Game){return;}
        World world = teleportEvent.getFrom().getWorld();
        if(world.getName().equals(new WorldUtil().end_world_name())) {
            teleportEvent.setTo(Bukkit.getWorld(new WorldUtil().world_name()).getSpawnLocation());
        }
    }
    @EventHandler
    public void portal1(PlayerPortalEvent portalEvent) {
        if(!MiceCats.Is_Game){return;}
        if(portalEvent.getFrom().getWorld().getName().equals(new WorldUtil().end_world_name())) {
            portalEvent.setTo(Bukkit.getWorld(new WorldUtil().world_name()).getSpawnLocation());
        }
    }
}
