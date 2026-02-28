package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class HumorousSniper extends BaseCard {
    public static final String ID = makeID(HumorousSniper.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER, // 能力牌
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1 // 2 能量
    );

    public HumorousSniper() {
        super(ID, info);
        setMagic(3, 1); // 力量数值：基础 3，升级 +1 = 4
        setCustomVar("weakAmt", 2, -1); // 虚弱层数：基础 2，升级 -1 = 1
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得永久力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));

        // 2. 获得虚弱 (注意：false 表示这是玩家给自己的，不是怪物给的)
        int weakAmount = customVar("weakAmt");
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, weakAmount, false), weakAmount));
    }
}