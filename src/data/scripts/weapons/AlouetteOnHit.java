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

public class AlouetteOnHit implements OnHitEffectPlugin {

    private static final Color WHITE = new Color(255, 255, 255, 255);
    private static final Color FRINGE_COLOR = new Color(100, 165, 255, 215);
    private static final float MAX_ARC_RANGE = 10000f;
    private static final float FIRST_ARC_CHANCE = 0.75f;
    private static final float SECOND_ARC_CHANCE = 0.5f;

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine) {

        if (target instanceof ShipAPI && !shieldHit) {
            double roll = Math.random();
            if (roll < FIRST_ARC_CHANCE) {
                float emp = projectile.getEmpAmount();
                float dam = projectile.getDamageAmount() * 0.25f;

                engine.spawnEmpArc(
                        projectile.getSource(),
                        point,
                        target,
                        target,
                        DamageType.ENERGY,
                        dam,
                        emp,
                        MAX_ARC_RANGE,
                        "tachyon_lance_emp_impact",
                        20f,
                        FRINGE_COLOR,
                        WHITE
                );

                if (roll < SECOND_ARC_CHANCE) {
                    engine.spawnEmpArc(
                            projectile.getSource(),
                            point,
                            target,
                            target,
                            DamageType.ENERGY,
                            dam,
                            emp,
                            MAX_ARC_RANGE,
                            "tachyon_lance_emp_impact",
                            20f,
                            FRINGE_COLOR,
                            WHITE
                    );
                }
            }
        }
    }
}
