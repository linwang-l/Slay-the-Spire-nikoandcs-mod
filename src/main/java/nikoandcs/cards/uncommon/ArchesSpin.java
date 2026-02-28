package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class ArchesSpin extends BaseCard {
    public static final String ID = makeID(ArchesSpin.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            3
    );

    public ArchesSpin() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 参考精炼混沌：遍历并处理。这里我们遍历手牌。
        // 注意：为了避免在遍历时修改列表导致崩溃，我们倒着遍历或者创建一个副本
        for (int i = p.hand.group.size() - 1; i >= 0; i--) {
            AbstractCard c = p.hand.group.get(i);

            // 筛选出攻击牌
            if (c.type == CardType.ATTACK) {
                // 参考精炼混沌的 use 方法第 41 行：随机获取一个活着的怪物
                AbstractMonster randomMonster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                // 使用 NewQueueCardAction 将其加入出牌队列
                // 参数：卡牌，随机目标，不消耗能量，保持原样（不删除）
                addToBot(new NewQueueCardAction(c, randomMonster, false, true));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2); // 升级后 2 费
            initializeDescription();
        }
    }
}