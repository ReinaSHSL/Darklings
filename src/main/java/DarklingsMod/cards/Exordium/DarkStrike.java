package DarklingsMod.cards.Exordium;

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

import DarklingsMod.cards.AbstractDittoCard;

public abstract class DarkStrike extends AbstractDittoCard {
    public static final String           ID = "DarkStrike";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "AcidSlime_S";

    public DarkStrike() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 3;
        this.damageUp = 1;

        this.baseMagicNumber = 3;
        this.magicNumberUp = 1;
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.hasPower("Strength")) {
            AbstractDungeon.player.getPower("Strength").amount = AbstractDungeon.player.getPower("Strength").amount * this.magicNumber; }
        super.applyPowers();
        if (AbstractDungeon.player.hasPower("Strength")) {
            AbstractDungeon.player.getPower("Strength").amount = AbstractDungeon.player.getPower("Strength").amount / this.magicNumber; }        
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (AbstractDungeon.player.hasPower("Strength")) {
            AbstractDungeon.player.getPower("Strength").amount = AbstractDungeon.player.getPower("Strength").amount * this.magicNumber; }
        super.calculateCardDamage(mo);
        if (AbstractDungeon.player.hasPower("Strength")) {
            AbstractDungeon.player.getPower("Strength").amount = AbstractDungeon.player.getPower("Strength").amount / this.magicNumber; }        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}