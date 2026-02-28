package nikoandcs.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class PreAim extends BaseCard {
    public static final String ID = makeID(PreAim.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC, // 初始卡组
            CardTarget.SELF,
            0 // 修改为 0 费，通常这类临时 Buff 牌 0 费较多，若需 1 费可自行改回
    );

    public PreAim() {
        super(ID, info);
        // 设置力量数值：基础 3，升级后 5
        setMagic(3, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 赋予力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        // 2. 赋予“回合结束时失去力量”的状态，从而实现“临时”效果
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2); // 升级增加 2 点力量 (3 -> 5)
            initializeDescription();
        }
    }
}