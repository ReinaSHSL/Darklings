package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AngerPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:AngerPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int strGain = 0;

    public AngerPower(AbstractCreature owner, int turns) {
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
        return TextureLoader.getTexture("DarklingImgs/powers/AngerPower.png");
    }

    public void atStartOfTurn(boolean isPlayer) {
        if (isPlayer) {
            strGain = 0;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.escaped) {
                    if ((mo.intent == AbstractMonster.Intent.ATTACK_BUFF) ||
                        (mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF) ||
                        (mo.intent == AbstractMonster.Intent.ATTACK_DEFEND) ||
                        (mo.intent == AbstractMonster.Intent.ATTACK)) {
                        strGain += this.amount;
                    }
                }
            }
        }
    }
  
    public void atEndOfRound() {
        if (strGain > 0) { 
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, strGain), strGain));
            flash();
        }
    }
}
