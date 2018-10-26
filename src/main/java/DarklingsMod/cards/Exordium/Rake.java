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

public class Rake extends AbstractDittoCard {
    public static final String           ID = "Rake";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ALL_ENEMY;
    public static final String  MONSTERPOOL = "SlaverBlue";

    public Rake() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 7;
        this.damageUp = 3;
        this.isMultiDamage = true;


    loadAnimation("images/monsters/theBottom/blueSlaver/skeleton.atlas", "images/monsters/theBottom/blueSlaver/skeleton.json", 1.0F);
    
    AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        this.skeleScale = 0.75F;
        this.renderTint = Color.ROYAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}