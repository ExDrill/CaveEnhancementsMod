package com.exdrill.ce.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static final Identifier GLOW_PASTE_PLACE_SOUND = new Identifier("ce:item.glow_paste.place");
    public static SoundEvent GLOW_PASTE_PLACE_SOUND_EVENT = new SoundEvent(GLOW_PASTE_PLACE_SOUND);

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, GLOW_PASTE_PLACE_SOUND, GLOW_PASTE_PLACE_SOUND_EVENT);
    }
}
