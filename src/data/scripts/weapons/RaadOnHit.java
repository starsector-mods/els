package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class RaadOnHit implements OnHitEffectPlugin {

    private static final Color CORE_COLOR = new Color(255, 255, 255, 255);
    private static final Color FRINGE_COLOR = new Color(255, 100, 235, 215);
    private static final float ARC_WIDTH = 20f;
    private static final float ARC_RANGE = 100000f;
    private static final String SFX = "tachyon_lance_emp_impact";

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine) {

        if (shieldHit && target instanceof ShipAPI) {
            float emp = projectile.getEmpAmount();
            float dam = projectile.getDamageAmount() * 0.25f;

            engine.spawnEmpArcPierceShields(
                    projectile.getSource(),
                    point, target, target,
                    DamageType.ENERGY,
                    dam,
                    emp,
                    ARC_RANGE,
                    SFX,
                    ARC_WIDTH,
                    CORE_COLOR,
                    FRINGE_COLOR
            );
        } else if (target instanceof ShipAPI) {
            engine.spawnEmpArc(
                    projectile.getSource(),
                    point, target, target,
                    DamageType.ENERGY,
                    0f,
                    0f,
                    ARC_RANGE,
                    SFX,
                    ARC_WIDTH,
                    CORE_COLOR,
                    FRINGE_COLOR
            );
        }
    }
}
