package DarklingsMod.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;

import DarklingsMod.cards.AbstractDittoCard;

public abstract class Template extends AbstractDittoCard {
    public static final String           ID = "BlueShift";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Darklings";

    public Template() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);

        // this.baseDamage = 0;
        // this.damageUp = 0;

        // this.baseBlock = 0;
        // this.blockUp = 0;

        // this.baseMagicNumber = 0;
        // this.magicNumberUp = 0;

        // this.costUp = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
}