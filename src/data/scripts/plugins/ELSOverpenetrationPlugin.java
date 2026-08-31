package data.scripts.plugins;

import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.CollisionClass;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import org.lazywizard.lazylib.CollisionUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;
import java.util.List;

public class ELSOverpenetrationPlugin extends BaseEveryFrameCombatPlugin {

    private static final float SIROCCO_HIT_GLOW_RADIUS = 60f;
    private static final Color SIROCCO_HIT_GLOW_COLOR = new Color(255, 145, 130, 125);
    private static final Vector2f VEC_ZERO = new Vector2f();

    private CombatEngineAPI engine;

    @Override
    public void init(CombatEngineAPI engine) {
        this.engine = engine;
    }

    @Override
    public void advance(float amount, List<InputEventAPI> events) {
        if (engine == null || engine.isPaused()) return;

        for (DamagingProjectileAPI shot : engine.getProjectiles()) {
            if (shot == null) continue;
            if ("els_raad_pulse".equals(shot.getProjectileSpecId())) {
                handleSiroccoPenetration(shot, amount);
            }
        }
    }

    private void handleSiroccoPenetration(DamagingProjectileAPI shot, float amount) {
        Vector2f loc = shot.getLocation();
        if (loc == null) return;
        List<CombatEntityAPI> targets = CombatUtils.getEntitiesWithinRange(loc, 20f);

        for (CombatEntityAPI target : targets) {
            if (target == null || target == shot.getSource() || target.getCollisionClass() == CollisionClass.NONE) {
                continue;
            }
            if (target instanceof DamagingProjectileAPI) {
                continue;
            }

            if (target instanceof ShipAPI ship) {
                if (ship.getShield() != null && ship.getShield().isOn() && ship.getShield().isWithinArc(loc)) {
                    if (Math.random() <= 0.25) {
                        shot.setCollisionClass(CollisionClass.PROJECTILE_FF);
                        return;
                    }
                }
            }

            if (Math.random() <= 0.25) {
                Vector2f vel = shot.getVelocity();
                Vector2f pLoc = new Vector2f(vel.x * -amount + loc.x, vel.y * -amount + loc.y);

                Vector2f collisionPoint = CollisionUtils.getCollisionPoint(pLoc, loc, target);
                if (collisionPoint == null && CollisionUtils.isPointWithinBounds(loc, target)) {
                    collisionPoint = loc;
                }

                if (collisionPoint != null) {
                    shot.setCollisionClass(CollisionClass.NONE);
                    engine.addHitParticle(collisionPoint, VEC_ZERO, SIROCCO_HIT_GLOW_RADIUS, 1f, 1.5f, SIROCCO_HIT_GLOW_COLOR);

                    float speed = shot.getVelocity().length();
                    float modifier = (speed > 0f && target.getCollisionRadius() > 0f)
                            ? 1.0f / ((target.getCollisionRadius() * 2f) / speed)
                            : 1.0f;
                    float damage = (shot.getDamageAmount() * amount) * modifier;
                    float emp = (shot.getEmpAmount() * amount) * modifier;

                    engine.applyDamage(target, collisionPoint, damage, shot.getDamageType(), emp, false, false, shot.getSource());
                    return;
                }
            }
        }
    }
}
