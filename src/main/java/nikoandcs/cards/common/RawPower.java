package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class RawPower extends BaseCard {
    public static final String ID = makeID(RawPower.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2 // 2 能量
    );

    public RawPower() {
        super(ID, info);
        setDamage(25, 7); // 基础 25，升级后 32
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 使用 BLUNT_HEAVY 特效表现出“重击”的感觉
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}