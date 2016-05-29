import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by moguno on 2016/05/29.
 */
public class CouponConfig
{
    String couponCode;
    String itemDescription;
    ArrayList<ItemStack> items;

    public CouponConfig()
    {
        items = new ArrayList<ItemStack>();
    }
};

