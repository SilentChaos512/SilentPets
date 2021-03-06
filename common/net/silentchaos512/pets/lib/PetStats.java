package net.silentchaos512.pets.lib;

public class PetStats {

  public static final double BASE_SPEED = 0.3;

  public static final PetStats cat = new PetStats(20.0, BASE_SPEED * 1.2, 4.0f);
  public static final PetStats chicken = new PetStats(16.0, BASE_SPEED * 1.2, 3.0f);
  public static final PetStats cow = new PetStats(24.0, BASE_SPEED * 0.9, 5.0f);
  public static final PetStats dog = new PetStats(20.0, BASE_SPEED * 1.1, 5.0f);
  public static final PetStats mooshroom = new PetStats(24.0, BASE_SPEED * 0.9, 5.0f);
  public static final PetStats pig = new PetStats(20.0, BASE_SPEED, 6.0f);
  public static final PetStats sheep = new PetStats(20.0, BASE_SPEED, 5.0f);

  public static final PetStats generic = new PetStats(20.0, BASE_SPEED, 4.0f);

  public final float damage;
  public final double health;
  public final double speed;

  private PetStats(double health, double speed, float damage) {

    this.damage = damage;
    this.health = health;
    this.speed = speed;
  }

  public static void init() {

  }
}
