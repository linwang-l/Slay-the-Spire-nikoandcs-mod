package nikoandcs.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FeelingHotPower extends AbstractPower {
    public static final String POWER_ID = "nikoandcs:FeelingHotPower";

    // 新增一个私有变量作为计数器，不再占用 amount
    private int attackCounter = 0;

    public FeelingHotPower(AbstractCreature owner, int stackAmount) {
        this.name = "手感火热";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = stackAmount; // 这里的 amount 代表层数 n
        this.type = PowerType.BUFF;
        this.loadRegion("corruption");
        this.updateDescription();
    }

    // 必须重写这个方法，才能在重复获得该能力时增加层数
    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        // 回合开始重置计数器
        this.attackCounter = 0;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.attackCounter++;

            if (this.attackCounter == 2) {
                this.flash();
                this.attackCounter = 0;
                // 关键点：每打出两张，回复 amount 点能量（即层数 n）
                addToBot(new GainEnergyAction(this.amount));
            }
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        // 动态描述：回复几点，当前进度是多少
        this.description = "每打出 2 张攻击牌，获得 " + this.amount + " 点能量。 [NL] 当前进度：" + this.attackCounter + "/2。";
    }
}