package com.exdrill.ce.client.model.item;

import com.exdrill.ce.Main;
import com.exdrill.ce.item.AmethystFluteItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmethystFluteModel extends AnimatedGeoModel<AmethystFluteItem> {


    @Override
    public Identifier getModelLocation(AmethystFluteItem object) {
        return new Identifier(Main.NAMESPACE, "geo/amethyst_flute.geo.json");
    }

    @Override
    public Identifier getTextureLocation(AmethystFluteItem object) {
        return new Identifier(Main.NAMESPACE, "textures/item/amethyst_flute.png");

    }

    @Override
    public Identifier getAnimationFileLocation(AmethystFluteItem animatable) {
        return new Identifier(Main.NAMESPACE, "animations/amethyst_flute.json");
    }
}
