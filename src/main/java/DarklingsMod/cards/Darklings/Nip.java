package DarklingsMod.cards;

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

import com.megacrit.cardcrawl.cards.DamageInfo;

import basemod.helpers.BaseModCardTags;

import DarklingsMod.cards.AbstractDittoCard;

public class Nip extends AbstractDittoCard {
    public static final String           ID = "Nip";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Darklings";

    public Nip() {
        super(ID, COST, TYPE, TARGET, AbstractCard.CardRarity.COMMON);

        this.baseDamage = 0;
        this.damageUp = 2;
    }

    @Override
    public void atTurnStart() {
        this.baseDamage = AbstractDungeon.monsterHpRng.random(9, 13);
        upgradeDamage(this.damageUp);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}