package DarklingsMod.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import kobting.friendlyminions.actions.ChooseAction;
import kobting.friendlyminions.actions.ChooseActionInfo;
import kobting.friendlyminions.cards.MonsterCard;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import java.util.ArrayList;

public class DarklingsBuddy extends AbstractFriendlyMonster {
    public static String NAME = "";
    public static String ID = "DarklingsBuddy";
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean green;
    private float offsetY;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public DarklingsBuddy(float offsetY, boolean green) {
        super(NAME, ID, 20,
                null, -2.0F, 10.0F, 220.0F, 240.0F,
                "DarklingsImgs/buddy/DarklingBuddy.png", -935, offsetY);
        this.green = green;
        this.offsetY = offsetY;

    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new MonsterCard(), target, "Choose your attack");
            this.moveInfo.forEach( move -> {
                pickAction.add(move.getName(), move.getDescription(), move.getAction());
            });
            AbstractDungeon.actionManager.addToBottom(pickAction);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    //Create possible moves for the monster
    private ArrayList<ChooseActionInfo> makeMoves(){
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        target = AbstractDungeon.getRandomMonster();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.id.equals(AwakenedOne.ID) || mo.id.equals(Darkling.ID)) {
                target = mo;
            }
        }
        if ((target != null)) {
            tempInfo.add(new ChooseActionInfo("Attack", "owo", () -> {
                DamageInfo info = new DamageInfo(this, 1, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }));
        }

        return tempInfo;
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

    @Override
    public void renderHealth(final SpriteBatch sb) {
        float x = this.hb.cX - this.hb.width / 2.0f;
        final float y = this.hb.cY - this.hb.height / 2.0f + offsetY;
        if (this.green) {
            if (this.currentBlock > 0) {
                sb.setColor(Color.valueOf("31568c00"));
            }
            else {
                sb.setColor(new Color(0.8f, 0.05f, 0.05f, 0.0f););
            }
            if (!this.hasPower("Poison")) {
                if (this.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - AbstractCreature.HEALTH_BAR_HEIGHT, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
                }
                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, this.targetHealthBarWidth, AbstractCreature.HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHealthBarWidth, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
            }
            else {
                int poisonAmt = this.getPower("Poison").amount;
                if (poisonAmt > 0 && this.hasPower("Intangible")) {
                    poisonAmt = 1;
                }
                if (this.currentHealth > poisonAmt) {
                    float w = 1.0f - (this.currentHealth - poisonAmt) / this.currentHealth;
                    w *= this.targetHealthBarWidth;
                    if (this.currentHealth > 0) {
                        sb.draw(ImageMaster.HEALTH_BAR_L, x - AbstractCreature.HEALTH_BAR_HEIGHT, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
                    }
                    sb.draw(ImageMaster.HEALTH_BAR_B, x, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, this.targetHealthBarWidth - w, AbstractCreature.HEALTH_BAR_HEIGHT);
                    sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHealthBarWidth - w, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
                }
            }
        } else {

        }
    }

}
