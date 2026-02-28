package nikoandcs.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Strafe extends BaseCard {
    public static final String ID = makeID(Strafe.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY, // 全体伤害
            2 // 2 能量
    );

    public Strafe() {
        super(ID, info);
        setDamage(5); // 基础伤害 5
        setMagic(4, 1); // 基础 4 次，升级后 +1 = 5 次
        this.isMultiDamage = true; // 声明是全体多次伤害
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 造成多次全体伤害
        for (int i = 0; i < magicNumber; i++) {
            // 使用 AbstractGameAction.AttackEffect.FIRE 来模拟开火特效
            addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }

        // 2. 丢弃手牌中所有的攻击牌
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : p.hand.group) {
                    if (c.type == CardType.ATTACK && c != Strafe.this) { // 检查是攻击牌且不是正在打出的这张
                        addToTop(new DiscardSpecificCardAction(c));
                    }
                }
                this.isDone = true;
            }
        });
    }
}