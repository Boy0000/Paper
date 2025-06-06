package com.destroystokyo.paper.event.player;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import static org.bukkit.Material.*;

/**
 * Called when the player themselves change their armor items
 * <p>
 * Not currently called for environmental factors though it <strong>MAY BE IN THE FUTURE</strong>
 * @apiNote Use {@link io.papermc.paper.event.entity.EntityEquipmentChangedEvent} for all entity equipment changes
 */
@NullMarked
@ApiStatus.Obsolete(since = "1.21.4")
public class PlayerArmorChangeEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final SlotType slotType;
    private final ItemStack oldItem;
    private final ItemStack newItem;

    @ApiStatus.Internal
    public PlayerArmorChangeEvent(final Player player, final SlotType slotType, final ItemStack oldItem, final ItemStack newItem) {
        super(player);
        this.slotType = slotType;
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    /**
     * Gets the type of slot being altered.
     *
     * @return type of slot being altered
     * @deprecated {@link SlotType} does not accurately represent what item types are valid in each slot. Use {@link #getSlot()} instead.
     */
    @Deprecated(since = "1.21.4")
    public SlotType getSlotType() {
        return this.slotType;
    }

    /**
     * Gets the slot being altered.
     *
     * @return slot being altered
     */
    public EquipmentSlot getSlot() {
        return switch (this.slotType) {
            case HEAD -> EquipmentSlot.HEAD;
            case CHEST -> EquipmentSlot.CHEST;
            case LEGS -> EquipmentSlot.LEGS;
            case FEET -> EquipmentSlot.FEET;
        };
    }

    /**
     * Gets the existing item that's being replaced
     *
     * @return old item
     */
    public ItemStack getOldItem() {
        return this.oldItem;
    }

    /**
     * Gets the new item that's replacing the old
     *
     * @return new item
     */
    public ItemStack getNewItem() {
        return this.newItem;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * @deprecated {@link SlotType} does not accurately represent what item types are valid in each slot.
     */
    @Deprecated(since = "1.21.4")
    public enum SlotType {
        HEAD(NETHERITE_HELMET, DIAMOND_HELMET, GOLDEN_HELMET, IRON_HELMET, CHAINMAIL_HELMET, LEATHER_HELMET, CARVED_PUMPKIN, PLAYER_HEAD, SKELETON_SKULL, ZOMBIE_HEAD, CREEPER_HEAD, WITHER_SKELETON_SKULL, TURTLE_HELMET, DRAGON_HEAD, PIGLIN_HEAD),
        CHEST(NETHERITE_CHESTPLATE, DIAMOND_CHESTPLATE, GOLDEN_CHESTPLATE, IRON_CHESTPLATE, CHAINMAIL_CHESTPLATE, LEATHER_CHESTPLATE, ELYTRA),
        LEGS(NETHERITE_LEGGINGS, DIAMOND_LEGGINGS, GOLDEN_LEGGINGS, IRON_LEGGINGS, CHAINMAIL_LEGGINGS, LEATHER_LEGGINGS),
        FEET(NETHERITE_BOOTS, DIAMOND_BOOTS, GOLDEN_BOOTS, IRON_BOOTS, CHAINMAIL_BOOTS, LEATHER_BOOTS);

        private final Set<Material> types;

        SlotType(final Material... types) {
            this.types = Set.of(types);
        }

        /**
         * Gets an immutable set of all allowed material types that can be placed in an
         * armor slot.
         *
         * @return immutable set of material types
         */
        public Set<Material> getTypes() {
            return this.types;
        }

        /**
         * Gets the type of slot via the specified material
         *
         * @param material material to get slot by
         * @return slot type the material will go in, or {@code null} if it won't
         */
        public static @Nullable SlotType getByMaterial(final Material material) {
            for (final SlotType slotType : values()) {
                if (slotType.getTypes().contains(material)) {
                    return slotType;
                }
            }
            return null;
        }

        /**
         * Gets whether this material can be equipped to a slot
         *
         * @param material material to check
         * @return whether this material can be equipped
         */
        public static boolean isEquipable(final Material material) {
            return getByMaterial(material) != null;
        }
    }
}
