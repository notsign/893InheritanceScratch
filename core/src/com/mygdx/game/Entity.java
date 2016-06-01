package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Kevin on 16/05/2016.
 */

// An entity is simply an object that can be updated and drawn.

// Update is for tick-based logic ie. Reacting to keys being pressed, position of player, etc.
// Draw is for simply drawing the object

public interface Entity {
	enum EntityType {
		UNKNOWN,
		PLAYER,
		BULLET,
		FAST_ENEMY,
		ENEMY_SPAWNER,
	}

	void update();
	void render(SpriteBatch spriteBatch);

	boolean shouldBeDestroyed();
	void destroy();

	EntityType getEntityType();

	Body getBody(); // Can be null if the implementing class doesn't have one
}
