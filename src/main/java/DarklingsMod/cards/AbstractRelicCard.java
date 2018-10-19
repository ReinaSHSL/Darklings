package DarklingsMod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public abstract class AbstractRelicCard extends AbstractDittoCard {

    public String relicId;

    public AbstractRelicCard(String id, int cost, CardType type, CardTarget target, CardRarity rarity, String relic) {
        super(id, cost, type, target, rarity, relic);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings("Darklings:"+id);
        this.originalName = cardStrings.NAME;
        this.name = cardStrings.NAME;
        this.rawDescription = cardStrings.DESCRIPTION;

        this.relicId = relic;

        initializeTitle();
        initializeDescription();
    }

    public AbstractRelicCard(String id, int cost, CardType type, CardTarget target, String relic) {
        this(id, cost, type, target, AbstractCard.CardRarity.SPECIAL, relic);
    }
}
