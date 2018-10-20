package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class StunPower extends AbstractPower
{
  public static final String POWER_ID = "Darklings:StunPower";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  public AbstractMonster monster;

  public StunPower(AbstractCreature owner, int amount)
  {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    if (this.owner instanceof AbstractMonster) { this.monster = (AbstractMonster)owner; }
    this.amount = amount;
    updateDescription();
    this.type = AbstractPower.PowerType.DEBUFF;

    this.img = getReincarnationPowerTexture();

    this.isTurnBased = true;
  }

  private static Texture getReincarnationPowerTexture() {
      return TextureLoader.getTexture("DarklingImgs/powers/StunPower.png");
  }

  public void onInitialApplication() {
    if (this.owner instanceof AbstractPlayer) { this.endTurn(); }
    else {
      this.monster.setMove((byte)-2, AbstractMonster.Intent.STUN); 
      this.monster.createIntent();
    }
  }

  @Override
  public void updateDescription()
  {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
  }

  // Decrement Stun, and make sure the enemy is correctly set to sleep
  @Override
  public void atEndOfTurn(boolean isPlayer)
  {
    if (this.owner instanceof AbstractPlayer && isPlayer) {
      AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    if (!isPlayer && (this.owner instanceof AbstractMonster)) { 
      this.monster.setMove((byte)-1, AbstractMonster.Intent.STUN); 
      this.monster.createIntent();

      if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
      {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        if (this.amount == 1) {
          switch(this.monster.id) {

            case "SlimeBoss":
              this.monster.setMove((byte)4, AbstractMonster.Intent.STRONG_DEBUFF);
              break;

            case "Looter":
            case "Mugger":
              this.monster.setMove((byte)2, AbstractMonster.Intent.DEFEND);
              break;

            default:
              this.monster.rollMove(); 
              break;
          }
        }
      }
    }
  }

  public void endTurn() {
      AbstractDungeon.actionManager.cardQueue.clear();
      for (AbstractCard c : AbstractDungeon.player.limbo.group) {
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
      }
      AbstractDungeon.player.limbo.group.clear();
      AbstractDungeon.player.releaseCard();
      AbstractDungeon.overlayMenu.endTurnButton.disable(true);
  }
}

