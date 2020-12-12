# PhysicsWrapper

<!--
I did it
-->

# Usage:

```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.GiantLuigi4:PhysicsWrapper:Tag'
    
    //All repos need for the physics engine of your choice
    //These are the ones for Box2D
    compile group: 'org.jbox2d', name: 'jbox2d', version: '2.2.1.1', ext: 'pom'
    compile group: 'org.jbox2d', name: 'jbox2d-serialization', version: '1.1.0'
    compile group: 'org.jbox2d', name: 'jbox2d-testbed', version: '2.2.1.1'
    compile group: 'org.jbox2d', name: 'jbox2d-testbed-jogl', version: '1.0.1'
    compile group: 'org.jbox2d', name: 'jbox2d-library', version: '2.2.1.1'
}
```

# Example of usage:
```java
Physics.init();
WrapperWorld world = new WrapperWorld(new Vector2(0, 0.1f));
world.addCollider(new CircleCollider(10,10,1));
world.addCollider(new BoxCollider(400,10).rotate(0).move(0,400).setImmovable());```
