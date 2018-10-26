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

public class Protect extends AbstractDittoCard {
    public static final String           ID = "Protect";
    public static final int            COST = 0;
    public static final CardType       TYPE = CardType.SKILL;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "GremlinTsundere";

    public Protect() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 7;
        this.damageUp = 4;

    loadAnimation("images/monsters/theBottom/femaleGremlin/skeleton.atlas", "images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0F);
    
    AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        this.skeleScale = 0.8F;

        this.renderTint = Color.PINK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        actForDarkittyn(new GainBlockAction(p, p, this.block));
    }
}