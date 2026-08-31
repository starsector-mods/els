package data.scripts.fx;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.FluxTrackerAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import data.util.ELSAnimationUtils;

import java.awt.Color;
import java.util.EnumSet;

public class FluxHeatVentEffect implements EveryFrameWeaponEffectPlugin {

    private static final Color COLOR = new Color(255, 125, 100, 25);
    private static final EnumSet<WeaponType> DECO = EnumSet.of(WeaponType.DECORATIVE);

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if (weapon == null || weapon.getShip() == null) return;
        ShipAPI ship = weapon.getShip();
        FluxTrackerAPI flux = ship.getFluxTracker();
        if (flux == null) return;

        float brightness = ELSAnimationUtils.map(0f, flux.getMaxFlux(), 0f, 25f, flux.getCurrFlux());
        ship.setWeaponGlow(brightness, COLOR, DECO);
    }
}
