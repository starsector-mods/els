package data.scripts.world;

import exerelin.campaign.SectorManager;

public class NexerelinIntegration {

    public static boolean isCorvusMode() {
        return SectorManager.getManager().isCorvusMode();
    }
}
