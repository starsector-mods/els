package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.dark.graphics.util.AnamorphicFlare;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class AlkahestCritical implements OnHitEffectPlugin {

    private static final Color ARC_CORE_COLOR = new Color(255, 100, 235, 255);
    private static final Color EXPLOSION_COLOR = new Color(255, 100, 235, 215);
    private static final Color FLARE_FRINGE_COLOR = new Color(255, 100, 235, 50);
    private static final float ARC_WIDTH = 20f;
    private static final float ARC_RANGE = 5000f;
    private static final String SFX = "tachyon_lance_emp_impact";
    private static final float CRIT_CHANCE = 0.25f;

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine) {

        if (target instanceof ShipAPI ship && Math.random() <= CRIT_CHANCE) {
            float emp;
            if (shieldHit) {
                emp = projectile.getEmpAmount();
            } else {
                engine.spawnExplosion(point, ship.getVelocity(), EXPLOSION_COLOR, 100f, 2.8f);
                emp = projectile.getEmpAmount() * 2f;
            }

            float dam = projectile.getDamageAmount() * 0.25f;
            float flareAngle = (float) Math.random() * 15f - 7.5f;

            if (Global.getSettings().getModManager().isModEnabled("shaderLib")) {
                AnamorphicFlare.createFlare(ship, point, engine, 0.40f, 0.1f, flareAngle, 10f, 5f, EXPLOSION_COLOR, FLARE_FRINGE_COLOR);
            }

            engine.spawnEmpArcPierceShields(
                    projectile.getSource(),
                    point,
                    ship,
                    ship,
                    DamageType.ENERGY,
                    dam,
                    emp,
                    ARC_RANGE,
                    SFX,
                    ARC_WIDTH,
                    EXPLOSION_COLOR,
                    ARC_CORE_COLOR
            );
        }
    }
}
