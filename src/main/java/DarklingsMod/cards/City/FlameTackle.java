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

public abstract class FlameTackle extends AbstractDittoCard {
    public static final String           ID = "FlameTackle";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "TorchHead";

    public FlameTackle() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new TeachAction(
            getDarkittyn("Anthony"),
            "Flame Tackle",
            "Deal 3 damage 2 times.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/FlameTackle.png"),
            () -> {
                for (int i=0;i<2 ;i++ ) {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(getDarkittyn("Anthony"), 3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                }
            }
            ));
    }
}