package DarklingsMod.character;

import DarklingsMod.enums.DarklingsEnum;
import basemod.BaseMod;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.characters.CustomCharSelectInfo;

import java.util.ArrayList;

public class Darklings extends AbstractPlayerWithMinions {
    public CustomCharSelectInfo getInfo() {
        return (CustomCharSelectInfo) getLoadout ();
    }

    public Darklings (String name, PlayerClass setClass) {
        super(name, setClass, null, null, (String)null, null);
        initializeClass(null, "DarklingsImgs/charassets/shoulder2.png",
                "DarklingsImgs/charassets/shoulder.png", "DarklingsImgs/charassets/corpse.png",
                getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f, new EnergyManager(3));
        this.loadAnimation("DarklingsImgs/charassets/skeleton.atlas", "DarklingsImgs/charassets/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }


    public static ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        return retVal;
    }

    public static ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        return retVal;
    }

    public static CharSelectInfo getLoadout() {

        CharSelectInfo info = new CustomCharSelectInfo (
                "Darklings",
                "TODO",
                20, //currentHP
                20, //maxHP
                0,  //maxOrbs
                2,  //maxMinions
                99, //gold
                5,  //cardDraw
                DarklingsEnum.DARKLING,
                getStartingRelics(),
                getStartingDeck(),
                false);
        return info;
    }
}
