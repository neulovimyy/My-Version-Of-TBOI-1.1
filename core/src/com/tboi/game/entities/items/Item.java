package com.tboi.game.entities.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.screens.GameScreen;
public abstract class Item {
    protected GameScreen screen;
    protected World world;
    protected Body body;
    protected Vector2 vector2;
    protected boolean isDestroyed, toDestroy;
}