package azuma;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import framework.game2D.Actor2D;
import framework.game2D.Sprite;
import framework.game2D.Velocity2D;

public class UnitBullet extends Sprite {
	
	public UnitBullet(String bullet) {
		super(bullet);
		super.setCollisionRadius(1.0);
	}

}
