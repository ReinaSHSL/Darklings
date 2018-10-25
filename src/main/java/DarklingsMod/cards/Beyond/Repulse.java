package DarklingsMod.cards;

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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.Texture;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.actions.TeachAction;
import DarklingsMod.powers.DazePower;

public class Repulse extends AbstractDittoCard {
    public static final String           ID = "Repulse";
    public static final int            COST = 0;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Repulsor";

    public Repulse() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 1;
        this.magicNumberUp = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new TeachAction(
            getDarkittyn("Casey"),
            "Repulse",
            "Apply " + String.valueOf(this.magicNumber) + " Daze.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/Repulse.png"),
            () -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, getDarkittyn("Casey"), new DazePower(target, this.magicNumber), this.magicNumber));
            }
            ));
    }
}