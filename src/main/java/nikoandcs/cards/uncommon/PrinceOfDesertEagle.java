package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import nikoandcs.cards.BaseCard;
import nikoandcs.cards.special.OneTap; // 导入你之前写的“颗秒”
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class PrinceOfDesertEagle extends BaseCard {
    public static final String ID = makeID(PrinceOfDesertEagle.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            -1 // -1 代表 X 消耗
    );

    public PrinceOfDesertEagle() {
        super(ID, info);
        setMagic(0, 1); // 升级后增加 1 张基础数量
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获取当前能量
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        // 2. 考虑“化学物 X”遗物的加成
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }

        // 3. 计算最终生成的张数：能量 X + 升级增加的 magicNumber
        int totalAmount = effect + this.magicNumber;

        if (totalAmount > 0) {
            // 4. 生成“颗秒”
            addToBot(new MakeTempCardInHandAction(new OneTap(), totalAmount));
        }

        // 5. 消耗掉所有能量
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }
}