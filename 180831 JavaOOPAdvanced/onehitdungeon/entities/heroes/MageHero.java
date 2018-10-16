package onehitdungeon.entities.heroes;

import onehitdungeon.entities.items.ArmorItemImpl;
import onehitdungeon.entities.items.OffhandItemImpl;
import onehitdungeon.entities.items.WeaponItemImpl;

public class MageHero extends BaseHero {
    public MageHero(String name) {
        super(name, new WeaponItemImpl(45, 15.0),
                new OffhandItemImpl(25, 20.0),
                new ArmorItemImpl(10, 25.0));
    }

    @Override
    public Integer getTotalBattlePower() {
        return ((this.getWeapon().getBattlePower() + this.getArmor().getBattlePower() - this.getOffhand().getBattlePower()) * 3) / 4;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - Lvl. %d Mage%n", this.getName(), this.getTrainings() + 1));
        sb.append(String.format("* Staff - %d (BP)%n", this.getWeapon().getBattlePower()));
        sb.append(String.format("* Orb - %d (BP)%n", this.getOffhand().getBattlePower()));
        sb.append(String.format("* Cape - %d (BP)%n", this.getArmor().getBattlePower()));
        sb.append("####################").append(System.lineSeparator());
        sb.append(String.format("Gold: %.2f%n", this.getGold()));
        sb.append(String.format("Upgrade cost: %.2f", this.getTotalPriceForUpgrade()));
        return sb.toString();
    }
}
