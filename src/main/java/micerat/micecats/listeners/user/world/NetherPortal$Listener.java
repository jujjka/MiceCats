package micerat.micecats.listeners.user.world;

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

public class NetherPortal$Listener implements Listener {
    @EventHandler
    public void portal(PlayerPortalEvent portalEvent){
        if(!MiceCats.Is_Game){return;}
        World world = portalEvent.getTo().getWorld();
        if(portalEvent.getFrom().getWorld().getName().equals(new WorldUtil().nether_world_name())){
        if(world.getEnvironment() != World.Environment.NORMAL){return;}
        Player user = portalEvent.getPlayer();
        if(GamePlayer.getGame_player(user) == null){return;}
        GamePlayer gP = GamePlayer.getGame_player(user);
        Location location = portalEvent.getFrom();
        int x = location.blockX() * 8;
        int y = location.getBlockY();
        int z = location.getBlockZ() * 8;
        portalEvent.setCanCreatePortal(true);
        portalEvent.setTo(Bukkit.getWorld(new WorldUtil().world_name()).getBlockAt(x,y,z).getLocation());
    }
}
}
