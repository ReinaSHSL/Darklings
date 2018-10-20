package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class SlimePower extends AbstractPower {
    public static final String POWER_ID = "Darklings:SlimePower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SlimePower(AbstractCreature owner, int turns) {
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
        this.description = DESCRIPTIONS[0] + (this.amount*10) + DESCRIPTIONS[1] + (this.amount*10) + DESCRIPTIONS[2];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if (type == DamageInfo.DamageType.NORMAL)
        {
            return damage * (1.0F-(this.amount*10));
        }
        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount)
    {
        return blockAmount * (1.0F-(this.amount*10));
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType)
    {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        return damage;
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/SlimePower.png");
    }
}
