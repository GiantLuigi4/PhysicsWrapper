import com.tfc.physics.wrapper.Physics;
import com.tfc.physics.wrapper.common.Vector2;
import com.tfc.physics.wrapper.common.WrapperWorld;
import com.tfc.physics.wrapper.common.backend.interfaces.ICollider;
import com.tfc.physics.wrapper.common.colliders.BoxCollider;
import com.tfc.physics.wrapper.common.colliders.CircleCollider;
import com.tfc.physics.wrapper.common.colliders.Collider;
import com.tfc.physics.wrapper.common.joint.Joint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class PhysicWrapperTest extends JComponent implements KeyListener {
	WrapperWorld world;
	
	private static final ArrayList<ICollider> colliders = new ArrayList<>();
	private static Collider middle = new CircleCollider(10,10);
	
	float lastPlayerY = 0;
	
	int jumpTime = 0;
	boolean isJumping = false;
	
	private static final JFrame frame = new JFrame("Physics Wrapper Test");
	
	public static void main(String[] args) {
		Physics.init();
		
		PhysicWrapperTest wrapperTest = new PhysicWrapperTest();
		
		frame.add(wrapperTest);
		frame.addKeyListener(wrapperTest);
		
		frame.setSize(1000, 1000);
		
		wrapperTest.world = new WrapperWorld(new Vector2(0, 0.1f));
		Collider player = new CircleCollider(10,10,1) {
			@Override
			public void draw(Graphics2D g2d) {
				Color c = g2d.getColor();
				g2d.setColor(Color.RED);
				super.draw(g2d);
				g2d.setColor(c);
			}
		};
		middle = player;
		middle.move(200,100);
		
		wrapperTest.world.addCollider(player);
		
//		wrapperTest.world.addCollider(new CircleCollider(50,50,1).move(0,60));
//		wrapperTest.world.addCollider(new BoxCollider(10,10).move(-105,60));
//		wrapperTest.world.addCollider(new BoxCollider(10,20).move(-95,60));
//		wrapperTest.world.addCollider(new BoxCollider(10,30).move(-85,60));
//		wrapperTest.world.addCollider(new BoxCollider(10,40).move(-75,60));
		
		
//		wrapperTest.world.addCollider(middle);
		
		for (int i=0;i<=32;i++) {
			ICollider collider2 = new CircleCollider(10,10,1,true) {
				@Override
				public float getFriction() {
					return 128;
				}
				
				@Override
				public float getDensity() {
					return 0;
				}
				
				@Override
				public float getLinearDamping() {
					return 0.01f;
				}
				
				@Override
				public void draw(Graphics2D g2d) {
					Color c = g2d.getColor();
					g2d.setColor(Color.ORANGE);
					super.draw(g2d);
					g2d.setColor(c);
				}
			}.move(200+(int)((Math.cos(Math.toRadians(i*12)))*110),100+(int)((Math.sin(Math.toRadians(i*12)))*110));
			colliders.add(collider2);
			wrapperTest.world.addCollider(collider2);
			if (i>=1) {
				ICollider collider1 = colliders.get(colliders.size()-2);
				Joint joint = new Joint(collider1,collider2);
				joint.descriptor.dampening = 256*2;
				wrapperTest.world.addJoint(joint);
			}
			
			Joint midJoint = new Joint(collider2,middle);
			midJoint.descriptor.dampening = 256*32;
			midJoint.descriptor.length = 116;
			wrapperTest.world.addJoint(midJoint);
		}
		{
			ICollider collider1 = colliders.get(0);
			ICollider collider2 = colliders.get(colliders.size()-1);
			wrapperTest.world.addJoint(new Joint(collider1,collider2));
		}
		
		for (int i=0;i<colliders.size();i++) {
			ICollider collider1 = colliders.get((i+16)%32);
			ICollider collider2 = colliders.get(i);
			
			collider1.setGravityScale(0);
			
			Joint parallelJoint = new Joint(collider2,collider1);
			parallelJoint.descriptor.dampening = 256*256;
			parallelJoint.descriptor.length = 120*2f;
			wrapperTest.world.addJoint(parallelJoint);
		}
		
//		Random random = new Random();
//		for (int i=0; i <= 10; i++) {
//			wrapperTest.world.addCollider(new BoxCollider(random.nextInt(90)+10, random.nextInt(90)+10).rotate(0)
//					.move(((random.nextBoolean()?-1:1)*random.nextInt(100))*100, ((random.nextBoolean()?-1:1)*random.nextInt(100))).setImmovable());
//		}
		
//		wrapperTest.world.addCollider(new RampCollider().move(0,0));
		wrapperTest.world.addCollider(new BoxCollider(400,10).rotate(0)
				.move(0,400).setImmovable());
		
		wrapperTest.world.addCollider(new BoxCollider(1000,10).rotate(0)
				.move(410,-100).rotate(Math.toRadians(90)).setImmovable());
		wrapperTest.world.addCollider(new BoxCollider(1000,10).rotate(0)
				.move(-410,-100).rotate(Math.toRadians(90)).setImmovable());
		
		for (int i=0; i<10;i++) {
			ICollider collider = new CircleCollider(10,10) {
				@Override
				public float getDensity() {
					return 0;
				}
				
				@Override
				public float getLinearDamping() {
					return 0;
				}
				
				@Override
				public float getFriction() {
					return 0;
				}
			}.move((int)player.getX(),(int)player.getY());
			wrapperTest.world.addCollider(collider);
			collider.setGravityScale(0);
		}
		
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
			
			boolean isOnGround = false;
			if ((int) (player.getPositionSupplier().get().y * 10) == (int) (wrapperTest.lastPlayerY * 10)) {
				isOnGround = true;
			}
			
			isOnGround = true;
			
//			if (new Random().nextBoolean() && new Random().nextBoolean() && new Random().nextBoolean()) {
//				wrapperTest.world.addCollider(new CircleCollider(10,10).move( (new Random().nextBoolean()?-1:1)*new Random().nextInt(300),-500));
//			}
			
//			System.out.println(wrapperTest.lastPlayerY);
//			System.out.println(player.getPositionSupplier().get().y);
			
			wrapperTest.lastPlayerY = player.getPositionSupplier().get().y;
			
			double yStrength = 1;
			double xStrength = 1;
			
			if (wrapperTest.isKeyPressed(87) && (isOnGround || (wrapperTest.isJumping && wrapperTest.jumpTime <= 20 && wrapperTest.jumpTime >= 0))) {
				player.addVelocity(0, -yStrength);
				wrapperTest.isJumping = true;
				wrapperTest.jumpTime++;
			} else {
				wrapperTest.isJumping = false;
				if (wrapperTest.jumpTime >= 1) {
					wrapperTest.jumpTime = -20;
				} else {
					wrapperTest.jumpTime++;
				}
			}
			if (wrapperTest.isKeyPressed(68)) player.addVelocity(xStrength, 0);
			if (wrapperTest.isKeyPressed(65)) player.addVelocity(-xStrength, 0);
			if (wrapperTest.isKeyPressed(83) && (isOnGround || (wrapperTest.isJumping && wrapperTest.jumpTime <= 20 && wrapperTest.jumpTime >= 0)))
				player.addVelocity(0, yStrength);
				
				boolean isFirst = true;
			float xMid = 0;
			float yMid = 0;
			
			int num = 0;
			
			for (ICollider collider : colliders) {
				xMid += collider.getPositionSupplier().get().x;
				yMid += collider.getPositionSupplier().get().y;
				if (isFirst) {
					isFirst = false;
				} else {
					xMid /= 2.1;
					yMid /= 3;
				}
				num++;
//				collider.getPositionSetter().setX((num-16)*20);
//				collider.getPositionSetter().setY(Math.sin(num/10f)*-80);
			}
			
//			middle.getPositionSetter().setX(0);
//			middle.getPositionSetter().setY(0);
			
//			System.out.println(xMid+","+yMid);
			Thread.sleep(5);
		} catch (Throwable ignored) {
			ignored.printStackTrace();
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
		g2d.scale(1,1);
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
