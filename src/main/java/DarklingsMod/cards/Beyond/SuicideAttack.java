package DarklingsMod.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import DarklingsMod.powers.WoundPower;

import DarklingsMod.cards.AbstractDittoCard;

public class SuicideAttack extends AbstractDittoCard {
    public static final String           ID = "SuicideAttack";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Dagger";

    public SuicideAttack() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 0;
        this.baseMagicNumber = 2;
        this.magicNumberUp = 1;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        act(new ApplyPowerAction(m, p, new WoundPower(m, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
    }
}