package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

import java.util.Set;

public class AmInjector extends BaseHullMod {

    private static final Set<String> BLOCKED_MODS = Set.of(
            "unstable_injector",
            "augmentedengines",
            "safetyoverrides"
    );

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship == null || ship.getVariant() == null) return;
        for (String mod : BLOCKED_MODS) {
            if (ship.getVariant().getHullMods().contains(mod)) {
                ship.getVariant().removeMod(mod);
            }
        }
    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        if (ship == null || ship.getVariant() == null) return true;
        for (String mod : BLOCKED_MODS) {
            if (ship.getVariant().getHullMods().contains(mod)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        return "Incompatible with Augmented Engines, Safety Overrides, or Unstable Injector.";
    }
}
