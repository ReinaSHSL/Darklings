package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class HexPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:HexPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HexPower(AbstractCreature owner, int dazes) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = dazes;
        updateDescription();
        this.img = getReincarnationPowerTexture();
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        }
    }

    public void onInitialApplication() {
        this.atStartOfTurn();
    }

    public void atStartOfTurn() {
        flash();
        if (((AbstractMonster)this.owner).intent != AbstractMonster.Intent.ATTACK ||
        ((AbstractMonster)this.owner).intent != AbstractMonster.Intent.ATTACK_BUFF ||
        ((AbstractMonster)this.owner).intent != AbstractMonster.Intent.ATTACK_DEBUFF ||
        ((AbstractMonster)this.owner).intent != AbstractMonster.Intent.ATTACK_DEFEND) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new DazePower(this.owner, this.amount), this.amount));
        }
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/HexPower.png");
    }
}
