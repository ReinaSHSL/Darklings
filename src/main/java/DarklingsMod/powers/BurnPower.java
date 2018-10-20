package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class BurnPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:BurnPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BurnPower(AbstractCreature owner, int turns) {
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
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/BurnPower.png");
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}
