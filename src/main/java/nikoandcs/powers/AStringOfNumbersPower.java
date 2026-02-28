package nikoandcs.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import nikoandcs.cards.special.Badjoke;

public class AStringOfNumbersPower extends AbstractPower {
    public static final String POWER_ID = "nikoandcs:AStringOfNumbersPower";
    private final boolean isUpgraded;

    public AStringOfNumbersPower(AbstractCreature owner, int amount, boolean upgraded) {
        this.name = "一串数字";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isUpgraded = upgraded;
        this.type = PowerType.BUFF;

        // 1. 修正图标路径：对齐原版 Infinite Blades 的资源名（没有下划线，B大写）
        this.loadRegion("infiniteBlades");

        // 防崩溃安全检查
        if (this.region48 == null) {
            this.loadRegion("strength");
        }
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        // 2. 完全对齐“无限刀刃”的触发逻辑：在回合刚开始时判定
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractCard card = new Badjoke();
            // 如果能力本身是升级版，则生成的“烂梗”也是升级版
            if (this.isUpgraded) {
                card.upgrade();
            }
            // 塞入 1 张卡牌到手牌
            addToBot(new MakeTempCardInHandAction(card, this.amount));
        }
    }

    @Override
    public void updateDescription() {
        String cardName = isUpgraded ? "升级后的 烂梗+1" : "烂梗+1";
        this.description = "每回合开始时，将 " + this.amount + " 张 " + cardName + " 加入手牌。";
    }
}