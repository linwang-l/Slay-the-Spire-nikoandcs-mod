package nikoandcs.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Defend extends BaseCard {
    // 1. 卡牌 ID
    public static final String ID = makeID(Defend.class.getSimpleName());

    // 2. 卡牌基础属性
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,       // 注意：防御牌是 SKILL（技能）
            CardRarity.BASIC,     // 基础牌
            CardTarget.SELF,      // 目标：自己
            1                     // 耗能：1费
    );

    // 3. 数值设定
    private static final int BLOCK = 5;         // 基础格挡：5
    private static final int UPG_BLOCK = 3;     // 升级后增加：3

    public Defend() {
        super(ID, info);

        // 设置格挡值和升级加成
        setBlock(BLOCK, UPG_BLOCK);

        // 添加“基础防御”标签，用于某些遗物触发（如潘多拉魔盒）
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 执行动作：玩家获得格挡
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Defend();
    }
}