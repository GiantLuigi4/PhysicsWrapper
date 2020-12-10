import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.backend.interfaces.ICustomShapeCollider;
import com.tfc.physics.wrapper.common.colliders.Collider;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RampCollider extends Collider implements ICustomShapeCollider {
	ArrayList<Vector2> vector2s = new ArrayList<>();
	
	public RampCollider() {
		vector2s.addAll(
				Arrays.asList(
						new Vector2(-10, 0),
						new Vector2(10, 10),
						new Vector2(10, 0)/*,
						new Vector2(-10, 0)*/
				)
		);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		for (int i=0;i<vector2s.size()-1;i++) {
			Vector2 vec1 = vector2s.get(i);
			Vector2 vec2 = vector2s.get(i + 1);
			g2d.drawLine(
					(int) vec1.x, (int) vec1.y,
					(int) vec2.x, (int) vec2.y
			);
		}
	}
	
	@Override
	public ArrayList<Vector2> createShape() {
		return vector2s;
	}
}
