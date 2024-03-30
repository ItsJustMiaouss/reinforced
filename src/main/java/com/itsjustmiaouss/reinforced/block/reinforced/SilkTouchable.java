package com.itsjustmiaouss.reinforced.block.reinforced;

import net.minecraft.block.Block;

/**
 * Represents a reinforced block that drops a specific
 * block when the breaker tool isn't enchanted with Silk Touch.
 */
public interface SilkTouchable {

    Block withoutSilkTouchDrop();

}
