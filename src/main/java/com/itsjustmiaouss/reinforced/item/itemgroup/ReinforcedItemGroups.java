package com.itsjustmiaouss.reinforced.item.itemgroup;

import com.itsjustmiaouss.reinforced.Reinforced;
import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import com.itsjustmiaouss.reinforced.item.ReinforcedItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ReinforcedItemGroups {

    public static final ItemGroup REINFORCED_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ReinforcedBlocks.REINFORCED_STONE))
            .displayName(Text.translatable("itemGroup.reinforced.reinforced"))
            .entries((displayContext, entries) -> {
                entries.add(ReinforcedItems.REINFORCER_ITEM);
                entries.add(ReinforcedBlocks.REINFORCED_STONE);
                entries.add(ReinforcedBlocks.REINFORCED_STONE_BRICKS);
                entries.add(ReinforcedBlocks.REINFORCED_DEEPSLATE_BRICKS);
            })
            .build();

    public static ItemGroup register() {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(Reinforced.MOD_ID, "reinforced_group"), REINFORCED_ITEM_GROUP);
    }
}
