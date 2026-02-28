package nikoandcs.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantTextEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class CounterExecution extends BaseCard {
    public static final String ID = makeID(CounterExecution.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3 // 3 能量
    );

    public CounterExecution() {
        super(ID, info);
        setMagic(75, 25); // 基础 75，升级后 +25 = 100
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            // 复制原版审判的黄金重击特效
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, Color.GOLD.cpy())));
            addToBot(new WaitAction(0.8F));
            // 弹出巨大的“JUDGED”文字
            addToBot(new VFXAction(new GiantTextEffect(m.hb.cX, m.hb.cY)));

            // 执行斩杀逻辑
            addToBot(new JudgementAction(m, this.magicNumber));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        if (m != null) {
            // 判定逻辑：只有当意图为 ATTACK 相关时才可使用
            boolean isAttacking = (m.intent == AbstractMonster.Intent.ATTACK ||
                    m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                    m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                    m.intent == AbstractMonster.Intent.ATTACK_DEFEND);

            if (!isAttacking) {
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0]; // 在 JSON 中定义“对方没有攻击意图”
                return false;
            }
        }
        return true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(25);
            initializeDescription();
        }
    }
}