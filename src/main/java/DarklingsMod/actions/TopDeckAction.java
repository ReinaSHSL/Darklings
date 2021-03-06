package DarklingsMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;

public class TopDeckAction extends AbstractGameAction {
  private AbstractPlayer p;
  
  public TopDeckAction(int amount)
  {
    this.p = AbstractDungeon.player;
    setValues(this.p, AbstractDungeon.player, amount);
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.duration = Settings.ACTION_DUR_MED;
  }
  
  public void update()
  {
    CardGroup tmp;
    String cardGroup;
    AbstractPlayer p = AbstractDungeon.player;
    if (this.duration == Settings.ACTION_DUR_MED)
    {
      tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
      for (AbstractCard c : this.p.drawPile.group) {
        tmp.addToRandomSpot(c);
        c.cantUseMessage = "drawPile";
      }
      for (AbstractCard c : this.p.hand.group) {
        tmp.addToBottom(c);
        c.cantUseMessage = "hand";
      }
      for (AbstractCard c : this.p.discardPile.group) {
        tmp.addToTop(c);
        c.cantUseMessage = "discardPile";
      }
      if (tmp.size() == 0)
      {
        this.isDone = true;
        return;
      }
      if (tmp.size() == 1)
      {
        AbstractCard card = tmp.getTopCard();
        card.unhover();
        card.lighten(true);
        card.setAngle(0.0F);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.current_x = CardGroup.DRAW_PILE_X;
        card.current_y = CardGroup.DRAW_PILE_Y;
        switch (card.cantUseMessage) {
          case "drawPile":
            this.p.drawPile.moveToDeck(card, false);
            break;
          case "hand":
            this.p.hand.moveToDeck(card, false);
            break;
          case "discardPile":
            this.p.discardPile.moveToDeck(card, false);
            break;
        }

        this.isDone = true;
        return;
      }
      if (tmp.size() <= this.amount)
      {
        for (int i = 0; i < tmp.size(); i++)
        {
          AbstractCard card = tmp.getNCardFromTop(i);
          card.unhover();
          card.lighten(true);
          card.setAngle(0.0F);
          card.drawScale = 0.12F;
          card.targetDrawScale = 0.75F;
          card.current_x = CardGroup.DRAW_PILE_X;
          card.current_y = CardGroup.DRAW_PILE_Y;
          switch (card.cantUseMessage) {
            case "drawPile":
              this.p.drawPile.moveToDeck(card, false);
              break;
            case "hand":
              this.p.hand.moveToDeck(card, false);
              break;
            case "discardPile":
              this.p.discardPile.moveToDeck(card, false);
              break;
          }
        }
        this.isDone = true;
        return;
      }
      if (this.amount == 1) {
        AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose a card to Exhaust.", false);
      } else {
        AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + Integer.toString(this.amount) + " cards to Exhaust.", false);
      }
      tickDuration();
      return;
    }
    if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
    {
      for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
      {
        card.unhover();
        switch (card.cantUseMessage) {
          case "drawPile":
            this.p.drawPile.moveToDeck(card, false);
            break;
          case "hand":
            this.p.hand.moveToDeck(card, false);
            break;
          case "discardPile":
            this.p.discardPile.moveToDeck(card, false);
            break;
        }
      }
      AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }
    tickDuration();
  }
}
