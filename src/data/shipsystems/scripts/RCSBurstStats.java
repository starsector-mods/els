package data.shipsystems.scripts;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

public class RCSBurstStats extends BaseShipSystemScript {

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        if (state == State.OUT) {
            stats.getMaxSpeed().unmodify(id);
        } else {
            stats.getMaxSpeed().modifyPercent(id, 200f * effectLevel);
            stats.getAcceleration().modifyPercent(id, 300f * effectLevel);
            stats.getMaxTurnRate().modifyPercent(id, 100f * effectLevel);
            stats.getTurnAcceleration().modifyPercent(id, 100f * effectLevel);
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0) {
            return new StatusData("increased maneuverability", false);
        }
        return null;
    }
}
