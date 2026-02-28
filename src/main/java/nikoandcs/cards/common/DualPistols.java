package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class DualPistols extends BaseCard {
    public static final String ID = makeID(DualPistols.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1 // 1 能量
    );

    public DualPistols() {
        super(ID, info);
        setDamage(3, 1); // 基础 3，升级后 4 (总伤害从 9 变为 12)
        setMagic(3);    // 攻击次数为 3
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 循环执行伤害动作
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
}