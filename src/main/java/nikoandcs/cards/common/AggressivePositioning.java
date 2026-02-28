package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class AggressivePositioning extends BaseCard {
    public static final String ID = makeID(AggressivePositioning.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON, // 修改为普通牌
            CardTarget.SELF,
            0
    );

    public AggressivePositioning() {
        super(ID, info);
        setMagic(2, 2); // 基础 5 点临时力量，升级后 5+3=8 点
        this.exhaust = true; // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 给予自己 2 层易伤
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 2, false), 2));

        // 2. 给予临时力量逻辑（力量 + 回合结束失去力量）
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));

    }
}