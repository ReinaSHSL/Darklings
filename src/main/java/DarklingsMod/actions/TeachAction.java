package DarklingsMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.core.Settings;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public class TeachAction extends AbstractGameAction
{
  private String moveName;
  private String moveDescription;
  private Texture moveImage;
  private Runnable moveActions;
  private AbstractFriendlyMonster owner;
  
  public TeachAction(AbstractFriendlyMonster target, String name, String description, Texture moveIcon, Runnable move)
  {
    this.moveName = name;
    this.moveDescription = description;
    this.moveImage = moveIcon;
    this.moveActions = move;
    this.actionType = AbstractGameAction.ActionType.SPECIAL;
    this.duration = Settings.ACTION_DUR_FAST;
  }
  
  public TeachAction(String name, String description, Texture moveIcon, Runnable move)
  {
    this(null, name, description, moveIcon, move);
  }

  public void update()
  {
    if ((this.duration == Settings.ACTION_DUR_FAST) && (this.target != null))
    {
      ((AbstractFriendlyMonster)this.target).addMove(
        new MinionMove(this.moveName, (AbstractFriendlyMonster)this.target, this.moveImage, this.moveDescription, this.moveActions));
    }
    tickDuration();
  }
}
