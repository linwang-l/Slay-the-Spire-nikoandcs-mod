package nikoandcs.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ZeroCostPower extends AbstractPower {
    public static final String POWER_ID = "nikoandcs:ZeroCostPower";

    public ZeroCostPower(AbstractCreature owner) {
        this.name = "绝地反击";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 1. 修改这里：使用“腐化”的图标名
        this.loadRegion("corruption");

        // 2. 安全检查：如果万一没加载成功，换成“敏捷”图标占位，防止 flash() 时崩溃
        if (this.region48 == null) {
            this.loadRegion("dexterity");
        }

        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        // 当能力刚施加时，遍历当前手牌并全部设为 0 费（限打出一次）
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.freeToPlayOnce = true;
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        // 抽到的牌也变 0 费
        card.freeToPlayOnce = true;
    }

    @Override
    public void updateDescription() {
        this.description = "所有卡牌的耗能变为 0。";
    }
}