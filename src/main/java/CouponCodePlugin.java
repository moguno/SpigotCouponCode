import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by moguno on 2016/05/29.
 */
public class CouponCodePlugin extends JavaPlugin implements Listener
{
    protected ArrayList<CouponConfig> coupons;

    @Override
    public void onEnable()
    {
        FileConfiguration config = getConfig();

        coupons = new ArrayList<CouponConfig>() {
            {
                add(new CouponConfig() {
                    {
                        couponCode = "WELCOME";

                        itemDescription = "一生モノツール詰め合わせ";

                        items.add(new ItemStack(Material.IRON_PICKAXE) {
                            {
                                addEnchantment(Enchantment.DURABILITY, 3);
                                addEnchantment(Enchantment.MENDING, 1);

                                ItemMeta meta = getItemMeta();
                                meta.setDisplayName("硬ったいピッケル");
                                setItemMeta(meta);
                            }
                        });

                        items.add(new ItemStack(Material.IRON_AXE) {
                            {
                                addEnchantment(Enchantment.DURABILITY, 3);
                                addEnchantment(Enchantment.MENDING, 1);

                                ItemMeta meta = getItemMeta();
                                meta.setDisplayName("硬ったいオノ");
                                setItemMeta(meta);
                            }
                        });

                        items.add(new ItemStack(Material.IRON_HOE) {
                            {
                                addEnchantment(Enchantment.DURABILITY, 3);
                                addEnchantment(Enchantment.MENDING, 1);

                                ItemMeta meta = getItemMeta();
                                meta.setDisplayName("硬ったいクワ");
                                setItemMeta(meta);
                            }
                        });

                        items.add(new ItemStack(Material.IRON_SPADE) {
                            {
                                addEnchantment(Enchantment.DURABILITY, 3);
                                addEnchantment(Enchantment.MENDING, 1);

                                ItemMeta meta = getItemMeta();
                                meta.setDisplayName("硬ったいスコップ");
                                setItemMeta(meta);
                            }
                        });
                    }
                });
            }
        };

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        for (CouponConfig coupon : coupons) {
            if (event.getMessage().endsWith(coupon.couponCode)) {
                List<String> users = getConfig().getStringList("paid." + coupon.couponCode);

                if (users == null || !users.contains(event.getPlayer().getName())) {
                    for(ItemStack item :coupon.items) {
                        event.getPlayer().getInventory().addItem(item);
                    }

                    users.add(event.getPlayer().getName());
                    getConfig().set("paid." + coupon.couponCode, users);
                    saveConfig();

                    event.getPlayer().sendMessage("クーポン[" + coupon.couponCode + "]を適用しました。");
                    event.getPlayer().sendMessage("[" + coupon.itemDescription + "]をプレゼント！");
                }
                else
                {
                    event.getPlayer().sendMessage("クーポン[" + coupon.couponCode + "]はすでに使用済みです。");
                }

                event.setCancelled(true);
            }
        }
    }
}
