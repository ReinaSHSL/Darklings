package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CountdownPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:CountdownPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int strGain;

    public CountdownPower(AbstractCreature owner, int turns, int strGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = turns;
        updateDescription();
        this.img = getReincarnationPowerTexture();
        this.strGain = strGain;
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.strGain + DESCRIPTIONS[2]);
    }

    public void atEndOfRound()
    {
        if (this.amount == 0) {
          AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Darklings:CountdownPower"));
          AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.strGain), this.strGain));
        } else {
          AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Darklings:CountdownPower", 1));
        }
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/CountdownPower.png");
    }
}
