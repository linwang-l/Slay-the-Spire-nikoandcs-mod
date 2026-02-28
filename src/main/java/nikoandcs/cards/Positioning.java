package nikoandcs.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Positioning extends BaseCard {
    public static final String ID = makeID(Positioning.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0 // 0 能量消耗
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2; // 升级后 5+2=7

    public Positioning() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(1, 1); // 敏捷：基础 1，升级后 1+1=2

        this.exhaust = true; // 设置为消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 获得格挡
        addToBot(new GainBlockAction(p, p, block));
        // 获得永久敏捷
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
    }
}