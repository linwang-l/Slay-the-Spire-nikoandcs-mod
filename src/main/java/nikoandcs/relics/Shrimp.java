package nikoandcs.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import nikoandcs.character.MyCharacter;

import static nikoandcs.BasicMod.makeID;

// 注意：这里继承的是你项目里的 BaseRelic
public class Shrimp extends BaseRelic {
    // 遗物 ID
    public static final String ID = makeID("Shrimp");

    public Shrimp() {
        // 这里的参数顺序：ID, 图片名, 颜色池, 稀有度, 落地声音
        // 模板会自动去 images/relics/ 目录下找 "shrimp.png"
        super(ID, "Shrimp", MyCharacter.Meta.CARD_COLOR, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        int strengthAmount = 2; // 默认普通敌人 +2 力量

        // 1. 判定是否为精英
        boolean isElite = AbstractDungeon.getCurrRoom().eliteTrigger;

        // 2. 判定是否为 Boss
        boolean isBoss = false;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                isBoss = true;
                break;
            }
        }

        // 3. 根据敌人类型调整力量数值 (Niko 变虾逻辑)
        if (isBoss) {
            strengthAmount = -1; // Boss 战反向爆发：-1 力量
        } else if (isElite) {
            strengthAmount = 1;  // 精英战：+1 力量
        }

        // 4. 执行效果
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, strengthAmount), strengthAmount));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}