package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class MentalPeak extends BaseCard {
    public static final String ID = makeID(MentalPeak.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0 // 0 能量
    );

    public MentalPeak() {
        super(ID, info);
        setMagic(1, 1); // 基础 1，升级后 +1 = 2
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 检查敌人意图是否为攻击
        boolean isAttacking = (m.intent == AbstractMonster.Intent.ATTACK ||
                m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEFEND);

        if (isAttacking) {
            // 意图为攻击，获得力量
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        } else {
            // 意图不为攻击，获得敏捷
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        }
    }

}