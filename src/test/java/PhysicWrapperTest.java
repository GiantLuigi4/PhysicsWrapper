import com.tfc.physics.wrapper.Physics;
import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.WrapperWorld;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.colliders.BoxCollider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class PhysicWrapperTest extends JComponent implements KeyListener {
	WrapperWorld world;
	
	private static final JFrame frame = new JFrame("Physics Wrapper Test");
	
	public static void main(String[] args) {
		Physics.init();
		
		PhysicWrapperTest wrapperTest = new PhysicWrapperTest();
		
		frame.add(wrapperTest);
		frame.addKeyListener(wrapperTest);
		
		frame.setSize(1000, 1000);
		
		wrapperTest.world = new WrapperWorld(new Vector2(0, 1));
		BoxCollider player = new BoxCollider(10, 10);
		wrapperTest.world.addCollider(player);
		wrapperTest.world.addCollider(new BoxCollider(400, 10).rotate(0).move(0, 100).setImmovable());
		
		frame.setVisible(true);
		
		while (frame.isVisible()) {
			tick(wrapperTest,player);
		}
		
		Runtime.getRuntime().exit(0);
	}
	
	private static void tick(PhysicWrapperTest wrapperTest, ICollider player) {
		try {
			wrapperTest.world.tick();
			frame.repaint();
			
			player.setAngle(0);
			
			if (wrapperTest.isKeyPressed(68))
				player.setVelocity(10,0);
			
			if (wrapperTest.isKeyPressed(65))
				player.setVelocity(-10,0);
			
//			player.getPositionSetter().setY(-100);
			
			Thread.sleep(10);
		} catch (Throwable ignored) {
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(new Color(0));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		g.setColor(new Color(255, 255, 255));
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transform = g2d.getTransform();
		g2d.translate(frame.getWidth() / 2f, frame.getHeight() / 2f);
		g2d.scale(0.5,0.5);
		world.getColliders().forEach(collider -> {
			AffineTransform transform1 = g2d.getTransform();
			collider.draw(g2d);
			g2d.setTransform(transform1);
		});
		g2d.setTransform(transform);
	}
	
	ArrayList<Integer> keys = new ArrayList<>();
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyCode())) {
			keys.add(e.getKeyCode());
			System.out.println(e.getKeyCode());
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (keys.contains(e.getKeyCode())) {
			keys.remove((Object) e.getKeyCode());
		}
	}
	
	public boolean isKeyPressed(int d) {
		return keys.contains(d);
	}
}
