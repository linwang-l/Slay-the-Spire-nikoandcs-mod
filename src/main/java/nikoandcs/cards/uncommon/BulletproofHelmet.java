package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class BulletproofHelmet extends BaseCard {
    public static final String ID = makeID(BulletproofHelmet.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public BulletproofHelmet() {
        super(ID, info);
        setMagic(3, 1);       // magicNumber 用于金属化：3 -> 升级后 4
        this.exhaust = true;  // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 给予金属化
        addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));

        // 2. 给予人工制品 (基础 1，升级后 2)
        int artifactAmount = upgraded ? 2 : 1;
        addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, artifactAmount), artifactAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // 金属化 +1
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}