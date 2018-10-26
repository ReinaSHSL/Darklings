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

import com.megacrit.cardcrawl.cards.DamageInfo;

import basemod.helpers.BaseModCardTags;

import DarklingsMod.cards.AbstractDittoCard;

public class Chomp extends AbstractDittoCard {
    public static final String           ID = "Chomp";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Darklings";

    public Chomp() {
        super(ID, COST, TYPE, TARGET, AbstractCard.CardRarity.UNCOMMON);

        this.baseDamage = 8;
        this.damageUp = 1;
        loadAnimation("images/monsters/theForest/darkling/skeleton.atlas", "images/monsters/theForest/darkling/skeleton.json");
        this.state.setAnimation(0, "Attack", true);
        this.skeleScale = 0.66F;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}