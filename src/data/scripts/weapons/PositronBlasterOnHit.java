package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class PositronBlasterOnHit implements OnHitEffectPlugin {

    private static final Color CORE_COLOR = new Color(255, 75, 100, 25);
    private static final Color FRINGE_COLOR = new Color(255, 100, 235, 50);
    private static final Vector2f ZERO = new Vector2f();

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine) {

        if (target instanceof ShipAPI && !shieldHit) {
            engine.spawnExplosion(point, ZERO, CORE_COLOR, 240f, 1.0f);
            engine.spawnExplosion(point, ZERO, FRINGE_COLOR, 120f, 0.6f);
        }
    }
}
