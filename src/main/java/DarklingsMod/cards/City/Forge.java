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
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.Texture;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.actions.TeachAction;

public class Forge extends AbstractDittoCard {
    public static final String           ID = "Forge";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Champ";

    public Forge() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 1;
        this.magicNumberUp = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new TeachAction(
            getDarkittyn("Casey"),
            "Forge",
            "All Darklings gain " + String.valueOf(this.magicNumber) + " Block each turn.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/Forge.png"),
            () -> {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction((AbstractCreature)getDarkittyn("Anthony"), p, new MetallicizePower((AbstractCreature)getDarkittyn("Anthony"), this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction((AbstractCreature)getDarkittyn("Casey"), p, new MetallicizePower((AbstractCreature)getDarkittyn("Casey"), this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));
            }
            ));
    }
}