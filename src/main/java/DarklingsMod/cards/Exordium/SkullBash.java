package DarklingsMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.DamageInfo;

import DarklingsMod.cards.AbstractDittoCard;

import java.util.ArrayList;

public class SkullBash extends AbstractDittoCard {
    public static final String           ID = "SkullBash";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "GremlinNob";

    public SkullBash() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 14;
        this.damageUp = 2;

        this.baseMagicNumber = 25;
        this.magicNumberUp = 25;

    loadAnimation("images/monsters/theBottom/nobGremlin/skeleton.atlas", "images/monsters/theBottom/nobGremlin/skeleton.json", 1.0F);
    
    AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
    e.setTimeScale(2.0F);
        this.skeleScale = 0.5F;
    }

    // Yes, I copy-pasta'd the entire function here just to add a single bit of math.
    public void calculateCardDamage(AbstractMonster mo)
    {
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        if ((!this.isMultiDamage) && (mo != null))
        {
            float tmp = this.baseDamage;
            if ((AbstractDungeon.player.hasRelic("WristBlade")) && ((this.costForTurn == 0) || (this.freeToPlayOnce)))
            {
                tmp += 3.0F;
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }
            if (mo != null) {
                for (AbstractPower p : mo.powers) {
                    if (p.ID == VulnerablePower.POWER_ID) {
                        tmp = tmp * ((this.magicNumber / 100.0F) + 1.5F);
                    } else {
                            tmp = p.atDamageReceive(tmp, this.damageTypeForTurn);
                    }
                }
            }
            for (AbstractPower p : player.powers)
            {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }
            if (mo != null) {
                for (AbstractPower p : mo.powers)
                {
                    tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn);
                    if (this.baseDamage != (int)tmp) {
                        this.isDamageModified = true;
                    }
                }
            }
            if (tmp < 0.0F) {
                tmp = 0.0F;
            }
            this.damage = MathUtils.floor(tmp);
        }
        else
        {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = this.baseDamage;
            }
            for (int i = 0; i < tmp.length; i++)
            {
                if ((AbstractDungeon.player.hasRelic("WristBlade")) && ((this.costForTurn == 0) || (this.freeToPlayOnce)))
                {
                    tmp[i] += 3.0F;
                    if (this.baseDamage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }
                for (AbstractPower p : player.powers)
                {
                    tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
                    if (this.baseDamage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
                    if ((!((AbstractMonster)m.get(i)).isDying) && (!((AbstractMonster)m.get(i)).isEscaping)) {
                        if (p.ID == VulnerablePower.POWER_ID) {
                            tmp[i] = tmp[i] * ((this.magicNumber / 100.0F) + 1.5F);
                        } else {
                            tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn);
                        }
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : player.powers)
                {
                    tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
                    if (this.baseDamage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
                    if ((!((AbstractMonster)m.get(i)).isDying) && (!((AbstractMonster)m.get(i)).isEscaping)) {
                        tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn);
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i] < 0.0F) {
                    tmp[i] = 0.0F;
                }
            }
            this.multiDamage = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                this.multiDamage[i] = MathUtils.floor(tmp[i]);
            }
            this.damage = this.multiDamage[0];
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}