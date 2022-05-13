package fun.mcbee.plugins.dyingcraft.GunSystem;

public class ShootingData {

    private Long lastShotTime = null;
    private long shootDelay;
    private int shotsLeft;

    public ShootingData(long shootDelay, int maxShots) {
        this.shootDelay = shootDelay;
        this.shotsLeft = maxShots;
    }

    public void UpdateShootTime() {
        this.lastShotTime = System.currentTimeMillis();
    }

    public boolean CanShoot() {
        return shotsLeft > 0 && GetTimeDelay() == 0;
    }

    public boolean Shoot() {
        if(this.CanShoot()) {
            UpdateShootTime();
            shotsLeft--;
            return true;
        }
        return false;
    }

    public int GetShotsLeft() {
        return this.shotsLeft;
    }

    public long GetTimeDelay() {
        if(this.lastShotTime == null) {
            return 0;
        }
        long diff = System.currentTimeMillis() - this.lastShotTime;
        if(diff >= this.shootDelay) {
            return 0;
        }
        return diff;
    }

}
