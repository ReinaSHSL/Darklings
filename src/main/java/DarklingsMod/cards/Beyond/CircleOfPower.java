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

import DarklingsMod.cards.AbstractDittoCard;

public class CircleOfPower extends AbstractDittoCard {
    public static final String           ID = "CircleOfPower";
    public static final int            COST = 3;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Donu";

    public CircleOfPower() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 10;
        this.damageUp = 2;

        this.baseMagicNumber = 2;
        this.magicNumberUp = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i=0;i<2;i++) {
            act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));        
        }
        act(new SFXAction("MONSTER_DONU_DEFENSE"));
        actForDarklings(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    }
}