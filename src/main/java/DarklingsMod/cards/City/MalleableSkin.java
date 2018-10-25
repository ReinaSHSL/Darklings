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
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.Texture;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.actions.TeachAction;

public class MalleableSkin extends AbstractDittoCard {
    public static final String           ID = "MalleableSkin";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "SnakePlant";

    public MalleableSkin() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 5;
        this.magicNumberUp = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new TeachAction(
            getDarkittyn("Anthony"),
            "Malleable Skin",
            "All Darklings gain " + String.valueOf(this.magicNumber) + " Block.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/MalleableSkin.png"),
            () -> {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(getDarkittyn("Anthony"), getDarkittyn("Anthony"), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(getDarkittyn("Casey"), getDarkittyn("Casey"), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
            }
            ));
    }
}