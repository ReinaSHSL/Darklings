package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VoidPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:VoidPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VoidPower(AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = turns;
        updateDescription();
        this.img = getReincarnationPowerTexture();
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/VoidPower.png");
    }
}
