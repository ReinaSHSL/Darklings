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

public class FlameTackle extends AbstractDittoCard {
    public static final String           ID = "FlameTackle";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "TorchHead";

    public FlameTackle() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);

        this.baseDamage = 3;
        this.damageUp = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractFriendlyMonster anthony = getDarkittyn("Anthony");

        if (!this.upgraded) {
        act(new TeachAction(
            anthony,
            "Flame Tackle",
            "Deal 3 damage 2 times.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/FlameTackle.png"),
            () -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(anthony, 3, this.damageTypeForTurn);
                info.applyPowers(anthony, target);
                for (int i=0;i<2 ;i++ ) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
                }
            }
            ));
        } else {
        act(new TeachAction(
            anthony,
            "Flame Tackle+",
            "Deal 4 damage 2 times.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/FlameTackle.png"),
            () -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(anthony, 4, this.damageTypeForTurn);
                info.applyPowers(anthony, target);
                for (int i=0;i<2 ;i++ ) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
                }
            }
            ));
        }
    }
}