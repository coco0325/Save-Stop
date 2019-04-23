package me.coco0325;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SaveStop extends JavaPlugin implements Listener {

    static int id;
    static  Integer delay;

    @EventHandler
    public  void onPlayerQuit(PlayerQuitEvent e){
        if(getServer().getOnlinePlayers().size() <= 1){
            shutdown();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(getServer().getScheduler().isQueued(id)){
            getServer().getScheduler().cancelTask(id);
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        delay = getConfig().getInt("delay");
        this.getServer().getPluginManager().registerEvents(this, this);
        if(getConfig().getBoolean("direct-start")){
            shutdown();
        }
    }

    @Override
    public void onDisable() {
    }

    private void shutdown(){
        id = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        getServer().shutdown();
                    }
                },delay*20
        );
    }
}
