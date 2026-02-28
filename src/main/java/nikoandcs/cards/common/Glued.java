package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Glued extends BaseCard {
    public static final String ID = makeID(Glued.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2 // 2 能量
    );

    public Glued() {
        super(ID, info);
        setDamage(8, 2);      // 伤害：8 -> 升级后 10
        setBlock(10, 2);      // 格挡：10 -> 升级后 12
        setMagic(2, 1);       // 虚弱层数：2 -> 升级后 3
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得格挡
        addToBot(new GainBlockAction(p, p, block));

        // 2. 造成伤害
        // 使用单手击打效果，模拟“黏住”或“拍击”的感觉
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // 3. 施加虚弱
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(2);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}