package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static final Identifier GLOW_PASTE_PLACE_SOUND = new Identifier(CaveEnhancements.NAMESPACE,"item.glow_paste.place");
    public static SoundEvent GLOW_PASTE_PLACE_SOUND_EVENT = new SoundEvent(GLOW_PASTE_PLACE_SOUND);

    public static final Identifier BLOCK_ROSE_QUARTZ_BREAK_ID = new Identifier(CaveEnhancements.NAMESPACE, "block.rose_quartz.break");
    public static SoundEvent BLOCK_ROSE_QUARTZ_BREAK = new SoundEvent(BLOCK_ROSE_QUARTZ_BREAK_ID);

    public static final BlockSoundGroup ROSE_QUARTZ  = new BlockSoundGroup(1.0F, 1.0F, BLOCK_ROSE_QUARTZ_BREAK, SoundEvents.BLOCK_GLASS_STEP, SoundEvents.BLOCK_CALCITE_PLACE, SoundEvents.BLOCK_GLASS_HIT, SoundEvents.BLOCK_GLASS_FALL);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, GLOW_PASTE_PLACE_SOUND, GLOW_PASTE_PLACE_SOUND_EVENT);
        Registry.register(Registry.SOUND_EVENT, BLOCK_ROSE_QUARTZ_BREAK_ID, BLOCK_ROSE_QUARTZ_BREAK);
    }


}
