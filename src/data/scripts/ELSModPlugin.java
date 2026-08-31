package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import data.scripts.world.ELSGen;
import data.scripts.world.NexerelinIntegration;
import org.dark.shaders.util.ShaderLib;

public class ELSModPlugin extends BaseModPlugin {

    public static boolean isGraphicsLibEnabled() {
        return Global.getSettings().getModManager().isModEnabled("shaderLib");
    }

    public static boolean isNexerelinEnabled() {
        return Global.getSettings().getModManager().isModEnabled("nexerelin");
    }

    @Override
    public void onApplicationLoad() {
        if (isGraphicsLibEnabled()) {
            ShaderLib.init();
        }
    }

    @Override
    public void onNewGame() {
        boolean isNex = isNexerelinEnabled();
        if (!isNex || NexerelinIntegration.isCorvusMode()) {
            new ELSGen().generate(Global.getSector());
        }
    }
}
