package onehitdungeon.entities.heroes;

import onehitdungeon.interfaces.ArmorItem;
import onehitdungeon.interfaces.Hero;
import onehitdungeon.interfaces.OffhandItem;
import onehitdungeon.interfaces.WeaponItem;

public abstract class BaseHero implements Hero {
    private String name;
    private WeaponItem weapon;
    private OffhandItem offhand;
    private ArmorItem armor;
    private double gold;
    private int trainings;

    public BaseHero(String name, WeaponItem weapon, OffhandItem offhand, ArmorItem armor) {
        this.name = name;
        this.weapon = weapon;
        this.offhand = offhand;
        this.armor = armor;
        this.trainings = 0;
    }

    @Override
    public String getHeroClass() {
        String className = this.getClass().getSimpleName();
        return className.substring(0, className.indexOf("Hero"));
    }

    @Override
    public Double getGold() {
        return this.gold;
    }

    @Override
    public void earnGold(Double gold) {
        this.gold += gold;
    }

    @Override
    public void payGold(Double gold) {
        this.trainings++;
        this.gold -= gold;
    }

    @Override
    public WeaponItem getWeapon() {
        return this.weapon;
    }

    @Override
    public OffhandItem getOffhand() {
        return this.offhand;
    }

    @Override
    public ArmorItem getArmor() {
        return this.armor;
    }

    @Override
    public abstract Integer getTotalBattlePower();

    @Override
    public Double getTotalPriceForUpgrade() {
        return this.getWeapon().getPriceForUpgrade()
                + this.getOffhand().getPriceForUpgrade()
                + this.getArmor().getPriceForUpgrade();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    int getTrainings(){
        return this.trainings;
    }
}
