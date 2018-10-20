package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import DarklingsMod.powers.StunPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class DazePower extends AbstractPower {
    public static final String POWER_ID = "Darklings:DazePower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DazePower(AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = turns;
        updateDescription();
        this.img = getReincarnationPowerTexture();
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    @Override
    public void onInitialApplication() {

    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 5) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 5));
            AbstractDungeon.actionManager.addToBottom(new  ApplyPowerAction(this.owner, this.owner, new StunPower(this.owner, 1), 1));
        }
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/DazePower.png");
    }
}
