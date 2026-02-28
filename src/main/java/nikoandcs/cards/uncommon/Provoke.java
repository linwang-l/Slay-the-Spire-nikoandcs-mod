package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Provoke extends BaseCard {
    public static final String ID = makeID(Provoke.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0 // 0 能量
    );

    public Provoke() {
        super(ID, info);
        // magicNumber 管理我方获得的力量：基础 2，升级后 3 (2+1)
        setMagic(2, 1);
        // vulnAmt 管理对方获得的易伤：基础 2，升级后 3 (2+1)
        setCustomVar("vulnAmt", 2, 1);
        // 对方获得的力量固定为 2，不需要升级
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 我方获得力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));

        // 2. 目标获得 2 点力量 (这是给敌人的 Buff，所以数值是正的 2)
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, 1), 1));

        // 3. 目标获得易伤
        int vAmt = customVar("vulnAmt");
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, vAmt, false), vAmt));
    }
}