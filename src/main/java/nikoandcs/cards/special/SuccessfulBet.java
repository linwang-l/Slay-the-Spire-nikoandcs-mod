package nikoandcs.cards.special;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class SuccessfulBet extends BaseCard {
    public static final String ID = makeID(SuccessfulBet.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK, // 设定为攻击牌，以享受力量加成
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY, // 目标为全体敌人
            0
    );

    public SuccessfulBet() {
        super(ID, info);
        setDamage(16, 4); // 基础 16，升级 +4 = 20
        setBlock(16, 4);  // 基础 16，升级 +4 = 20
        this.isMultiDamage = true; // 声明这是群体伤害
        this.isEthereal = true;    // 虚无
        this.exhaust = true;       // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得格挡
        addToBot(new GainBlockAction(p, p, block));

        // 2. 对全体敌人造成伤害
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeBlock(4);
            initializeDescription();
        }
    }
}