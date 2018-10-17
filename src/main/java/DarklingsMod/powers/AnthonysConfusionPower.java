package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

import DarklingsMod.monsters.Anthony;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import basemod.ReflectionHacks;

public class AnthonysConfusionPower extends AbstractPower {
    public static final String POWER_ID = "Darklings:AnthonysConfusionPower";
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static Anthony anthony;
    public boolean upgraded = false;
    public HashMap<String, MinionMove> moves = new HashMap();
    public static Random random = new Random();

    public AnthonysConfusionPower(boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.upgraded = upgraded;
        updateDescription();
        this.img = getReincarnationPowerTexture();

        // Grab Anthony
        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            if (m instanceof Anthony) {
                this.anthony = (Anthony)m;
            }
        }
    }

    public void onInitialApplication() { randomizeAnthony(); }
    public void atStartOfTurn() { randomizeAnthony(); }

    public void randomizeAnthony() {
        // Clear Existing Moves
        MinionMoveGroup movelist = (MinionMoveGroup)ReflectionHacks.getPrivate(this.anthony, this.anthony.getClass(), "moves");
        ArrayList<MinionMove> minmovelist = (ArrayList<MinionMove>)ReflectionHacks.getPrivate(movelist, movelist.getClass(), "moves");
        minmovelist.clear();
        movelist.updatePositions();

        // Pick random moves
        int tmp;
        int i;
        int loop = MathUtils.random(1,3);
        for (i=0;i<loop;i++) {
            // Reset Random Moves
            this.moves.clear();
            defineMoves();

            tmp = MathUtils.random(20);
            switch (tmp) {
                case 0:
                    this.anthony.addMove(this.moves.get("endTurn"));
                    break;
                case 1:
                    this.anthony.addMove(this.moves.get("transmutehand"));
                    break;
                case 2:
                    this.anthony.addMove(this.moves.get("vampire"));
                    break;
                case 3:
                    this.anthony.addMove(this.moves.get("tripleblock"));
                    break;
                case 4:
                    this.anthony.addMove(this.moves.get("potion"));
                    break;
                case 5:
                    this.anthony.addMove(this.moves.get("energy"));
                    break;
                case 6:
                    this.anthony.addMove(this.moves.get("multi-hit"));
                    break;
                case 7:
                    this.anthony.addMove(this.moves.get("loseHP"));
                    break;
                case 8:
                case 9:
                    this.anthony.addMove(this.moves.get("discard"));
                    break;
                case 10:
                case 11:
                    this.anthony.addMove(this.moves.get("block"));
                    break;
                case 12:
                case 13:
                    this.anthony.addMove(this.moves.get("random-hit"));
                    break;
                case 14:
                case 15:
                case 16:
                    this.anthony.addMove(this.moves.get("gainStatus"));
                    break;
                default:
                    this.anthony.addMove(this.moves.get("attack"));
                    break;
            }
        }
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public void defineMoves() {
        int u = 0;
        if (this.upgraded) { u = 4; }
        int random10 = MathUtils.random(10+u);
        int random5 = MathUtils.random(5+u/2);
        int random3 = MathUtils.random(3+u/4);
        int random2 = MathUtils.random(2+u/4);

        this.moves.put("attack", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cAttack.png"),
            "Deal " + random10 + " damage.",
            () -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(this.anthony, random10, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this.anthony, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, randomEnum(AbstractGameAction.AttackEffect.class)));
            }));

        this.moves.put("multi-hit", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cMulti.png"),
            "Deal " + random5 + " damage " + random3 + " times.",
            () -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(this.anthony, random5, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this.anthony, target);
                for (int i=0;i<random3 ;i++ ) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, randomEnum(AbstractGameAction.AttackEffect.class)));
                }
            }));

        this.moves.put("random-hit", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cRandomMulti.png"),
            "Deal " + random5 + " damage to " + random3 + " enemies randomly.",
            () -> {
                for (int i=0;i<random3 ;i++ ) {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    DamageInfo info = new DamageInfo(this.anthony, random5, DamageInfo.DamageType.NORMAL);
                    info.applyPowers(this.anthony, target);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, randomEnum(AbstractGameAction.AttackEffect.class)));
                }
            }));

        this.moves.put("energy", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cEnergy.png"),
            "Gain " + random2 + " energy.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(random2));
            }));

        this.moves.put("potion", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cPotion.png"),
            "Obtain a random potion.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            }));

        this.moves.put("block", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cBlock.png"),
            "Gain " + random5 + " Block.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.anthony, this.anthony, random5));
            }));

        this.moves.put("tripleblock", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cTripleBlock.png"),
            "Triple Player block.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new TripleYourBlockAction(AbstractDungeon.player));
            }));

        this.moves.put("vampire", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cVampire.png"),
            "Deal " + random5 + " damage and gain " + random5 + " HP.",
            () -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(this.anthony, random5, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this.anthony, target);
                AbstractGameAction a = new VampireDamageAction(target, info, randomEnum(AbstractGameAction.AttackEffect.class));
                a.source = this.anthony;
                AbstractDungeon.actionManager.addToBottom(a);
            }));

        this.moves.put("transmutehand", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cTransmute.png"),
            "Transmute the cards in your hand.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new TransmuteAction());
            }));

        this.moves.put("loseHP", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cLoseHp.png"),
            "Lose " + random5 + " HP.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.anthony, this.anthony, random5));
            }));

        this.moves.put("discard", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cDiscard.png"),
            "Discard " + random3 + " cards.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, this.anthony, random3, true));
            }));

        this.moves.put("endTurn", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cEndTurn.png"),
            "Skips your turn.",
            () -> {
                AbstractDungeon.actionManager.addToBottom(new EndTurnAction());
            }));

        this.moves.put("gainStatus", 
            new MinionMove("???", this.anthony,
            ImageMaster.loadImage("DarklingImgs/buddy/actions/cStatus.png"),
            "Add " + random2 + " status cards to your hand.",
            () -> {
                ArrayList<AbstractCard> statuses = new ArrayList();
                statuses.add(new Burn());
                statuses.add(new Wound());
                statuses.add(new Dazed());
                statuses.add(new com.megacrit.cardcrawl.cards.status.Void());
                statuses.add(new Slimed());
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(statuses.get(random.nextInt(statuses.size())), random2));
            }));
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }

    private static Texture getReincarnationPowerTexture() {
        return TextureLoader.getTexture("DarklingImgs/powers/AnthonysConfusionPower.png");
    }
}
