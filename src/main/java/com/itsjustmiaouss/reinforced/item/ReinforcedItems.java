package com.itsjustmiaouss.reinforced.item;

import com.itsjustmiaouss.reinforced.Reinforced;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ReinforcedItems {

    public static final Item REINFORCER_ITEM = registerItem(
            "reinforcer",
            new ReinforcerItem(new FabricItemSettings().maxCount(1).maxDamage(1000))
    );

    private static Item registerItem(String identifier, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Reinforced.MOD_ID, identifier), item);
    }

    public static void register() {
        Reinforced.LOGGER.info("Registered items");
    }

}
