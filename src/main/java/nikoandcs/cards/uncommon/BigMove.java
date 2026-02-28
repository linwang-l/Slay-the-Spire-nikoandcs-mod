package nikoandcs.cards.uncommon; // 修改为 uncommon 包

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.actions.FlamingAction; // 调用烧牌动作
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class BigMove extends BaseCard {
    public static final String ID = makeID(BigMove.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON, // 修改 1：改为蓝卡
            CardTarget.NONE,
            2
    );

    public BigMove() {
        super(ID, info);
        this.exhaust = true;
        this.isEthereal = true; // 基础版带虚无
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 修改 2：调用消耗非攻击牌的动作
        addToBot(new FlamingAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // 修改 3：升级逻辑改为去掉虚无
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}