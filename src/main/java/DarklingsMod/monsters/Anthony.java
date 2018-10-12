package DarklingsMod.monsters;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.AbstractPower;
import kobting.friendlyminions.actions.ChooseAction;
import kobting.friendlyminions.actions.ChooseActionInfo;
import kobting.friendlyminions.cards.MonsterCard;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import org.apache.logging.log4j.LogManager;


import java.util.ArrayList;

public class Anthony extends AbstractFriendlyMonster {
    public static String NAME = "Anthony";
    public static String ID = "Darklings:Anthony";
    private ArrayList<ChooseActionInfo> moveInfo;
    public boolean green;
    private float offsetY;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public Anthony() {
        super(NAME, ID, 20,
                null, -2.0F, 10.0F, 220.0F, 240.0F,
                "DarklingsImgs/buddy/DarklingBuddy.png", -935, -130);
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
}
