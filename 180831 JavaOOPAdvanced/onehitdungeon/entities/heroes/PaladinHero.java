package onehitdungeon.entities.heroes;

import onehitdungeon.entities.items.ArmorItemImpl;
import onehitdungeon.entities.items.OffhandItemImpl;
import onehitdungeon.entities.items.WeaponItemImpl;

public class PaladinHero extends BaseHero {
    public PaladinHero(String name) {
        super(name, new WeaponItemImpl(20, 10.0),
                new OffhandItemImpl(10, 10.0),
                new ArmorItemImpl(25, 20.0));
    }

    @Override
    public Integer getTotalBattlePower() {
        return ((this.getWeapon().getBattlePower() + this.getOffhand().getBattlePower() + this.getArmor().getBattlePower()) * 4) / 9;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - Lvl. %d Paladin%n", this.getName(), this.getTrainings() + 1));
        sb.append(String.format("* Mace - %d (BP)%n", this.getWeapon().getBattlePower()));
        sb.append(String.format("* Shield - %d (BP)%n", this.getOffhand().getBattlePower()));
        sb.append(String.format("* Cuirass - %d (BP)%n", this.getArmor().getBattlePower()));
        sb.append("####################").append(System.lineSeparator());
        sb.append(String.format("Gold: %.2f%n", this.getGold()));
        sb.append(String.format("Upgrade cost: %.2f", this.getTotalPriceForUpgrade()));
        return sb.toString();
    }
}
