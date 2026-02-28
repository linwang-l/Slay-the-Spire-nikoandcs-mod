package nikoandcs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class ThirdFloorFun extends BaseCard {
    public static final String ID = makeID(ThirdFloorFun.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,   // 能力牌
            CardRarity.RARE,   // 稀有度：金卡
            CardTarget.SELF,
            3 // 初始能量消耗 3
    );

    public ThirdFloorFun() {
        super(ID, info);
        setMagic(3); // 获得 3 点缓冲
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 参考 Buffer 源码第 33 行，赋予玩家缓冲能力
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2); // 升级后能量消耗改为 2
            initializeDescription();
        }
    }
}