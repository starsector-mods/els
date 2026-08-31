package data.scripts.world;

import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.scripts.world.systems.Rousseau;

public class ELSGen implements SectorGeneratorPlugin {

    @Override
    public void generate(SectorAPI sector) {
        if (SharedData.getData() != null && SharedData.getData().getPersonBountyEventData() != null) {
            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("directory");
        }
        Rousseau.generate(sector);
    }
}
