package com.itsjustmiaouss.reinforced;

import com.itsjustmiaouss.reinforced.block.ReinforcedBlocks;
import com.itsjustmiaouss.reinforced.block.entity.ReinforcedBlockEntities;
import com.itsjustmiaouss.reinforced.event.AttackBlockEvent;
import com.itsjustmiaouss.reinforced.item.ReinforcedItems;
import com.itsjustmiaouss.reinforced.item.itemgroup.ReinforcedItemGroups;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reinforced implements ModInitializer {

	public static final String MOD_ID = "reinforced";
    public static final Logger LOGGER = LoggerFactory.getLogger(Reinforced.MOD_ID);

	@Override
	public void onInitialize() {
		ReinforcedItems.register();
		ReinforcedBlocks.register();
		ReinforcedBlockEntities.register();
		ReinforcedItemGroups.register();

		AttackBlockCallback.EVENT.register(new AttackBlockEvent());
	}
}