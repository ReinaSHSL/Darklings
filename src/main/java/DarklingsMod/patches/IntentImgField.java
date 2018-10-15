package DarklingsMod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(clz = AbstractMonster.class, method = SpirePatch.CLASS)
public class IntentImgField {
    public static SpireField<Texture> intentImage = new SpireField<>(() -> null);
}
