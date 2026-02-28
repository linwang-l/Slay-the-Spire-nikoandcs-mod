package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Backstab extends BaseCard {
    public static final String ID = makeID(Backstab.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0 // 1 能量
    );

    public Backstab() {
        super(ID, info);
        setDamage(20, 6); // 基础 11，升级后 11+6=17
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 使用 SLASH_HEAVY 特效模拟有力的背刺
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        // 检查怪物的意图是否为攻击类（包括攻击、攻击+Buff、攻击+Debuff、攻击+防御）
        if (m != null && (m.intent == AbstractMonster.Intent.ATTACK ||
                m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {

            this.cantUseMessage = "敌人正准备攻击，无法使用背刺。";
            return false;
        }
        return true;
    }
}