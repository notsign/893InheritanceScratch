package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rueban Rasaselvan on 03/04/2016.
 */

// K: For what it's doing, this should really be called something along the lines of 'EnemyManager'

public class EnemySpawner implements Entity {
	World world;
	Vector2 position;
	List<Entity> entityBuffer; // Reference to the main entityList
	int spawnTimer;
	int maxEnemies;
	Player player;

	EnemySpawner(World world, List<Entity> entityBuffer, Player player, Vector2 position, int maxEnemies) {
		this.world = world;
		this.entityBuffer = entityBuffer;
		this.player = player;
		this.position = position;
		this.maxEnemies = maxEnemies;
		spawnTimer = 0;
	}

	void createEnemy() {
		entityBuffer.add(new FastEnemy(world, player, position));
	}

	public void render(SpriteBatch spriteBatch) {

	}

	public void update() {
		float playerDist = position.dst(player.getPosition());
		//only if the player enters within the region of radius 500 from the spawner block
		//then the enemies will spawn.
		spawnTimer++;
		if (playerDist <= 500f) {
			//every 5 seconds a new enemy is spawned
			if (spawnTimer >= 60) {
				spawnTimer = 0;
				createEnemy();
			}
		}
	}

	public boolean shouldBeDestroyed(){
		return false; //lol
	}

	public void destroy(){
		//lol
	}

	public Body getBody(){
		return null;
	}
}


