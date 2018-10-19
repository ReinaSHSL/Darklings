package DarklingsMod.cards.Relics;

import DarklingsMod.cards.AbstractRelicCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Astrolabe extends AbstractRelicCard {
    public static final String           ID = "Astrolabe";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.SKILL;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String       RELICID = Astrolabe.ID;

    public Astrolabe() {
        super(ID, COST, TYPE, TARGET, RELICID);

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
