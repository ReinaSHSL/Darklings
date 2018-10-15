package DarklingsMod.cards;

import DarklingsMod.powers.WoundPower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.DamageInfo;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.powers.BurnPower;

public abstract class StabbyStab extends AbstractDittoCard {
    public static final String           ID = "StabbyStab";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "BookOfStabbing";

    public StabbyStab() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 6;
        this.damageUp = 1;

        this.baseMagicNumber = 2;
    }

    @Override
    public void triggerWhenDrawn() {
        this.baseMagicNumber++;
        this.isMagicNumberModified = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i=0;i<this.magicNumber;i++) {
            act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));        
        }
        if (this.upgraded) {
            act(new ApplyPowerAction(m, p, new WoundPower(m, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}