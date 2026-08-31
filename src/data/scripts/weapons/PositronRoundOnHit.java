package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.Color;

public class PositronRoundOnHit implements OnHitEffectPlugin {

    private static final Color EXPLOSION_COLOR = new Color(255, 100, 235, 215);

    @Override
    public void onHit(DamagingProjectileAPI projectile,
                      CombatEntityAPI target,
                      Vector2f point,
                      boolean shieldHit,
                      ApplyDamageResultAPI damageResult,
                      CombatEngineAPI engine) {

        if (!shieldHit && target != null) {
            engine.spawnExplosion(point, target.getVelocity(), EXPLOSION_COLOR, 120f, 2.4f);
        }
    }
}
