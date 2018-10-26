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

public class Heal extends AbstractDittoCard {
    public static final String           ID = "Heal";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.SKILL;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Mystic";

    public Heal() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 16;
        this.magicNumberUp = 4;
        this.exhaust = true;
        loadAnimation("images/monsters/theCity/healer/skeleton.atlas", "images/monsters/theCity/healer/skeleton.json", 1.0F);
        
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Attack", true);
        this.skeleScale = 0.6F;
        this.renderTint = Color.LIME;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        actForDarkittyn(new HealAction(p, p, this.magicNumber));
    }
}