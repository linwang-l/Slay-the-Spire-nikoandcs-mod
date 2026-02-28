package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Flashbang extends BaseCard {
    public static final String ID = makeID(Flashbang.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public Flashbang() {
        super(ID, info);
        setMagic(1, 1);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));

        int strLoss = upgraded ? 6 : 4;

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                // 1. 先处理力量逻辑 (参考 Piercing Wail 原版顺序)
                // 无论有没有人工制品，都先把“减力量”排队
                addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -strLoss), -strLoss, true, AbstractGameAction.AttackEffect.NONE));

                // 2. 只有当目前没有人工制品时，才给予“回力”镣铐
                // 这样如果减力量被人工制品挡了，就不会给回力；如果没被挡，就会给回力
                if (!mo.hasPower("Artifact")) {
                    addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, strLoss), strLoss, true, AbstractGameAction.AttackEffect.NONE));
                }

                // 3. 最后再排入虚弱。这样即使虚弱吃掉了人工制品，也不会影响上面的力量检查逻辑
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}