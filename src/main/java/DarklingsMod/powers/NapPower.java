package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NapPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:NapPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean NoCardsPlayed = false;

    public NapPower(AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = turns;
        updateDescription();
        this.img = getReincarnationPowerTexture();
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        }
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/NapPower.png");
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            NoCardsPlayed = false;
            if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0) {
                NoCardsPlayed = true;
                return;
            }
        }
    }
  
    public void atEndOfRound() {
        if (NoCardsPlayed) { 
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.amount));
            flash();
        }
    }
}
