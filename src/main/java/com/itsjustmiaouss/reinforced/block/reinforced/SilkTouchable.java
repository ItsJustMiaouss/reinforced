package com.itsjustmiaouss.reinforced.block.reinforced;

import net.minecraft.block.Block;

/**
 * Represents a reinforced block that drops a specific
 * item when the used tool isn't enchanted with Silk Touch.
 */
public interface SilkTouchable {

    Block withoutSilkTouchDrop();

}
