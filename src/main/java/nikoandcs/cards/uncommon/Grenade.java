package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Grenade extends BaseCard {
    public static final String ID = makeID(Grenade.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY, // 对所有敌人
            1 // 1 能量
    );

    public Grenade() {
        super(ID, info);
        setDamage(14, 4); // 基础 14，升级后 +4 = 18
        this.isMultiDamage = true; // 声明是全体伤害
        this.exhaust = true; // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 播放手雷爆炸音效
        addToBot(new SFXAction("EXPLOSION_1"));

        // 2. 在屏幕中央播放爆炸视觉效果（模拟手雷炸开）
        // 我们遍历一下敌人，在第一个活着的敌人位置播放爆炸，或者在屏幕中心
        for (AbstractMonster mo : (com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new VFXAction(new ExplosionSmallEffect(mo.hb.cX, mo.hb.cY), 0.1F));
            }
        }

        // 3. 造成全体伤害
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }
}